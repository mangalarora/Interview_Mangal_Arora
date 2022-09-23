package dev.mnglarora.interview.network.utils


import android.text.TextUtils
import dev.mnglarora.interview.network.response.BaseResponse
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


suspend inline fun <reified T> safeApiCall(crossinline apiToBeCalled: suspend () -> BaseResponse<T>): ApiResponse<T> {
    val json = Json { ignoreUnknownKeys = true; isLenient=true}
    return withContext(Dispatchers.IO) {
        try {

            val response: BaseResponse<T> = apiToBeCalled()

            if (response.isSuccessful && !TextUtils.isEmpty(response.message)) {
                ApiResponse.Success(data = json.decodeFromString(response.message!!))
            } else {
                ApiResponse.Failure(
                    message = "Something went wrong"
                )
            }

        } catch (e: IOException) {
            ApiResponse.NetworkException(e)
        } catch (e: Exception) {
            ApiResponse.NetworkException(e)
        }
    }
}
