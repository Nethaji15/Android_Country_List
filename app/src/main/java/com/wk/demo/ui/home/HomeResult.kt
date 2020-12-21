package com.wk.demo.ui.home

import android.location.Location
import com.wk.demo.data.remote.reqres.CountriesItem

data class HomeResult(
    var success: List<CountriesItem>?=null,
    var countriesItem: CountriesItem?=null,
    val location: Location? = null,
    val errorCode: Int? = null,
    val error: String? = null
)