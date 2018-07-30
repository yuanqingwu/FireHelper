package com.wyq.firehelper.developKit.Dagger;


import android.content.Context;

import com.orhanobut.logger.Logger;

public class Person {

    public String name;
    public Context context;

    public Person(Context context){
        Logger.i("person create with context:" +context);
        this.context = context;
    }

    public Person(String name){
        Logger.i("person create with name:" +name);
        this.name = name;
    }
    public void logPerson(){
        Logger.i(String.valueOf(hashCode()));
    }
}
