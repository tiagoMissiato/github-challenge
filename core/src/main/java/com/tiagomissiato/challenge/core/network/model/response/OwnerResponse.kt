package com.tiagomissiato.challenge.core.network.model.response

import com.squareup.moshi.Json

data class OwnerResponse(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "login")
    val login: String,
    @field:Json(name = "avatar_url")
    val avatarUrl: String,
)