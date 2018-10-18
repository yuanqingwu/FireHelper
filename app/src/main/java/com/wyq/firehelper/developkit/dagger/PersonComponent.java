package com.wyq.firehelper.developkit.dagger;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {PersonModule.class})
public interface PersonComponent {

    void inject(DaggerActivity dagger);
    void inject(DaggerFragment dagger);
}
