package com.wyq.firehelper.kotlin.mvpGitHub.injection.component

import com.wyq.firehelper.kotlin.mvpGitHub.injection.module.PresenterModule
import com.wyq.firehelper.kotlin.mvpGitHub.view.GitHubMainActivity
import dagger.Component

@Component(modules = arrayOf(PresenterModule::class))
interface PresenterComponent {

    fun inject(activity:GitHubMainActivity)
}