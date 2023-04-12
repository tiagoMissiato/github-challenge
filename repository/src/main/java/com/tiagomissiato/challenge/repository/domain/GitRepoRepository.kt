package com.tiagomissiato.challenge.repository.domain

import com.tiagomissiato.challenge.core.result.DataSourceResult
import com.tiagomissiato.challenge.repository.data.model.GitRepo

interface GitRepoRepository {
    suspend fun getRepositoryList(page: Int): DataSourceResult<List<GitRepo>>
}