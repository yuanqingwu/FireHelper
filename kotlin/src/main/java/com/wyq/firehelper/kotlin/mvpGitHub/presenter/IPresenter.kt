package com.wyq.firehelper.kotlin.mvpGitHub.presenter

import com.wyq.firehelper.kotlin.mvpGitHub.Contract

interface IPresenter<in v:Contract.IView> {
    fun attachView(view:v)
    fun detachView()
}