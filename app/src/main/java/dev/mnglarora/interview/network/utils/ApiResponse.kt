package dev.mnglarora.interview.network.utils

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Failure(val message: String) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
    data class NetworkException(val error: Exception?=null) : ApiResponse<Nothing>()
    sealed class HttpErrors : ApiResponse<Nothing>() {
        data class ResourceForbidden(val exception: String) : HttpErrors()
        data class ResourceNotFound(val exception: String) : HttpErrors()
        data class InternalServerError(val exception: String) : HttpErrors()
        data class BadGateWay(val exception: String) : HttpErrors()
        data class ResourceRemoved(val exception: String) : HttpErrors()
        data class RemovedResourceFound(val exception: String) : HttpErrors()
    }
}