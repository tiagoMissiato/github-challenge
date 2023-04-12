package com.tiagomissiato.challenge.core.mock

import com.tiagomissiato.challenge.core.database.entity.GitRepoEntity
import com.tiagomissiato.challenge.core.network.model.response.GitRepoResponse
import com.tiagomissiato.challenge.core.network.model.response.LicenseResponse
import com.tiagomissiato.challenge.core.network.model.response.OwnerResponse
import kotlin.random.Random
import kotlin.random.nextInt

object GitRepoDataMock {

    fun getGitRepoResponse(numItems: Int) = List(numItems) {
        GitRepoResponse(
            id = it,
            name = "Repo Name $it",
            fullName = "Repo Full Name $it",
            description = "Description $it",
            url = "url/$it",
            starCount = Random.nextInt(0..1000),
            forkCount = Random.nextInt(0..1000),
            OwnerResponse(
                id = it,
                login = "ownerLogin$it",
                avatarUrl = "owner/avatar/url/$it"
            ),
            license = LicenseResponse(
                name = "MIT$it",
                spdxId = "MIT$it"
            )
        )
    }

    fun getGitRepoEntity(numItems: Int) = List(numItems) {
        GitRepoEntity(
            id = it,
            name = "Repo Name $it",
            fullName = "Repo Full Name $it",
            description = "Description $it",
            url = "url/$it",
            starCount = Random.nextInt(0..1000),
            forkCount = Random.nextInt(0..1000),
            ownerName = "Owner name $it",
            ownerAvatarUrl = "avatar/url/$it",
            licenseName = "MIT - $it"
        )
    }

}