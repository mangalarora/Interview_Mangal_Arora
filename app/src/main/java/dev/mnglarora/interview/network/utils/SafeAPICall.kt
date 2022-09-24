package dev.mnglarora.interview.network.utils


import dev.mnglarora.interview.network.response.BaseResponse
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend inline fun <reified T> safeApiCall(crossinline apiToBeCalled: suspend () -> BaseResponse<T>): ApiResponse<T> {

    return withContext(Dispatchers.IO) {
        try {
            val response: BaseResponse<T> = apiToBeCalled()

            if (response.isSuccessful && response.items != null) {
                ApiResponse.Success(data = (response.items as T))
            } else {
                ApiResponse.Failure(message = "Something went wrong")
            }

        } catch (e: IOException) {
            ApiResponse.NetworkException(e)
        } catch (e: Exception) {
            ApiResponse.NetworkException(e)
        }
    }
}
