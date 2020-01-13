package com.github.toprepo.network

import androidx.lifecycle.LiveData
import com.github.toprepo.models.GitHubApiResult

object GitHubApiRepo : BaseDataSource() {

    private val service: GithubService = GitHubAPI.create();

    fun getTopStarredRepos(page: Int = 0): LiveData<Result<GitHubApiResult>> {
        val option = hashMapOf(
            "sort" to "stars",
            "order" to "desc",
            "q" to "created:>2020-01-12",
            "page" to page.toString()
        )
        return getResult { service.getTopRepos(option) }
    }
}