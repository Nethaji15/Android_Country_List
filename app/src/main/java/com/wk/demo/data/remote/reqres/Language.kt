package com.wk.demo.data.remote.reqres

import com.squareup.moshi.Json

data class Language(
    @Json(name = "iso639_1")
    val iso639_1: String?=null,
    @Json(name = "iso639_2")
    val iso639_2: String?=null,
    @Json(name = "name")
    val name: String?=null,
    @Json(name = "nativeName")
    val nativeName: String?=null
)