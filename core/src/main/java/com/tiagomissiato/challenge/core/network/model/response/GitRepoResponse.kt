package com.tiagomissiato.challenge.core.network.model.response

import com.squareup.moshi.Json

data class GitRepoResponse(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "full_name")
    val fullName: String,
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "url")
    val url: String,
    @field:Json(name = "stargazers_count")
    val starCount: Int,
    @field:Json(name = "forks_count")
    val forkCount: Int,
    @field:Json(name = "owner")
    val owner: OwnerResponse,
    @field:Json(name = "license")
    val license: LicenseResponse? = null
)