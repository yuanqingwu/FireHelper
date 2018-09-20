package com.wyq.firehelper.developKit.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    @PersonWithContext
    @ActivityScope
    @Provides
    public Person providePersonWithContext(Context context){
        return new Person(context);
    }

    //@Named("string")
    @PersonWithName
    @ActivityScope
    @Provides
    public Person providePersonWithName(String name){
        return new Person(name);
    }


}
