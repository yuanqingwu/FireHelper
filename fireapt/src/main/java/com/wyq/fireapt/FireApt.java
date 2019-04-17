package com.wyq.fireapt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidx.appcompat.app.AppCompatActivity;

public class FireApt {

    public static void bind(AppCompatActivity activity){

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
