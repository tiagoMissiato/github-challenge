package com.tiagomissiato.challenge.repository.data.mapper

import com.tiagomissiato.challenge.core.database.entity.GitRepoEntity
import com.tiagomissiato.challenge.core.network.model.response.GitRepoResponse
import com.tiagomissiato.challenge.core.network.model.response.LicenseResponse
import com.tiagomissiato.challenge.core.network.model.response.OwnerResponse
import com.tiagomissiato.challenge.repository.data.model.GitRepo
import com.tiagomissiato.challenge.repository.data.model.License
import com.tiagomissiato.challenge.repository.data.model.Owner

fun GitRepoResponse.toDto() = GitRepo(
    id = this.id,
    name = this.name,
    fullName = this.fullName,
    description = this.description,
    url = this.url,
    starCount = this.starCount,
    forkCount = this.forkCount,
    owner = this.owner.toDto(),
    license = this.license?.toDto()
)

fun OwnerResponse.toDto() = Owner(
    name = this.login,
    login = this.login,
    avatarUrl = this.avatarUrl
)

fun LicenseResponse.toDto() = License (
    name = this.name,
    spdxId = this.spdxId
)

fun GitRepoEntity.toDto() = GitRepo(
    id = this.id,
    name = this.name,
    fullName = this.fullName,
    description = this.description,
    url = this.url,
    starCount = this.starCount,
    forkCount = this.forkCount,
    owner = Owner(
        name = this.ownerName,
        login = this.ownerName,
        avatarUrl = this.ownerAvatarUrl
    ),
    license = if (this.licenseName.isEmpty()) null else License(
        name = this.licenseName,
        spdxId = this.licenseName,
    )
)

fun GitRepoResponse.toEntity() = GitRepoEntity(
    id = this.id,
    name = this.name,
    fullName = this.fullName,
    description = this.description,
    url = this.url,
    starCount = this.starCount,
    forkCount = this.forkCount,
    ownerName = this.owner.login,
    ownerAvatarUrl = this.owner.avatarUrl,
    licenseName = this.license?.spdxId ?: ""
)