package com.wk.demo.data.remote.reqres

import com.squareup.moshi.Json

class CountriesResponse (
    @Json(name = "countryList")
    var countryList: List<CountriesItem>?=null
)