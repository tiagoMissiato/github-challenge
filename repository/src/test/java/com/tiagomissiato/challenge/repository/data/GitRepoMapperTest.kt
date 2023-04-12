package com.tiagomissiato.challenge.repository.data

import com.tiagomissiato.challenge.core.mock.GitRepoDataMock
import com.tiagomissiato.challenge.repository.base.BaseTest
import com.tiagomissiato.challenge.repository.data.mapper.toDto
import com.tiagomissiato.challenge.repository.data.mapper.toEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class GitRepoMapperTest: BaseTest() {

    @Test
    fun convert_Response_toDto() {
        val list = GitRepoDataMock.getGitRepoResponse(2)
        val dtoList = list.map { it.toDto() }

        for (i in list.indices) {
            val response = list[i]
            val dto = dtoList[i]

            assertEquals(response.id, dto.id)
            assertEquals(response.name,dto.name)
            assertEquals(response.fullName,dto.fullName)
            assertEquals(response.description,dto.description)
            assertEquals(response.url,dto.url)
            assertEquals(response.starCount,dto.starCount)
            assertEquals(response.forkCount,dto.forkCount)
            assertEquals(response.owner.login, dto.owner.name)
            assertEquals(response.owner.login, dto.owner.login)
            assertEquals(response.owner.avatarUrl, dto.owner.avatarUrl)
            assertEquals(response.license?.name, dto.license?.name)
            assertEquals(response.license?.spdxId, dto.license?.spdxId)
        }
    }

    @Test
    fun convert_Response_toEntity() {
        val list = GitRepoDataMock.getGitRepoResponse(2)
        val entityList = list.map { it.toEntity() }

        for (i in list.indices) {
            val response = list[i]
            val entity = entityList[i]

            assertEquals(response.id, entity.id)
            assertEquals(response.name,entity.name)
            assertEquals(response.fullName,entity.fullName)
            assertEquals(response.description,entity.description)
            assertEquals(response.url,entity.url)
            assertEquals(response.starCount,entity.starCount)
            assertEquals(response.forkCount,entity.forkCount)
            assertEquals(response.owner.login, entity.ownerName)
            assertEquals(response.owner.login, entity.ownerName)
            assertEquals(response.owner.avatarUrl, entity.ownerAvatarUrl)
            assertEquals(response.license?.name, entity.licenseName)
            assertEquals(response.license?.spdxId, entity.licenseName)
        }
    }

    @Test
    fun convert_Entity_toDto() {
        val list = GitRepoDataMock.getGitRepoEntity(2)
        val dtoList = list.map { it.toDto() }

        for (i in list.indices) {
            val response = list[i]
            val dto = dtoList[i]

            assertEquals(response.id, dto.id)
            assertEquals(response.name,dto.name)
            assertEquals(response.fullName,dto.fullName)
            assertEquals(response.description,dto.description)
            assertEquals(response.url,dto.url)
            assertEquals(response.starCount,dto.starCount)
            assertEquals(response.forkCount,dto.forkCount)
            assertEquals(response.ownerName,dto.owner.login)
            assertEquals(response.ownerName,dto.owner.name)
            assertEquals(response.ownerAvatarUrl,dto.owner.avatarUrl)
            assertEquals(response.licenseName, dto.license?.name)
            assertEquals(response.licenseName, dto.license?.spdxId)
        }
    }

}