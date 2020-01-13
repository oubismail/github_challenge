package com.github.toprepo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.toprepo.models.GitHubApiResult
import com.github.toprepo.network.GitHubApiRepo
import com.github.toprepo.network.Result

class MainViewModel : ViewModel() {
    fun getTopStarredRepos(): LiveData<Result<GitHubApiResult>> {
        return GitHubApiRepo.getTopStarredRepos()
    }
}
