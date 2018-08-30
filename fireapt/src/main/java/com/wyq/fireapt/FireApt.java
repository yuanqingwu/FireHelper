package com.wyq.fireapt;

import android.app.Activity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FireApt {

    public static void bind(Activity activity){

        Class aClass = activity.getClass();
        try {
            Class bindingClass = Class.forName(aClass.getName()+"_ViewBinding");
            Method method = bindingClass.getMethod("bind",aClass);
            method.invoke(bindingClass.newInstance(),activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
