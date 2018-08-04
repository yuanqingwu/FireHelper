package com.wyq.firehelper.kotlin.mvpGitHub.injection.module

import android.app.Activity
import dagger.Module
import dagger.Provides

@Module
class PresenterModule(private val mActivity:Activity) {

    @Provides
    internal fun provideActivity():Activity{
        return mActivity
    }


}