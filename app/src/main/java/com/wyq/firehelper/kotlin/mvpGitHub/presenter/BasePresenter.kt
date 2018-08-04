package com.wyq.firehelper.kotlin.mvpGitHub.presenter

import com.wyq.firehelper.kotlin.mvpGitHub.Contract
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<T : Contract.IView> : IPresenter<T> {

    var view: T? = null
        private set

    private val mCompositeDisposable = CompositeDisposable()

    override fun attachView(view: T) {
        this.view = view
    }

    override fun detachView() {
        view = null
        if (!mCompositeDisposable.isDisposed)
            mCompositeDisposable.clear()
    }


    val isViewAttach: Boolean
        get() = view != null


    fun addDisposable(disposable:Disposable){
        mCompositeDisposable.add(disposable)
    }
}