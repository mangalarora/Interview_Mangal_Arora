package dev.mnglarora.interview.network

import android.util.Log
import dev.mnglarora.interview.model.GhRepo
import dev.mnglarora.interview.network.APIConfig.ORDER_PARAM
import dev.mnglarora.interview.network.APIConfig.PAGE_PARAM
import dev.mnglarora.interview.network.APIConfig.PAGE_VALUE
import dev.mnglarora.interview.network.APIConfig.PER_PAGE_PARAM
import dev.mnglarora.interview.network.APIConfig.PER_PAGE_VALUE
import dev.mnglarora.interview.network.APIConfig.Q_PARAM
import dev.mnglarora.interview.network.APIConfig.SORT_PARAM
import dev.mnglarora.interview.network.APIConfig.getQParam
import dev.mnglarora.interview.network.response.GhRepoResponse
import dev.mnglarora.interview.network.utils.ApiResponse
import dev.mnglarora.interview.network.utils.Order
import dev.mnglarora.interview.network.utils.Sort
import dev.mnglarora.interview.network.utils.safeApiCall
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

class GhRepoServiceImpl(private val client: HttpClient) : GhRepoService {

    private val json = Json { ignoreUnknownKeys = true }
    override suspend fun getTrendingRepos(): ApiResponse<List<GhRepo>> = safeApiCall {
                val res = client.get {
                    url(APIConfig.apiSearchUrl)
                    parameter(SORT_PARAM, Sort.STAR)
                    parameter(ORDER_PARAM, Order.DESCENDING)
                    parameter(Q_PARAM, getQParam())
                    parameter(PER_PAGE_PARAM, PER_PAGE_VALUE)
                    parameter(PAGE_PARAM, PAGE_VALUE)
                }

                GhRepoResponse().apply {
                    items = json.parseToJsonElement(res.bodyAsText()).jsonObject["items"] as List<GhRepo>? //Null
                    message = items.toString()
                    isSuccessful = res.status == HttpStatusCode.OK
                }
            }

}