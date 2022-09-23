package dev.mnglarora.interview.network

import dev.mnglarora.interview.model.GhRepo
import dev.mnglarora.interview.network.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface GhRepoService {

    suspend fun getTrendingRepos() : ApiResponse<List<GhRepo>>

}