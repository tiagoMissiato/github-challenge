package com.tiagomissiato.challenge.repository.data

import com.tiagomissiato.challenge.core.database.dao.GitRepoDao
import com.tiagomissiato.challenge.core.database.entity.GitRepoEntity
import com.tiagomissiato.challenge.core.network.api.GitApiService
import com.tiagomissiato.challenge.core.network.model.response.SearchGitRepoResponse
import com.tiagomissiato.challenge.core.result.DataSourceResult
import com.tiagomissiato.challenge.repository.data.mapper.toDto
import com.tiagomissiato.challenge.repository.data.mapper.toEntity
import com.tiagomissiato.wheredidistop.core.network.adapter.NetworkResult
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GitRepositoryTest {

    @MockK
    lateinit var remoteDataSource: GitApiService

    @MockK
    lateinit var localDataSource: GitRepoDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mockkStatic("com.tiagomissiato.challenge.repository.data.mapper.GitRepoMapperKt")
    }

    @Test
    fun getGitList_Page1_Success() = runTest {
        //given
        coEvery { remoteDataSource.searchGitRepo(1) } returns NetworkResult.Success( mockSuccessRemoteResponse() )
        coEvery { localDataSource.getAll() } returns emptyList()

        coJustRun { localDataSource.deleteAll() }
        coJustRun { localDataSource.insertAll(any()) }

        //when
        val repo = GitRepoRepositoryImpl(remoteDataSource, localDataSource)
        val response = repo.getRepositoryList(1)

        //then
        coVerify(exactly = 1) { localDataSource.deleteAll() }
        coVerify(exactly = 1) { localDataSource.insertAll(any()) }

        assertTrue(response is DataSourceResult.Success)
    }

    @Test
    fun getGitList_Page2_Success() = runTest {
        //given
        coEvery { remoteDataSource.searchGitRepo(2) } returns NetworkResult.Success( mockSuccessRemoteResponse() )
        coEvery { localDataSource.getAll() } returns emptyList()

        coJustRun { localDataSource.insertAll(any()) }

        //when
        val repo = GitRepoRepositoryImpl(remoteDataSource, localDataSource)
        val response = repo.getRepositoryList(2)

        //then
        coVerify(exactly = 0) { localDataSource.deleteAll() }
        coVerify(exactly = 1) { localDataSource.insertAll(any()) }

        assertTrue(response is DataSourceResult.Success)
    }

    @Test
    fun getGitList_Page1_Error() = runTest {
        //given
        coEvery { remoteDataSource.searchGitRepo(1) } returns NetworkResult.Error( 0, "Error" )
        coEvery { localDataSource.getAll() } returns List(2) {
            mockk {
                every { toDto() } returns mockk()
            }
        }

        coJustRun { localDataSource.deleteAll() }
        coJustRun { localDataSource.insertAll(any()) }

        //when
        val repo = GitRepoRepositoryImpl(remoteDataSource, localDataSource)
        val response = repo.getRepositoryList(1)

        //then
        coVerify(exactly = 0) { localDataSource.deleteAll() }
        coVerify(exactly = 0) { localDataSource.insertAll(any()) }

        assertTrue(response is DataSourceResult.Error)
        assertEquals((response as DataSourceResult.Error).data.size, 2)
    }

    fun mockSuccessRemoteResponse() =
        mockk<SearchGitRepoResponse> {
            every { items } returns List(2) {
                mockk {
                    every { name } returns "Mocked Name $it"
                    every { toDto() } returns mockk()
                    every { toEntity() } returns mockk()
                }
            }
        }

}