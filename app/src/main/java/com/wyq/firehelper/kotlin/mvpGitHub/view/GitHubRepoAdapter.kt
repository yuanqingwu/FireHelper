package com.wyq.firehelper.kotlin.mvpGitHub.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wyq.firehelper.R
import com.wyq.firehelper.kotlin.mvpGitHub.model.Repository
import kotlinx.android.synthetic.main.kotlin_activity_mvp_github_recycler_view_item.view.*

class GitHubRepoAdapter(private val repos :MutableList<Repository>):RecyclerView.Adapter<GitHubRepoAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent?.context).inflate(R.layout.kotlin_activity_mvp_github_recycler_view_item,parent,false).let {
            ViewHolder(it)
        }
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(repos[position])
    }


    class ViewHolder(repItemView: View): RecyclerView.ViewHolder(repItemView) {

        fun bindData(repository: Repository){
            with(repository){
                itemView.kotlin_activity_mvp_github_rv_title.text = repository.name
                itemView.kotlin_activity_mvp_github_rv_desc.text = repository.description
            }
        }
    }

    fun refresh(repositories: MutableList<Repository>){
        repos.clear()
        repos.addAll(repositories)
        notifyDataSetChanged()
    }
}