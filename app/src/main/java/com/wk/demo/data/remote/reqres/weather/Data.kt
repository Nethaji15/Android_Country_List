package com.wk.demo.data.remote.reqres.weather

import com.squareup.moshi.Json

data class Data(
    @Json(name = "current_condition")
    val current_condition: List<CurrentCondition>?=null

)