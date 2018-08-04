package com.wyq.firehelper.kotlin.mvpGitHub.view

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import com.wyq.firehelper.R
import com.wyq.firehelper.activity.BaseActivity
import com.wyq.firehelper.kotlin.mvpGitHub.Contract
import com.wyq.firehelper.kotlin.mvpGitHub.injection.component.DaggerPresenterComponent
import com.wyq.firehelper.kotlin.mvpGitHub.model.Repository
import com.wyq.firehelper.kotlin.mvpGitHub.presenter.GitHubPresenter
import kotlinx.android.synthetic.main.kotlin_activity_mvp_github_layout.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class GitHubMainActivity : BaseActivity(), Contract.IView {


    @Inject
    lateinit var presenter: GitHubPresenter

    var repoAdapter: GitHubRepoAdapter? = null
    var searchView :SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kotlin_activity_mvp_github_layout)
        toolbar.title = "GitHub"
        setSupportActionBar(toolbar)


        val presenterComponent = DaggerPresenterComponent.builder().build()
        presenterComponent.inject(this)
        presenter.attachView(this)
        loadRepositories("wuyuanqing527")
    }

    fun loadRepositories(name: String) {
        presenter.loadRepositories(name);
        kotlin_activity_mvp_github_progress_bar.visibility = View.VISIBLE
    }

    private fun closeSearchView(){
        searchView?.setQuery("",false)
        searchView?.onActionViewCollapsed()
    }

    override fun showRepository(repositories: MutableList<Repository>) {
        repoAdapter = GitHubRepoAdapter(repositories)
        kotlin_activity_mvp_github_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        kotlin_activity_mvp_github_recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        kotlin_activity_mvp_github_recycler_view.adapter = repoAdapter

        kotlin_activity_mvp_github_progress_bar.visibility = View.GONE
        closeSearchView()
    }

    override fun showRepositoryFailed(error: String?) {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

         searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView

        searchView!!.queryHint = "GitHub仓库搜索"
//        val et = searchView.findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
//        et.hint = "请输入用户名"
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView!!.clearFocus()
                if (query != null)
                    loadRepositories(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }
}