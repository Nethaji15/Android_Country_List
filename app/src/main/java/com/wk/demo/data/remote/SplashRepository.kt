package com.wk.demo.data.remote

import android.util.Log
import com.squareup.moshi.Moshi
import com.wk.demo.R
import com.wk.demo.data.model.room.dao.CountriesDAO
import com.wk.demo.data.model.room.entity.Countries
import com.wk.demo.data.remote.reqres.BaseResponse
import com.wk.demo.data.remote.reqres.CountriesItem
import com.wk.demo.data.remote.reqres.CountriesResponse
import com.wk.demo.utils.PrefUtils
import javax.inject.Inject

/**
 * Repository Class that handles fetching details from remote server / Room .
 */

class SplashRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val countriesDAO: CountriesDAO,
    val prefUtils: PrefUtils
) {

    @Inject
    lateinit var moshi: Moshi

    /**
     * function for loading country list from server
     */
    fun getCountries(): Result<String>? {

        try {

            val response = apiInterface.getCountries().execute()

            return when {
                response.body() is List<CountriesItem> -> {
                    val countryListObjects = response.body()


                    val adapter = moshi.adapter(CountriesResponse::class.java).lenient()
                    val strcountryList =
                        adapter.toJson(CountriesResponse(countryList = countryListObjects))
                    countriesDAO.deleteAll()
                    countriesDAO.insertCountries(Countries(_id = "1",countriesList = strcountryList))
                    Log.i("success ", strcountryList)
                               Result.Success(strcountryList!!)
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
    /**
     * function for get country list from Room
     */
    fun getCountriesFromDB(): Result<CountriesResponse>? {
        try {
            val userList = countriesDAO.getAll()
            return if (!userList.isNullOrEmpty()){
            val adapter = moshi.adapter(CountriesResponse::class.java).lenient()
            val countryList =
                adapter.fromJson(userList)
                Result.Success(countryList)}
            else Result.Error(statusCode = R.string.unknown_error)

        } catch (e: Throwable) {
            e.printStackTrace()
            return Result.Error(resId = R.string.unknown_error)
        }


    }


}
