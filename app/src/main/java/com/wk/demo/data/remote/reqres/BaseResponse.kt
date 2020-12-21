package com.wk.demo.data.remote.reqres

import com.squareup.moshi.Json

open class BaseResponse (
    @Json(name = "errors")
    var errors: ApiErrors? = null
)