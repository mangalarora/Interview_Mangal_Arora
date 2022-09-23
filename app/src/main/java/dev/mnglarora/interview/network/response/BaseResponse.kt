package dev.mnglarora.interview.network.response

import io.ktor.http.*


abstract class BaseResponse<T> {
    var statusCode : HttpStatusCode? = null
    var message : String? = null
    var isSuccessful: Boolean = false
    var items : T? = null
}