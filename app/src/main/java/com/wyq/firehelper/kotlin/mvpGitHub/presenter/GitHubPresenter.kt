package com.wyq.firehelper.kotlin.mvpGitHub.presenter

import com.wyq.firehelper.kotlin.mvpGitHub.Contract
import com.wyq.firehelper.kotlin.mvpGitHub.net.GithubServiceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GitHubPresenter @Inject constructor() :BasePresenter<Contract.IView>() {

    companion object {
        private val defaultUser="wuyuanqing527"
        private val defaultType = "public"
    }

    fun loadRepositories(repoUser:String?){

        GithubServiceFactory.makeRetrofit().getUserRepos(checkUserNull(repoUser),defaultType,"updated")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.showRepository(it)
                },{
                    view?.showRepositoryFailed(it.message)
                })
    }


    private fun checkUserNull(repoUser: String?) :String{
        if(repoUser == null || repoUser.length == 0){
            return defaultUser
        }
        return repoUser
    }
}