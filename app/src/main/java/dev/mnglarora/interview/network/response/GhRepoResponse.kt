package dev.mnglarora.interview.network.response

import dev.mnglarora.interview.model.GhRepo

@kotlinx.serialization.Serializable
data class GhRepoResponse(
    val incomplete_results: Boolean = false,
    val total_count: Int = 0,
) : BaseResponse<List<GhRepo>>()