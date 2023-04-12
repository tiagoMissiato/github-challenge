package com.tiagomissiato.challenge.repository.presenter

import com.tiagomissiato.challenge.core.result.DataSourceResult
import com.tiagomissiato.challenge.repository.base.BaseTest
import com.tiagomissiato.challenge.repository.data.model.GitRepo
import com.tiagomissiato.challenge.repository.domain.GitRepoRepository
import com.tiagomissiato.challenge.repository.domain.RepositoryInteractorImpl
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryListViewModelTest: BaseTest() {

    @MockK
    lateinit var repository: GitRepoRepository

    @Test
    fun getGitRepository_Page1_Success() = runTest {

        //given
        coEvery { repository.getRepositoryList(1) } returns DataSourceResult.Success(mockGitRepoPage1())

        val interactor = RepositoryInteractorImpl(repository)

        val viewModel = RepositoryListViewModel(
            interactor,
            mainDispatcherRule.testDispatcher
        )

        collectUiStates(viewModel) { stateList ->
            //when
            viewModel.getPage(1)

            //then
            assertTrue(stateList[0] is RepositoryListUiState.Empty)
            assertTrue(stateList[1] is RepositoryListUiState.Loading)
            val stateSuccess = stateList[2]
            assertTrue(stateSuccess is RepositoryListUiState.Success)
            assertEquals((stateSuccess as RepositoryListUiState.Success).items.size, 2)
        }
    }

    @Test
    fun getGitRepository_Page2_Success() = runTest {
        //given
        coEvery { repository.getRepositoryList(2) } returns DataSourceResult.Success(mockGitRepoPage1())

        val interactor = RepositoryInteractorImpl(repository)

        val viewModel = RepositoryListViewModel(
            interactor,
            mainDispatcherRule.testDispatcher
        )

        collectUiStates(viewModel) { stateList ->
            //when
            viewModel.getPage(2)

            //then
            assertTrue(stateList[0] is RepositoryListUiState.Empty)
            assertTrue(stateList[1] is RepositoryListUiState.LoadingMore)
            val stateSuccess = stateList[2]
            assertTrue(stateSuccess is RepositoryListUiState.LoadMore)
            assertEquals((stateSuccess as RepositoryListUiState.LoadMore).items.size, 2)
        }
    }

    @Test
    fun getGitRepository_Page1_Error() = runTest {
        //given
        val cachedMockk = List(2) {
            mockk<GitRepo>()
        }
        coEvery { repository.getRepositoryList(1) } returns DataSourceResult.Error(cachedMockk, "Internet error")

        val interactor = RepositoryInteractorImpl(repository)

        val viewModel = RepositoryListViewModel(
            interactor,
            mainDispatcherRule.testDispatcher
        )

        collectUiStates(viewModel) { stateList ->
            //when
            viewModel.getPage(1)

            //then
            assertTrue(stateList[0] is RepositoryListUiState.Empty)
            assertTrue(stateList[1] is RepositoryListUiState.Loading)
            val stateError = stateList[2]
            assertTrue(stateError is RepositoryListUiState.Error)
            assertEquals((stateError as RepositoryListUiState.Error).cache.size, 2)
        }
    }

    @Test
    fun getGitRepository_Page2_Error() = runTest {
        //given
        val cachedMockk = List(2) {
            mockk<GitRepo>()
        }
        coEvery { repository.getRepositoryList(2) } returns DataSourceResult.Error(cachedMockk, "Internet error")

        val interactor = RepositoryInteractorImpl(repository)

        val viewModel = RepositoryListViewModel(
            interactor,
            mainDispatcherRule.testDispatcher
        )

        collectUiStates(viewModel) { stateList ->
            //when
            viewModel.getPage(2)

            //then
            assertTrue(stateList[0] is RepositoryListUiState.Empty)
            assertTrue(stateList[1] is RepositoryListUiState.LoadingMore)
            assertFalse(stateList.any { it is RepositoryListUiState.Loading })
            val stateError = stateList[2]
            assertTrue(stateError is RepositoryListUiState.Exception)
            assertEquals((stateError as RepositoryListUiState.Exception).message, "Internet error")
        }
    }

    @Test
    fun getGitRepository_Page1_Exception() = runTest {
        //given
        val cachedMockk = List(2) {
            mockk<GitRepo>()
        }
        coEvery { repository.getRepositoryList(1) } throws Exception("Exception")

        val interactor = RepositoryInteractorImpl(repository)

        val viewModel = RepositoryListViewModel(
            interactor,
            mainDispatcherRule.testDispatcher
        )

        collectUiStates(viewModel) { stateList ->
            //when
            viewModel.getPage(1)

            //then
            assertTrue(stateList[0] is RepositoryListUiState.Empty)
            assertTrue(stateList[1] is RepositoryListUiState.Loading)
            val stateError = stateList[2]
            assertTrue(stateError is RepositoryListUiState.Exception)
            assertEquals((stateError as RepositoryListUiState.Exception).message, "Exception")
        }
    }

    @Test
    fun getGitRepository_Page2_Exception() = runTest {
        //given
        val cachedMockk = List(2) {
            mockk<GitRepo>()
        }
        coEvery { repository.getRepositoryList(2) } throws Exception("Exception")

        val interactor = RepositoryInteractorImpl(repository)

        val viewModel = RepositoryListViewModel(
            interactor,
            mainDispatcherRule.testDispatcher
        )

        collectUiStates(viewModel) { stateList ->
            //when
            viewModel.getPage(2)

            //then
            assertTrue(stateList[0] is RepositoryListUiState.Empty)
            assertTrue(stateList[1] is RepositoryListUiState.LoadingMore)
            assertFalse(stateList.any { it is RepositoryListUiState.Loading })
            val stateError = stateList[2]
            assertTrue(stateError is RepositoryListUiState.Exception)
            assertEquals((stateError as RepositoryListUiState.Exception).message, "Exception")
        }
    }


 fun mockGitRepoPage1() =
     List(2) { mockk<GitRepo>() }

}

@OptIn(ExperimentalCoroutinesApi::class)
fun TestScope.collectUiStates(vm: RepositoryListViewModel, block: (stateList: List<RepositoryListUiState>) -> Unit) {
    val listState = mutableListOf<RepositoryListUiState>()
    val collectJob =
        launch(UnconfinedTestDispatcher()) { vm.uiState.toList(listState) }
    block(listState)
    collectJob.cancel()
}