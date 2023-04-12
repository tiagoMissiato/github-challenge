package com.tiagomissiato.challenge.core.network.api

import com.tiagomissiato.challenge.core.network.model.response.SearchGitRepoResponse
import com.tiagomissiato.challenge.core.network.model.response.UserResponse
import com.tiagomissiato.wheredidistop.core.network.adapter.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitApiService {

    @GET("/search/repositories?q=language:kotlin&sort=stars&per_page=10")
    suspend fun searchGitRepo(@Query("page") page: Int): NetworkResult<SearchGitRepoResponse>

    @GET("/users/{login}")
    suspend fun getUser(@Path("login") page: String): NetworkResult<UserResponse>

}