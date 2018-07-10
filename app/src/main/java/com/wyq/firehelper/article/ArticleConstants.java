package com.wyq.firehelper.article;

import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ArticleConstants {

    public static final ArticleResource _1 = new ArticleResource("https://www.diycode.cc/wiki/androidinterview","《Android 开发工程师面试指南》");
    public static final ArticleResource _2 = new ArticleResource("https://github.com/WeMobileDev/article","articles by WeChat Mobile Development Team ");
    public static final ArticleResource _3 = new ArticleResource("https://www.jianshu.com/p/cafedd319512","多点触控详解");
    public static final ArticleResource _4 = new ArticleResource("https://www.jianshu.com/p/1e456b63e1ab","欢迎来到Github世界");

    //developKit
    /**
     * http://jakewharton.github.io/butterknife/
     */
    public static final ArticleResource DEVKIT_INJECT_BUTTERKNIFE_0 = new ArticleResource("http://jakewharton.github.io/butterknife/","butterknife官网");
    public static final ArticleResource DEVKIT_INJECT_BUTTERKNIFE_1 = new ArticleResource("https://blog.csdn.net/donkor_/article/details/77879630","Android Butterknife（黄油刀） 使用方法总结");

    /**
     * Dagger
     *
     * https://www.jianshu.com/p/39d1df6c877d
     */
    public static final ArticleResource DEVKIT_INJECT_DAGGER_0 = new ArticleResource("https://google.github.io/dagger/users-guide","dagger/users-guide");
    public static final ArticleResource DEVKIT_INJECT_DAGGER_1 = new ArticleResource("https://www.jianshu.com/p/39d1df6c877d","Dagger2从入门到放弃再到恍然大悟");

    /**
     * AndroidAnnotations
     */
    public static final ArticleResource DEVKIT_INJECT_ANDROIDANNOTATIONS_0 = new ArticleResource("https://github.com/androidannotations/androidannotations/wiki","androidannotations");

    /**
     * https://bumptech.github.io/glide/
     */
    public static final ArticleResource DEVKIT_IMAGE_GLIDE_0 = new ArticleResource("https://bumptech.github.io/glide/","Glide文档");



    /**
     * rxjava:
     * http://gank.io/post/560e15be2dca930e00da1083
     * https://mcxiaoke.gitbooks.io/rxdocs/content/
     */
    public static final ArticleResource DEVKIT_REACTIVEX_RXJAVA_0 = new ArticleResource("http://reactivex.io/RxJava/2.x/javadoc/","RxJava/2.x/javadoc");
    public static final ArticleResource DEVKIT_REACTIVEX_RXJAVA_1 = new ArticleResource("https://mcxiaoke.gitbooks.io/rxdocs/content/","ReactiveX/RxJava文档中文版");
    public static final ArticleResource DEVKIT_REACTIVEX_RXJAVA_3 = new ArticleResource("https://www.jianshu.com/p/0cd258eecf60","这可能是最好的RxJava 2.x 教程");

    /**
     * 获取此类中所有的field
     * @return
     */
    public static List<ArticleResource> getAllFiled(){
        Field[] fields = ArticleConstants.class.getDeclaredFields();
        List<ArticleResource> list = new ArrayList<>();
        for(Field field : fields){
            try {
                Logger.i(field.get(null)+"  "+fields.length);
                if(field.get(null) instanceof ArticleResource)
                list.add((ArticleResource) field.get(null));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
