package com.github.toprepo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.toprepo.R
import com.github.toprepo.models.Item
import kotlinx.android.synthetic.main.repo_item.view.*

class RepoListAdapter : RecyclerView.Adapter<RepoListAdapter.RepoListViewHolder>() {

    private val data = mutableListOf<Item>()

    class RepoListViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repo_item, parent, false) as View
        return RepoListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) {
        val item = data[position]
        holder.itemView.repoName.text = item.name
        holder.itemView.repoDescription.text = item.description
        if (item.description?.isNotEmpty() == true) {
            holder.itemView.repoDescription.text = item.description
        } else {
            holder.itemView.repoDescription.visibility = View.GONE
        }
        holder.itemView.repoUserName.text = item.owner.login
        holder.itemView.repoStarCount.text = item.stargazersCount?.toString() ?: "0"
    }

    fun show(repos: List<Item>) {
        data.clear()
        data.addAll(repos)
        notifyDataSetChanged()
    }
}