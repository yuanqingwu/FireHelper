package com.wyq.firehelper.developKit.dagger;

import android.content.Context;

import dagger.Component;

@ApplicationScope
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context getContext();
    String getName();
}
