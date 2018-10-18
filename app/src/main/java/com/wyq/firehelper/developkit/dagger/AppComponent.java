package com.wyq.firehelper.developkit.dagger;

import android.content.Context;

import dagger.Component;

@ApplicationScope
@Component(modules = {AppModule.class})
public interface AppComponent {
    Context getContext();
    String getName();
}
