package com.wyq.firehelper.developKit.Dagger;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PersonModule {

    private Context context;
    private String name;

    public PersonModule(Context context){
        this.context = context;
    }

    public  PersonModule(String name){
        this.name = name;
        this.context = null;
    }

    //@Named("context") 以字符串标注容易出错
    //@PersonWithContext 以自定义标签不容易出错
    @PersonWithContext
//    @Singleton
    @Provides
    public Person providePersonWithContext(Context context){
        return new Person(context);
    }

    //@Named("string")
    @PersonWithName
//    @Singleton
    @Provides
    public Person providePersonWithName(String name){
        return new Person(name);
    }

    @Provides
    public Context provideContext(){
        return this.context;
    }

    @Provides
    public String provideName(){
        return this.name;
    }
}
