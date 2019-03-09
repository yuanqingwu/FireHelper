package com.wyq.firehelper.kotlin.mvpGitHub.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wyq.firehelper.kotlin.R
import com.wyq.firehelper.kotlin.mvpGitHub.model.Repository
import com.wyq.firehelper.kotlin.mvpGitHub.model.RepositoryDetail
import kotlinx.android.synthetic.main.kotlin_activity_mvp_github_recycler_view_item.view.*

class GitHubRepoAdapter(private val repos: MutableList<Repository>) : androidx.recyclerview.widget.RecyclerView.Adapter<GitHubRepoAdapter.ViewHolder>() {

     interface OnItemClickListener {
        fun onItemCLick()
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

//    var repoDetail: RepositoryDetail? = null
    var repoDetails:MutableMap<Long,RepositoryDetail>? = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context).inflate(R.layout.kotlin_activity_mvp_github_recycler_view_item, parent, false).let {
            ViewHolder(it)
        }
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(repos[position],repoDetails)
        holder.itemView.setOnClickListener(View.OnClickListener {
            listener?.onItemCLick()
        })
    }

    class ViewHolder(repItemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(repItemView) {

        fun bindData(repository: Repository,repoDetails: MutableMap<Long,RepositoryDetail>?) {
            with(repository) {
                itemView.kotlin_activity_mvp_github_rv_title.text = repository.name
                itemView.kotlin_activity_mvp_github_rv_desc.text = repository.description
            }
            if(repoDetails?.get(repository.id)!=null){
                with(repoDetails.get(repository.id)){
                    itemView.kotlin_activity_mvp_github_rv_star.text = this?.stargazers_count
                    itemView.kotlin_activity_mvp_github_rv_fork.text = this?.forks_count
                }
            }
        }
    }

    fun refreshRepo(repositories: MutableList<Repository>) {
        repos.clear()
        repos.addAll(repositories)
        notifyDataSetChanged()
    }

    fun refreshRepoDetail(id: Long, repoDetail: RepositoryDetail) {
//        this.repoDetail = repoDetail
        repoDetails?.put(id,repoDetail)
        notifyDataSetChanged()
    }
}