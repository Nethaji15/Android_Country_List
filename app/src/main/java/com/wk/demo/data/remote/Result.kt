package com.wk.demo.data.remote


/**
 * Result Class that handles repo response.
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T?) : Result<T>()
    data class Error(val msg: String? = null,val statusCode :Int? = null,val resId: Int? = null) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[error=$msg|$resId]"
        }
    }
}