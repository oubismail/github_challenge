package com.github.toprepo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.github.toprepo.models.GitHubApiResult
import com.github.toprepo.network.GitHubApiRepo
import com.github.toprepo.network.Result

class MainViewModel : ViewModel() {

    private val reloadTrigger = MutableLiveData<Boolean>()

    val reposList: LiveData<Result<GitHubApiResult>> = Transformations.switchMap(reloadTrigger) {
        GitHubApiRepo.getTopStarredRepos()
    }

    private fun getTopStarredRepos(): LiveData<Result<GitHubApiResult>> {
        return GitHubApiRepo.getTopStarredRepos()
    }

    fun refresh(){
        reloadTrigger.value = true
    }
}
