package com.wyq.firehelper.developKit.Dagger;

import dagger.Component;

@ActivityScope
@Component(dependencies = {AppComponent.class},modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(DaggerActivity daggerActivity);
}
