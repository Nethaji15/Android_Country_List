package com.wk.demo.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.wk.demo.R
import com.wk.demo.data.remote.reqres.CountriesItem
import com.wk.demo.data.remote.reqres.weather.CurrentCondition
import com.wk.demo.ui.adapters.CountryListAdapter
import com.wk.demo.ui.home.HomeActivity.Companion.isDetailFragment
import com.wk.demo.utils.PrefUtils
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_countries_list.*
import timber.log.Timber
import java.io.IOException
import java.util.*
import javax.inject.Inject


/**
 * CountriesListFragment that handles display the list of the countries
 */

class CountriesListFragment : Fragment() {
    //initializing CountriesList view model factory
    @Inject
    lateinit var homeViewModelFactory: HomeViewModelFactory
    lateinit var homeViewModel: HomeViewModel
    private var adapter: CountryListAdapter? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null
    private var handler1: Handler? = null
    var runnable1: Runnable? = null

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
        return inflater.inflate(R.layout.fragment_countries_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel.getCountries()
        observer()
    }

    // function handle that to get weather details from remote server based on mobile location
    private fun getWeatherReport() {

        homeViewModel.getWeatherReport(
            lat = lastLocation!!.latitude,
            long = lastLocation!!.longitude
        )
    }

    private fun observer() {
        //observing the view model for countries list
        homeViewModel.countriesResult.observe(activity!!, Observer { result ->
            if (!result.success.isNullOrEmpty()) {
                if (recyclerView != null) {
                    setAdapter(result.success!!)
                }
            }
            if (result.error != null) {
                showMessage(result.error)
            }
            if (result.errorCode != null) {
                showMessage(result.errorCode.toString())
            }
        })

        //observing the view model for weather result
        homeViewModel.currentCondition.observe(activity!!, Observer { result ->
            if (result.success != null) {
                if (progress != null) {
                    updateReport(result.success!!)
                }

            }
            if (result.error != null) {
                showMessage( result.error)
            }
            if (result.errorCode != null) {
                showMessage(result.errorCode.toString())
            }
        })
    }

    // function handle that to update weather report on UI
    private fun updateReport(result: CurrentCondition) {
        progress.visibility = View.GONE
        Glide.with(this)
            .load(result.weatherIconUrl?.first()?.value)
            .placeholder(R.drawable.placeholder)

            .into(weatherIcon)

        temp.text = result.temp_C.plus("\u2103").plus(" ")
            .plus(getAddress(lastLocation!!.latitude, lastLocation!!.longitude))
        place.text = result.weatherDesc?.first()?.value

    }

    // Function handle that to retrieve the location address
    private fun getAddress(lat: Double, lng: Double): String? {
        val geoCoder = Geocoder(activity, Locale.getDefault())
        return try {
            val addresses: List<Address> = geoCoder.getFromLocation(lat, lng, 1)
            val obj: Address = addresses[0]
            obj.locality
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }

    private fun setAdapter(countryList: List<CountriesItem>) {
        adapter = CountryListAdapter(activity!!, countryList) { item ->
            isDetailFragment = true
            prefUtils.setCountriesItem(item)
            loadFragment(CountrieItemFragment())
        }
        // set a StaggeredGridLayoutManager with 2 number of columns and vertical orientation
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        // set LayoutManager to RecyclerView
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = adapter
        // recycler view search
        val searchIcon = search.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.BLACK)

        val cancelIcon = search.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.BLACK)

        val textView = search.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.BLACK)

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter!!.filter.filter(newText)
                return false
            }

        })
        if (isDetailFragment) {
            loadFragment(CountrieItemFragment())
        }
    }
// function handle that to check the permission for location
    private fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        if (!checkPermissions()) {
            progress.visibility = View.GONE
            requestPermissions()
        } else {
            getLastLocation()
        }
    }
    // function handle that to get the mobile location
    private fun getLastLocation() {
        fusedLocationClient?.lastLocation!!.addOnCompleteListener(activity!!) { task ->
            if (task.isSuccessful && task.result != null) {
                lastLocation = task.result
                getWeatherReport()

            } else {
                progress.visibility = View.GONE
                showMessage("No location detected. Make sure location is enabled on the device.")
            }
        }
    }
    // function handle that to show toast message
    private fun showMessage(str: String) {

        Toast.makeText(activity!!, str, Toast.LENGTH_LONG).show()

    }

    private fun showSnackbar(
        mainTextStringId: String
    ) {
        showMessage( mainTextStringId)
    }
    // function handle that to check the location permission
    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }
    // function handle that to start requesting the location permission
    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            activity!!,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(
            activity!!,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (shouldProvideRationale) {
          Timber.i("Displaying permission rationale to provide additional context.")
            showSnackbar("Location permission is needed for core functionality")
        } else {
          Timber.i("Requesting permission")
            startLocationPermissionRequest()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        Timber.i("onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                   Timber.i("User interaction was cancelled.")
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    // Permission granted.
                    getLastLocation()
                }
                else -> {
                    showSnackbar(
                        "Permission was denied"
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "LocationProvider"
        private const val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }

    override fun onResume() {
        getLocation()
        handler1?.postDelayed(Runnable {
            handler1?.postDelayed(runnable1!!, 1000)
            if (checkPermissions()) {
                progress.visibility = View.VISIBLE
                handler1!!.removeCallbacks(runnable1!!)
                getLastLocation()
            }
        }.also { runnable1 = it }, 1000)

        super.onResume()
    }

    private fun loadFragment(fragment: Fragment?) {

        try {
            //switching fragment
            if (fragment != null) {
                val fragmentTransaction =
                    activity!!.supportFragmentManager.beginTransaction()
                fragmentTransaction.add(R.id.fragment, fragment)
                fragmentTransaction.addToBackStack("CountrieItemFragment")
                fragmentTransaction.commit()
            }
        } catch (e: Exception) {

            Timber.e("Exception%s", e.message)
        }
    }

}
