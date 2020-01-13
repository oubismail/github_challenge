package com.github.toprepo.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.github.toprepo.R
import com.github.toprepo.network.GitHubAPI
import com.github.toprepo.network.GitHubApiRepo
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getTopStarredRepos().observe(viewLifecycleOwner, Observer { result ->
                run {
                    result?.data?.items?.forEach {
                        Log.e("My TAG", "Name is  : ${it.name}")
                    }
                }
            })
        }
    }

}
