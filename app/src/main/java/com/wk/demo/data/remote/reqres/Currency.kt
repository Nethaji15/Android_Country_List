package com.wk.demo.data.remote.reqres

import com.squareup.moshi.Json

data class Currency(
    @Json(name = "code")
    val code: String?=null,
    @Json(name = "name")
    val name: String?=null,
    @Json(name = "symbol")
    val symbol: String?=null
)