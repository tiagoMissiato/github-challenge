package com.tiagomissiato.challenge.repository.domain

import com.tiagomissiato.challenge.core.result.DataSourceResult
import com.tiagomissiato.challenge.repository.data.model.GitRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface RepositoryInteractor {
    fun getRepositoryList(page: Int): Flow<DataSourceResult<List<GitRepo>>>
}

class RepositoryInteractorImpl @Inject constructor(
    private val repoRepository: GitRepoRepository
): RepositoryInteractor {

    override fun getRepositoryList(page: Int) = flow {
        emit(repoRepository.getRepositoryList(page))
    }

}