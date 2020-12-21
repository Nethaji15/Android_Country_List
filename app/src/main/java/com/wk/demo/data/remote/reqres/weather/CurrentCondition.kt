package com.wk.demo.data.remote.reqres.weather

data class CurrentCondition(

    val temp_C: String?=null,
    val temp_F: String?=null,
    val weatherIconUrl: List<WeatherIconUrl>?=null,
    val weatherDesc: List<WeatherDesc>?=null

)