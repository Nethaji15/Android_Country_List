package com.wk.demo.data.remote.reqres


import com.squareup.moshi.Json

data class ApiErrors(
    @Json(name = "messages")
    val messages: List<String>
)