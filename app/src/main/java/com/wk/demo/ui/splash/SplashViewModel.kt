package com.wk.demo.ui.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wk.demo.data.remote.Result

import com.wk.demo.R
import com.wk.demo.data.model.room.dao.CountriesDAO
import com.wk.demo.data.remote.SplashRepository
import com.wk.demo.ui.base.BaseViewModel
import com.wk.demo.ui.home.HomeResult
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * View model class for splash
 */
class SplashViewModel @Inject constructor(
    private val splashRepository: SplashRepository) : BaseViewModel() {
    /**
     * livedata to observe country list api result data
     */
    private val _countriesResult = MutableLiveData<SplashResult>()
    val countriesResult: LiveData<SplashResult> = _countriesResult


    fun getCountries() {
        // can be launched in a separate asynchronous job

        launch {
            val result =
                splashRepository.getCountries()
            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {

                        _countriesResult.value =
                            SplashResult(success = result.data)
                    }
                    is Result.Error -> {
                        _countriesResult.value =
                            SplashResult(error = result.msg, errorCode = result.resId)
                    }
                    else -> _countriesResult.value =
                        SplashResult(errorCode = R.string.unknown_error)
                }

            }
        }
    }
    fun getCountriesFromDB() {
        // can be launched in a separate asynchronous job

        launch {
            val result =
                splashRepository.getCountriesFromDB()
            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {

                        _countriesResult.value =
                            SplashResult(success = "success")
                    }
                    is Result.Error -> {
                        _countriesResult.value =
                            SplashResult(error = result.msg, errorCode = result.resId)
                    }
                    else -> _countriesResult.value =
                        SplashResult(errorCode = R.string.unknown_error)
                }

            }
        }

    }

}
