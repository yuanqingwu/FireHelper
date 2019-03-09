package com.wyq.firehelper.kotlin.mvpGitHub.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.wyq.firehelper.base.BaseActivity
import com.wyq.firehelper.base.R2.id.toolbar
import com.wyq.firehelper.base.navigation.NavigationManager
import com.wyq.firehelper.kotlin.R
import com.wyq.firehelper.kotlin.mvpGitHub.Contract
import com.wyq.firehelper.kotlin.mvpGitHub.model.Repository
import com.wyq.firehelper.kotlin.mvpGitHub.model.RepositoryDetail
import com.wyq.firehelper.kotlin.mvpGitHub.presenter.GitHubPresenter
import kotlinx.android.synthetic.main.kotlin_activity_mvp_github_layout.*
import javax.inject.Inject

@Route(path = NavigationManager.NAVIGATION_KOTLIN_MAIN_ACTIVITY)
class GitHubMainActivity : BaseActivity(), Contract.IView {

    override fun attachLayoutRes(): Int {
        return R.layout.kotlin_activity_mvp_github_layout
    }

    override fun initToolBar() {
        initToolBar(findViewById<Toolbar>(toolbar), "GitHub", true)
    }

    override fun initView() {
    }

    @Inject
    lateinit var presenter: GitHubPresenter

    var repoAdapter: GitHubRepoAdapter? = null
    var searchView: SearchView? = null
    var repoUserName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val presenterComponent = DaggerPresenterComponent.builder().build()
//        presenterComponent.inject(this)
        presenter.attachView(this)
        loadRepositories("wuyuanqing527")
    }

    fun loadRepositories(name: String) {
        presenter.loadRepositories(name);
        repoUserName = name
        kotlin_activity_mvp_github_progress_bar.visibility = View.VISIBLE
    }

    private fun closeSearchView() {
        searchView?.setQuery("", false)
        searchView?.onActionViewCollapsed()
    }

    override fun showRepository(repositories: MutableList<Repository>) {
        repoAdapter = GitHubRepoAdapter(repositories)
        kotlin_activity_mvp_github_recycler_view.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        kotlin_activity_mvp_github_recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        kotlin_activity_mvp_github_recycler_view.adapter = repoAdapter

        kotlin_activity_mvp_github_progress_bar.visibility = View.GONE
        closeSearchView()
        findViewById<Toolbar>(toolbar).title = repoUserName
    }

    override fun showRepositoryFailed(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        kotlin_activity_mvp_github_progress_bar.visibility = View.GONE
        closeSearchView()
    }

    override fun showRepositoryDetail(id: Long, repoDetail: RepositoryDetail) {
        repoAdapter!!.refreshRepoDetail(id, repoDetail)
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

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onDestroy() {
        searchView?.cancelPendingInputEvents()
        searchView = null
        super.onDestroy()
    }

    fun instance(context: Context) {
        startActivity(Intent(context,
                GitHubMainActivity::class.java))
    }
}