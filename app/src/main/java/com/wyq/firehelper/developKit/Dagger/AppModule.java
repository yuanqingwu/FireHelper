package com.wyq.firehelper.developKit.Dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;
    private String name;

    public AppModule(Context context) {
        this.context = context;
    }

    public AppModule(String name) {
        this.name = name;
    }

    @ApplicationScope
    @Provides
    public Context provideContext() {
        return this.context;
    }

    @ApplicationScope
    @Provides
    public String provideName() {
        return this.name;
    }
}
