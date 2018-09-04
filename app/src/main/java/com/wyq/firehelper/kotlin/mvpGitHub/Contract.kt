package com.wyq.firehelper.kotlin.mvpGitHub

import com.wyq.firehelper.kotlin.mvpGitHub.model.Repository
import com.wyq.firehelper.kotlin.mvpGitHub.model.RepositoryDetail

object Contract {

    interface IView{
        fun showRepository(repositories : MutableList<Repository>)
        fun showRepositoryFailed(error : String?)
        fun showRepositoryDetail(id:Long,repoDetail:RepositoryDetail)
    }


}