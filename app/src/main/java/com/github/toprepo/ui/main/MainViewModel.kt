package com.github.toprepo.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.github.toprepo.models.GitHubApiResult
import com.github.toprepo.network.GitHubApiRepo
import com.github.toprepo.network.Result
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MainViewModel(context: Context) : ViewModel(), KodeinAware {

    private val reloadTrigger = MutableLiveData<Boolean>()
    private val gitHubRepo : GitHubApiRepo by  instance()

    val reposList: LiveData<Result<GitHubApiResult>> = Transformations.switchMap(reloadTrigger) {
        gitHubRepo.getTopStarredRepos()
    }

    fun refresh(){
        reloadTrigger.value = true
    }

    override val kodein: Kodein by  closestKodein(context)
}
