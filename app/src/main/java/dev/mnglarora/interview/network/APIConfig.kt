package dev.mnglarora.interview.network

import java.text.SimpleDateFormat
import java.util.*

object APIConfig {

    const val apiSearchUrl = "https://api.github.com/search/repositories"
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    const val Q_PARAM = "q"
    const val PAGE_PARAM = "page"
    const val PAGE_VALUE = 1
    const val PER_PAGE_PARAM = "per_page"
    const val PER_PAGE_VALUE = 25
    const val SORT_PARAM = "sort"
    const val ORDER_PARAM = "order"
    fun getQParam() =
        "created:>${simpleDateFormat.format(Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000L))}"

}