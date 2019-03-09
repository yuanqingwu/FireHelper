package com.wyq.firehelper.java.vm;

/**
 * @author Uni.W
 * @date 2018/12/20 21:17
 */
public class RuntimeConstantPool {

    public static void main(String[] args) {
        RuntimeConstantPool pool = new RuntimeConstantPool();
        pool.cmp();
//        pool.oom();
    }

//    /**
//     * 会使计算机内存不足
//     */
//    public void oom(){
//        List<String> list = new ArrayList<String>();
//        long i = 0;
//        while (true){
//            list.add(String.valueOf(i++).intern());
//        }
//    }

    /**
     * JDK1.6中会得到两个false：intern()方法会把首次遇到的字符串实例复制到永久代中，返回的也是永久代中这个字符串的实例引用
     * 而StringBuilder创建的字符串实例在Java堆上，所以必然不同。
     * JDK1.7中intern()实现不会再复制实例，只是在常量池中记录首次出现的实例引用。因此如果字符串是第一次创建那么intern()返
     * 回的引用和StringBuilder创建的那个字符串实例是同一个。
     */
    public void cmp() {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str = "java";//TODO 注释这句观察结果差异（是否首次出现）
        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}
