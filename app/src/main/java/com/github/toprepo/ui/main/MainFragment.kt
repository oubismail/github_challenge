package com.github.toprepo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.toprepo.R
import com.github.toprepo.adapters.RepoListAdapter
import com.github.toprepo.network.Result
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var repoListAdapter: RepoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repoListAdapter = RepoListAdapter()
        val mainViewModelFactory = MainViewModelFactory(context)
        viewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)
//        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.refresh()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        repoRecyclerView.adapter = repoListAdapter
        repoRecyclerView.layoutManager = LinearLayoutManager(context)
        repoRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.reposList.observe(viewLifecycleOwner, Observer { result ->
                when (result.status) {
                    Result.Status.SUCCESS -> result?.data?.items?.let {
                        repoRecyclerView.visibility = View.VISIBLE
                        repoListAdapter.show(it)
                    }
                    Result.Status.ERROR -> Toast.makeText(
                        context,
                        "Error network",
                        Toast.LENGTH_SHORT
                    ).show()
                    Result.Status.LOADING -> {
                        if (!swipeRefresh.isRefreshing)
                            progressBar.visibility = View.VISIBLE
                    }
                    Result.Status.COMPLETE -> {
                        swipeRefresh.isRefreshing = false
                        progressBar.visibility = View.GONE
                    }
                }

            })
        }
    }

}
