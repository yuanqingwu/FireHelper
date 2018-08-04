package com.wyq.firehelper.kotlin.mvpGitHub

import com.wyq.firehelper.kotlin.mvpGitHub.model.Repository

object Contract {

    interface IView{
        fun showRepository(repositories : MutableList<Repository>)
        fun showRepositoryFailed(error : String?)
    }


}