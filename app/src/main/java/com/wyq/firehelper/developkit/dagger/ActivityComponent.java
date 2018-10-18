package com.wyq.firehelper.developkit.dagger;

import dagger.Component;

@ActivityScope
@Component(dependencies = {AppComponent.class},modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(DaggerActivity daggerActivity);
}
