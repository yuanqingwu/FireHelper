package com.wyq.firehelper.java.concurrent;

import android.util.LruCache;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuanqingwu
 * @date 2019/06/18
 */
public class MapTest {

    public static void main(String[] args){


    }

    public static void testLinkedHashMap(){
        /**
         * accessOrder:是否基于访问排序，默认为false,代表按照插入顺序排序
         *
         * true:代表按照访问顺序排序
         */
        LinkedHashMap<Integer,String> linkedHashMap = new LinkedHashMap<>(0, 0.75f, true);
        linkedHashMap.put(0,"0");
        linkedHashMap.put(1,"1");
        linkedHashMap.put(2,"2");

        ReentrantLock lock = new ReentrantLock();


    }
}
