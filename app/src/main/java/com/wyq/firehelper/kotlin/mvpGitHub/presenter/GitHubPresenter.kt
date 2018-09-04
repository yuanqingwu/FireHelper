package com.wyq.firehelper.kotlin.mvpGitHub.presenter

import com.wyq.firehelper.kotlin.mvpGitHub.Contract
import com.wyq.firehelper.kotlin.mvpGitHub.model.Repository
import com.wyq.firehelper.kotlin.mvpGitHub.net.GithubServiceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GitHubPresenter @Inject constructor() :BasePresenter<Contract.IView>() {

    companion object {
        private val defaultUser="wuyuanqing527"
        private val defaultType = "public"
        private val defaultRepoName = "FireHelper"
    }

    fun loadRepositories(repoUser:String?){
        GithubServiceFactory.makeRetrofit().getUserRepos(checkUserNull(repoUser),defaultType,"updated")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.showRepository(it)
                    loadRepositoryDetail(repoUser,it)
                },{
                    view?.showRepositoryFailed(it.message)
                })
    }

    fun loadRepositoryDetail(repoUser: String?,repos:MutableList<Repository>){

        repos.onEach {
            var id = it.id
            GithubServiceFactory.makeRetrofit().getRepository(checkUserNull(repoUser),checkRepoNameNull(it.name))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Consumer {
                        view?.showRepositoryDetail(id,it)
                    }, Consumer {

                    })
        }

    }

    private fun checkUserNull(repoUser: String?) :String{
        if(repoUser == null || repoUser.length == 0){
            return defaultUser
        }
        return repoUser
    }

    private fun checkRepoNameNull(repoName: String?) :String{
        if(repoName == null || repoName.length == 0){
            return defaultRepoName
        }
        return repoName
    }
}