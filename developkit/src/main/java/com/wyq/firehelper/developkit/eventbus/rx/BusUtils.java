package com.wyq.firehelper.developkit.eventbus.rx;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author yuanqingwu
 * @date 2019/05/10
 */
public class BusUtils {

    /**
     * 判断对象是否相等
     *
     * @param o1 对象1
     * @param o2 对象2
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(Object o1, Object o2) {
        return Objects.equals(o1, o2);
    }

    public static void requireNonNull(final Object... objects) {
        if (objects == null) {
            throw new NullPointerException();
        }
        for (Object object : objects) {
            if (object == null) {
                throw new NullPointerException();
            }
        }
    }


    /**
     * fixme:修复未设置泛型的情况下崩溃的问题
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> Class<T> getTypeClassFromParadigm(final RxBus.OnEventListener<T> callback) {
        if (callback == null) {
            return null;
        }
        Type[] genericInterfaces = callback.getClass().getGenericInterfaces();
        Type type;
        if (genericInterfaces.length == 1) {
            type = genericInterfaces[0];
        } else {
            type = callback.getClass().getGenericSuperclass();
        }
        if(type instanceof ParameterizedType){
            type = ((ParameterizedType) type).getActualTypeArguments()[0];
            if(type instanceof ParameterizedType) {
                type = ((ParameterizedType) type).getRawType();
            }
        }

        String className = type.toString();
        if (className.startsWith("class ")) {
            className = className.substring(6);
        } else if (className.startsWith("interface ")) {
            className = className.substring(10);
        }
        try {
            //noinspection unchecked
            return (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class getClassFromObject(final Object obj) {
        if (obj == null) {
            return null;
        }
        Class objClass = obj.getClass();
        if (objClass.isAnonymousClass() || objClass.isSynthetic()) {
            Type[] genericInterfaces = objClass.getGenericInterfaces();
            String className;
            if (genericInterfaces.length == 1) {// interface
                Type type = genericInterfaces[0];
                while (type instanceof ParameterizedType) {
                    type = ((ParameterizedType) type).getRawType();
                }
                className = type.toString();
            } else {// abstract class or lambda
                Type type = objClass.getGenericSuperclass();
                while (type instanceof ParameterizedType) {
                    type = ((ParameterizedType) type).getRawType();
                }
                className = type.toString();
            }

            if (className.startsWith("class ")) {
                className = className.substring(6);
            } else if (className.startsWith("interface ")) {
                className = className.substring(10);
            }
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return objClass;
    }
}
