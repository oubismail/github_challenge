package com.github.toprepo

import android.app.Application
import com.github.toprepo.network.GitHubApiRepo
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class MyApp : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        bind<GitHubApiRepo>() with singleton { GitHubApiRepo()}
    }
}