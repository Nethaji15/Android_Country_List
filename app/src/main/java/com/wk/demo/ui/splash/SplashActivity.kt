package com.wk.demo.ui.splash

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.wk.demo.R
import com.wk.demo.ui.base.BaseActivity
import com.wk.demo.ui.home.HomeActivity
import com.wk.demo.utils.PrefUtils
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.fragment_country_details.*
import timber.log.Timber
import javax.inject.Inject


/**
 * Activity for Splash
 */

class SplashActivity : BaseActivity() {

    //initializing splash view model factory
    @Inject
    lateinit var splashViewModelFactory: SplashViewModelFactory

    @Inject
    lateinit var prefUtils: PrefUtils
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        AndroidInjection.inject(this)
        //initializing splash view model
        splashViewModel =
            ViewModelProvider(this, splashViewModelFactory).get(SplashViewModel::class.java)

        //function used for getting country data from server
        getCountryList()
        observer()

    }

    private fun observer() {
        splashViewModel.countriesResult.observe(this, Observer {

            if (!it.success.isNullOrEmpty()) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                progressbar.visibility=View.GONE
                Toast.makeText(this,"No record found",Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun getCountryList() {

     // Checking connectivity to load data from server
       if (isOnline(this)) {
        splashViewModel.getCountries()
         }else{
           splashViewModel.getCountriesFromDB()
       }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Timber.i("NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Timber.i("NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Timber.i("NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}

