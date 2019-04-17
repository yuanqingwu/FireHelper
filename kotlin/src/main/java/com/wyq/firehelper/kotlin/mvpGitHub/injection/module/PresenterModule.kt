package com.wyq.firehelper.kotlin.mvpGitHub.injection.module

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides

@Module
class PresenterModule(private val mActivity: AppCompatActivity) {

    @Provides
    internal fun provideActivity(): AppCompatActivity {
        return mActivity
    }


}