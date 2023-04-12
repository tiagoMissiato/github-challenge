package com.tiagomissiato.challenge.core.network.model.response

import com.squareup.moshi.Json

data class LicenseResponse(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "spdx_id")
    val spdxId: String
)