package com.wk.demo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wk.demo.R
import com.wk.demo.ui.adapters.CountryListAdapter
import com.wk.demo.ui.home.HomeActivity.Companion.isDetailFragment
import com.wk.demo.utils.PrefUtils
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_country_details.*
import javax.inject.Inject


/**
 * CountrieItemFragment that handles display the details of the selected country
 */

class CountrieItemFragment : Fragment() {

    //initializing view model factory
    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory
    lateinit var homeViewModel: HomeViewModel
    lateinit var adapter: CountryListAdapter

    @Inject
    lateinit var prefUtils: PrefUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Injecting this activity via dagger2
        AndroidSupportInjection.inject(this)
        //initializing home view model
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setValues()

        back.setOnClickListener {
            isDetailFragment = false
            fragmentManager!!.popBackStack()
        }
    }

    // function handle that set the values of the selected country details
    private fun setValues() {
        // get the selected country item from preferences
        val result = prefUtils.getPrefUser()
        // concat the list of currencies along with comma separator
        val list: ArrayList<String> = ArrayList()
        result?.currencies?.forEach {
            it.name?.let { it1 -> list.add(it1) }
        }
// set text the values
        country_name.text = if (!result!!.name.isNullOrEmpty()) result.name else "N/A"
        capital.text = if (!result.capital.isNullOrEmpty()) result.capital else "N/A"
        regian.text = if (!result.region.isNullOrEmpty()) result.region else "N/A"
        population.text = if (result.population != null) result.population.toString() else "N/A"
        area.text = if (result.area != null) result.area.toString().plus(" km ") else "N/A"
        currencies.text =
            if (!java.lang.String.join(", ", list).isNullOrEmpty()) java.lang.String.join(
                ", ",
                list
            ) else "N/A"


    }


}
