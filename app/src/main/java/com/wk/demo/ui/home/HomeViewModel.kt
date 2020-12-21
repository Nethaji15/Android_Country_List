package com.wk.demo.ui.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.Moshi
import com.wk.demo.data.remote.Result

import com.wk.demo.R
import com.wk.demo.data.remote.HomeRepository
import com.wk.demo.data.remote.reqres.CountriesItem
import com.wk.demo.ui.base.BaseViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * View model class for login
 */
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : BaseViewModel() {
    @Inject
    lateinit var moshi: Moshi
    /**
     * livedata to observe country list api result data
     */
    private val _countriesResult = MutableLiveData<HomeResult>()
     val countriesResult: LiveData<HomeResult> = _countriesResult
    /**
     * livedata to observe weather api result data
     */
    private val _currentCondition = MutableLiveData<WeatherReportResult>()
     val currentCondition: LiveData<WeatherReportResult> = _currentCondition


    fun getCountries() {
        // can be launched in a separate asynchronous job

        launch {
            val result =
                homeRepository.getCountries()
            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {

                        _countriesResult.value =
                            HomeResult(success = result.data?.countryList)
                    }
                    is Result.Error -> {
                        _countriesResult.value =
                            HomeResult(error = result.msg, errorCode = result.resId)
                    }
                    else -> _countriesResult.value =
                        HomeResult(errorCode = R.string.unknown_error)
                }

            }
        }


    }

    fun getWeatherReport(lat: Double, long: Double) {
        // can be launched in a separate asynchronous job

        launch {
            val url =
                "http://api.worldweatheronline.com/premium/v1/weather.ashx?key=578e1d2eca414f7db65151606201912&q=$lat,$long&format=json"
            val result =
                homeRepository.getWeatherReport(url)
            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {

                        _currentCondition.value =
                            WeatherReportResult(success = result.data?.data?.current_condition?.first())
                    }
                    is Result.Error -> {
                        _currentCondition.value =
                            WeatherReportResult(error = result.msg, errorCode = result.resId)
                    }
                    else -> _currentCondition.value =
                        WeatherReportResult(errorCode = R.string.unknown_error)
                }

            }
        }


    }

}
