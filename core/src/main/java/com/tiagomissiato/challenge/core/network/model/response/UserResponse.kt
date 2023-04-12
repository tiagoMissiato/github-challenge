package com.tiagomissiato.challenge.core.network.model.response

import com.squareup.moshi.Json

data class UserResponse(
    @field:Json(name = "login")
    val login: String,
    @field:Json(name = "name")
    val name: String
)