package com.github.toprepo.network

import androidx.lifecycle.LiveData
import com.github.toprepo.models.GitHubApiResult
import java.text.SimpleDateFormat
import java.util.*

class GitHubApiRepo : BaseDataSource() {

    private val service: GithubService = GitHubAPI.create();

    fun getTopStarredRepos(page: Int = 0): LiveData<Result<GitHubApiResult>> {
        val option = hashMapOf(
            "sort" to "stars",
            "order" to "desc",
            "q" to "created:>${getDate()}",
            "page" to page.toString()
        )
        return getResult { service.getTopRepos(option) }
    }

    private fun getDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -30)
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(calendar.time)
    }
}