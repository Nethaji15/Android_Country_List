package com.wk.demo.data.remote

import android.util.Log
import com.squareup.moshi.Moshi
import com.wk.demo.R
import com.wk.demo.data.model.room.dao.CountriesDAO
import com.wk.demo.data.model.room.entity.Countries
import com.wk.demo.data.remote.reqres.BaseResponse
import com.wk.demo.data.remote.reqres.CountriesItem
import com.wk.demo.data.remote.reqres.CountriesResponse
import com.wk.demo.data.remote.reqres.weather.WeatherResponse
import javax.inject.Inject

/**
 * Repository Class that handles user login.
 */

class HomeRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val countriesDAO: CountriesDAO
) {

    @Inject
    lateinit var moshi: Moshi

    /**
     * function for get country list
     */
    fun getCountries(): Result<CountriesResponse>? {
        try {

            var userList = countriesDAO.getAll()
            val adapter = moshi.adapter(CountriesResponse::class.java).lenient()
            val countryList =
                adapter.fromJson(userList)
            return if (!countryList?.countryList.isNullOrEmpty())
                Result.Success(countryList)
            else Result.Error(statusCode = R.string.unknown_error)

        } catch (e: Throwable) {
            e.printStackTrace()
            return Result.Error(resId = R.string.unknown_error)
        }


    }

    /**
     * function for get mobile's location weather report
     */
    fun getWeatherReport(url: String): Result<WeatherResponse>? {

        try {

            val response = apiInterface.getLocation(url).execute()

            return when {
                response.body() is WeatherResponse -> {
                    val countryListObjects = response.body()
                    Result.Success(countryListObjects!!)
                }
                response.errorBody() != null -> {
                    val adapter = moshi.adapter(BaseResponse::class.java).lenient()
                    val error = adapter.fromJson(response.errorBody()?.string()!!)
                    print("Error")
                    Result.Error(
                        msg = error?.errors?.messages?.get(0),
                        statusCode = response.code()
                    )
                }
                else -> {
                    print("Error unknown")
                    Result.Error(resId = R.string.unknown_error)
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            return Result.Error(resId = R.string.unknown_error)
        }
    }

}
