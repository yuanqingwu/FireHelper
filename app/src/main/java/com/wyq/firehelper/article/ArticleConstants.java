package com.wyq.firehelper.article;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ArticleConstants {

    //ViewDragHelper
    //Spring Animations for Android  https://github.com/facebook/rebound
    //A declarative framework for building efficient UIs on Android.  https://github.com/facebook/litho

    public static final ArticleResource _1 = new ArticleResource("https://www.diycode.cc/wiki/androidinterview", "《Android 开发工程师面试指南》", "", "面试");
    public static final ArticleResource _2 = new ArticleResource("https://github.com/WeMobileDev/article", "articles by WeChat Mobile Development Team ", "", "公司");
    public static final ArticleResource _3 = new ArticleResource("https://www.jianshu.com/p/cafedd319512", "多点触控详解", "UI", "UI");
    public static final ArticleResource _4 = new ArticleResource("https://github.com/CymChad/BaseRecyclerViewAdapterHelper", "BaseRecyclerViewAdapterHelper", "UI", "UI");
    public static final ArticleResource _5 = new ArticleResource("https://www.jianshu.com/p/1e456b63e1ab", "欢迎来到Github世界", "", "Github");

    public static final ArticleResource _6 = new ArticleResource("https://github.com/googlesamples/android-architecture", "googlesamples/android-architecture", "", "公司");
    public static final ArticleResource _7 = new ArticleResource("http://pingguohe.net/2016/12/20/Tangram-design-and-practice.html", " 页面动态化的基础 —— Tangram", "UI", "UI");
    public static final ArticleResource _8 = new ArticleResource("http://tangram.pingguohe.net/", " Tangram", "UI", "UI","主页");

    //developKit
    /**
     * http://jakewharton.github.io/butterknife/
     */
    public static final ArticleResource DEVKIT_INJECT_BUTTERKNIFE_0 = new ArticleResource("http://jakewharton.github.io/butterknife/", "butterknife官网", "", "主页", "框架");
    public static final ArticleResource DEVKIT_INJECT_BUTTERKNIFE_1 = new ArticleResource("https://blog.csdn.net/donkor_/article/details/77879630", "Android Butterknife（黄油刀） 使用方法总结", "", "框架");

    /**
     * Dagger
     * <p>
     * https://www.jianshu.com/p/39d1df6c877d
     */
    public static final ArticleResource DEVKIT_INJECT_DAGGER_0 = new ArticleResource("https://google.github.io/dagger/users-guide", "dagger/users-guide", "", "主页", "框架");
    public static final ArticleResource DEVKIT_INJECT_DAGGER_1 = new ArticleResource("https://www.jianshu.com/p/39d1df6c877d", "Dagger2从入门到放弃再到恍然大悟", "", "框架");

    /**
     * AndroidAnnotations
     */
    public static final ArticleResource DEVKIT_INJECT_ANDROIDANNOTATIONS_0 = new ArticleResource("https://github.com/androidannotations/androidannotations/wiki", "androidannotations", "", "主页", "框架");

    /**
     * https://bumptech.github.io/glide/
     */
    public static final ArticleResource DEVKIT_IMAGE_GLIDE_0 = new ArticleResource("https://bumptech.github.io/glide/", "Glide文档", "", "主页", "框架");


    /**
     * rxjava:
     * http://gank.io/post/560e15be2dca930e00da1083
     * https://mcxiaoke.gitbooks.io/rxdocs/content/
     */
    public static final ArticleResource DEVKIT_REACTIVEX_RXJAVA_0 = new ArticleResource("http://reactivex.io/RxJava/2.x/javadoc/", "RxJava/2.x/javadoc", "", "主页", "框架");
    public static final ArticleResource DEVKIT_REACTIVEX_RXJAVA_1 = new ArticleResource("https://mcxiaoke.gitbooks.io/rxdocs/content/", "ReactiveX/RxJava文档中文版", "", "框架");
    public static final ArticleResource DEVKIT_REACTIVEX_RXJAVA_3 = new ArticleResource("https://www.jianshu.com/p/0cd258eecf60", "这可能是最好的RxJava 2.x 教程", "", "框架");


    /**
     * Retrofit
     */
    public static final ArticleResource DEVKIT_RETROFIT_1 = new ArticleResource("https://square.github.io/retrofit/", "retrofit", "", "主页", "框架");
    public static final ArticleResource DEVKIT_RETROFIT_2 = new ArticleResource("https://blog.csdn.net/jiankeufo/article/details/73186929", "Android 源码解析 Retrofit2 原理", "", "框架");
    public static final ArticleResource DEVKIT_RETROFIT_3 = new ArticleResource("https://zhuanlan.zhihu.com/p/40097338", "知乎安卓客户端启动优化 - Retrofit 代理", "Retrofit", "框架", "优化");

    /**
     * 获取此类中所有的field
     *
     * @return
     */
    private static List<ArticleResource> list;

    public static List<ArticleResource> getAllFiled() {
        if (list != null && list.size() > 0) {
            return list;
        }
        Field[] fields = ArticleConstants.class.getDeclaredFields();
        list = new ArrayList<>();
        for (Field field : fields) {
            try {
//                Logger.i(field.get(null)+"  "+fields.length);
                if (field.get(null) instanceof ArticleResource)
                    list.add((ArticleResource) field.get(null));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static List<ArticleResource> getListByTag(String tag) {
        if (list == null || tag == null) {
            return null;
        } else if (tag.equals("全部")) {
            if (list == null) {
                list = getAllFiled();
            }
            return list;
        }

        List<ArticleResource> listWithTag = new ArrayList<>();
        for (ArticleResource resource : list) {
            if (resource.getTag() != null) {
                for (String s : resource.getTag()) {
                    if (s.equals(tag)) {
                        listWithTag.add(resource);
                    }
                }
            }
        }
        return listWithTag;
    }

    public static List<ArticleResource> getListByFilter(String filter) {
        if (list == null || filter == null) {
            return null;
        }
        List<ArticleResource> listFilter = new ArrayList<>();
        for (ArticleResource resource : list) {
            if (resource.toString().toUpperCase().contains(filter.toUpperCase())) {
                listFilter.add(resource);
            }
        }
        return listFilter;
    }


    /**
     * 获取所有的TAG
     *
     * @return
     */
    public static List<String> getAllTags() {
        List<String> tags = new ArrayList<>();
        tags.add("全部");
        for (ArticleResource resource : getAllFiled()) {
            for (String tag : resource.getTag()) {
                if (tag != null && tag.length() > 0) {
                    if (!tags.contains(tag))
                        tags.add(tag);
                }
            }
        }
        return tags;
    }
}
