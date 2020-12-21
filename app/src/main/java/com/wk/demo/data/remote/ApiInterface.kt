package com.wk.demo.data.remote

import com.wk.demo.data.remote.reqres.*
import com.wk.demo.data.remote.reqres.weather.WeatherResponse


import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    /**
     * Getting all the records from remote server
     */

    @GET("all")
    fun getCountries(): Call<List<CountriesItem>>
    /**
     * Getting current mobile location weather report
     */

    @GET
    fun getLocation(
        @Url url: String
    ): Call<WeatherResponse>

}