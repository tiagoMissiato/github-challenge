package com.tiagomissiato.challenge.repository.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiagomissiato.challenge.core.result.DataSourceResult
import com.tiagomissiato.challenge.repository.data.model.GitRepo
import com.tiagomissiato.challenge.repository.domain.RepositoryInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

sealed class RepositoryListUiState {
    object Loading: RepositoryListUiState()
    object LoadingMore: RepositoryListUiState()
    data class Error(val cache: List<GitRepo>, val message: String?): RepositoryListUiState()
    data class Exception(val message: String?): RepositoryListUiState()
    object Empty: RepositoryListUiState()
    data class Success(val items: List<GitRepo>): RepositoryListUiState()
    data class LoadMore(val items: List<GitRepo>): RepositoryListUiState()
}

@HiltViewModel
class RepositoryListViewModel @Inject constructor(
    private val interactor: RepositoryInteractor,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private var _uiState = MutableStateFlow<RepositoryListUiState>(RepositoryListUiState.Empty)
    val uiState: StateFlow<RepositoryListUiState>
        get() = _uiState

    private var searchJob: Job? = null

    fun getRefreshGitRepoList() {
        getGitRepoList()
    }

    private fun getGitRepoList(page: Int = 1) {
        //assure search to happen only once at a time
        searchJob?.cancel()

        searchJob = interactor.getRepositoryList(page)
            .flowOn(ioDispatcher)
            .onStart {
                if (page == 1) {
                    _uiState.value = RepositoryListUiState.Loading
                } else {
                    _uiState.value = RepositoryListUiState.LoadingMore
                }
            }
            .onEmpty {
                if (page == 1) _uiState.value = RepositoryListUiState.Empty
            }
            .onEach {
                when(it) {
                    is DataSourceResult.Error -> {
                        _uiState.value = if (page == 1) {
                            RepositoryListUiState.Error(it.data, it.message)
                        } else {
                            RepositoryListUiState.Exception(it.message)
                        }
                    }
                    is DataSourceResult.Success -> {
                        _uiState.value = if (page == 1) {
                            RepositoryListUiState.Success(it.data)
                        } else {
                            RepositoryListUiState.LoadMore(it.data)
                        }
                    }
                }
            }
            .catch {
                _uiState.value = RepositoryListUiState.Exception(it.message)
            }
            .launchIn(viewModelScope)
    }

    fun getPage(page: Int = 1) {
        getGitRepoList(page)
    }
}