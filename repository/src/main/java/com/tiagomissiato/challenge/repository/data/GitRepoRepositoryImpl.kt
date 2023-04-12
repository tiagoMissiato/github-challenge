package com.tiagomissiato.challenge.repository.data

import android.util.Log
import com.tiagomissiato.challenge.core.database.dao.GitRepoDao
import com.tiagomissiato.challenge.core.network.api.GitApiService
import com.tiagomissiato.challenge.core.result.DataSourceResult
import com.tiagomissiato.challenge.repository.data.mapper.toDto
import com.tiagomissiato.challenge.repository.data.mapper.toEntity
import com.tiagomissiato.challenge.repository.data.model.GitRepo
import com.tiagomissiato.challenge.repository.domain.GitRepoRepository
import com.tiagomissiato.wheredidistop.core.network.adapter.NetworkResult
import javax.inject.Inject

class GitRepoRepositoryImpl @Inject constructor(
    private val remoteDataSource: GitApiService,
    private val localDataSource: GitRepoDao
): GitRepoRepository {

    override suspend fun getRepositoryList(page: Int): DataSourceResult<List<GitRepo>> {
        val cachedList = getLocalGitRepoList()
        val list = when(val response = remoteDataSource.searchGitRepo(page)) {
            is NetworkResult.Error -> {
                DataSourceResult.Error(cachedList, "Erro de internet")
            }
            is NetworkResult.Exception -> {
                DataSourceResult.Error(cachedList, "Erro de internet")
            }
            is NetworkResult.Success -> {
                val entityList = response.data.items.map { it.toEntity() }
                //first page need to reset cache
                if (page == 1) localDataSource.deleteAll()
                localDataSource.insertAll(entityList)
                //Assure the Single Source of truth
                DataSourceResult.Success(getLocalGitRepoList())
            }
        }

        return list
    }

    private suspend fun getLocalGitRepoList() =
        localDataSource.getAll().map { it.toDto() }

}