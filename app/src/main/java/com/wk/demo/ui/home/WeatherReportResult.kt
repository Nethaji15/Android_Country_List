package com.wk.demo.ui.home

import com.wk.demo.data.remote.reqres.weather.CurrentCondition

data class WeatherReportResult(
    var success: CurrentCondition?=null,
    val errorCode: Int? = null,
    val error: String? = null
)