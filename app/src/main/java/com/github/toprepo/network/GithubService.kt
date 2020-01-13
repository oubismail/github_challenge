package com.github.toprepo.network

import com.github.toprepo.models.GitHubApiResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GithubService {
    @GET("repositories")
    suspend fun getTopRepos(
        @QueryMap options: HashMap<String, String>) : Response<GitHubApiResult>
}