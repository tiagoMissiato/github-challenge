package com.tiagomissiato.challenge.core.network.model.response

import com.squareup.moshi.Json

open class SearchResponse<T>(
    val totalCount: Int = 0,
    val incompleteResult: Boolean = false,
    val items: List<T> = emptyList()
)
