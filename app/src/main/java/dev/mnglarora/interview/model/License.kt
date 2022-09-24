package dev.mnglarora.interview.model


@kotlinx.serialization.Serializable
data class License(
    val key: String? = null,
    val name: String? = null,
    val node_id: String? = null,
    val spdx_id: String? = null,
    val url: String? = null
)