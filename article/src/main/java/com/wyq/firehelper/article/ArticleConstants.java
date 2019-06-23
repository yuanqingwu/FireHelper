package com.wyq.firehelper.article;

import com.wyq.firehelper.article.entity.ArticleResource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ArticleConstants {

    //***************************CATEGORY***********************
    public static final String CATEGORY_BLOG = "BLOG";
    public static final String CATEGORY_WEBSITE = "WEBSITE";
    public static final String CATEGORY_SOURCE_CODE = "SOURCE_CODE";

    //***************************TAG****************************
    public static final String TAG_UI = "UI";
    public static final String TAG_ANDROID_DEVELOPER = "谷歌官方";
    public static final String TAG_INTERVIEW = "面试";
    public static final String TAG_COMPANY_BLOG_SITE = "公司博客";
    public static final String TAG_PERSONAL_BLOG_SITE = "个人博客";
    public static final String TAG_GIT = "Git";
    public static final String TAG_JAVA = "JAVA";
    public static final String TAG_FRAMEWORK = "框架";
    public static final String TAG_ARCHITECTURE = "架构";
    public static final String TAG_OPTIMIZATION = "优化";
    public static final String TAG_DATA_STATISTICS = "数据统计";
    public static final String TAG_AOP = "AOP";
    public static final String TAG_JNI = "JNI";
    public static final String TAG_DEVICE = "设备";
    public static final String TAG_TEST = "测试";
    public static final String TAG_ANDROID_FRAMEWORK = "安卓框架";
    public static final String TAG_OPEN_GL = "OpenGL";
    public static final String TAG_SECURITY = "安全";


    //ViewDragHelper
    //Spring Animations for Android  https://github.com/facebook/rebound
    //A declarative framework for building efficient UIs on Android.  https://github.com/facebook/litho

    public static final ArticleResource _1 = new ArticleResource("https://www.diycode.cc/wiki/androidinterview", "《Android 开发工程师面试指南》", CATEGORY_BLOG, TAG_INTERVIEW);
    public static final ArticleResource _2 = new ArticleResource("https://github.com/WeMobileDev/article", "微信移动客户端开发团队", CATEGORY_BLOG, TAG_COMPANY_BLOG_SITE);
    public static final ArticleResource _3 = new ArticleResource("https://www.jianshu.com/p/cafedd319512", "多点触控详解", CATEGORY_BLOG, TAG_UI);
    public static final ArticleResource _4 = new ArticleResource("https://github.com/CymChad/BaseRecyclerViewAdapterHelper", "BaseRecyclerViewAdapterHelper", CATEGORY_SOURCE_CODE, TAG_UI);
    public static final ArticleResource _5 = new ArticleResource("https://www.jianshu.com/p/1e456b63e1ab", "欢迎来到Github世界", CATEGORY_BLOG, TAG_GIT);
    public static final ArticleResource _6 = new ArticleResource("https://github.com/googlesamples/android-architecture", "googlesamples/android-architecture", CATEGORY_SOURCE_CODE, TAG_ANDROID_DEVELOPER, TAG_ARCHITECTURE);
    public static final ArticleResource _7 = new ArticleResource("http://pingguohe.net/2016/12/20/Tangram-design-and-practice.html", " 页面动态化的基础 —— Tangram", CATEGORY_BLOG, TAG_UI);
    public static final ArticleResource _8 = new ArticleResource("http://pingguohe.net/", "苹果核（天猫）", CATEGORY_WEBSITE, TAG_COMPANY_BLOG_SITE);
    public static final ArticleResource _9 = new ArticleResource("https://guides.codepath.com/android/handling-scrolls-with-coordinatorlayout", "coordinatorlayout", CATEGORY_BLOG, TAG_UI);
    public static final ArticleResource _10 = new ArticleResource("https://blog.csdn.net/javazejian/article/details/72828483", "深入理解Java并发之synchronized实现原理", CATEGORY_BLOG, TAG_JAVA);

    public static final ArticleResource _12 = new ArticleResource("https://www.jianshu.com/p/e66a1afe474c", "D项目Android模块化 VCS演进之路", CATEGORY_BLOG, TAG_OPTIMIZATION, TAG_ARCHITECTURE);
    public static final ArticleResource _13 = new ArticleResource("https://tech.meituan.com/", "美团技术团队", CATEGORY_BLOG, TAG_COMPANY_BLOG_SITE);
    public static final ArticleResource _14 = new ArticleResource("http://f2e.souche.com/blog/", "搜车大无线团队博客", CATEGORY_BLOG, TAG_COMPANY_BLOG_SITE);
    public static final ArticleResource _15 = new ArticleResource("https://tech.youzan.com/", "有赞技术团队", CATEGORY_BLOG, TAG_COMPANY_BLOG_SITE);
    public static final ArticleResource _16 = new ArticleResource("https://www.jianshu.com/p/c202853059b4", "Android AOP之字节码插桩", CATEGORY_BLOG, TAG_DATA_STATISTICS, TAG_AOP);
    public static final ArticleResource _17 = new ArticleResource("https://www.jianshu.com/p/b5ffe845fe2d", "Android无埋点数据收集SDK关键技术", CATEGORY_BLOG, TAG_DATA_STATISTICS, TAG_AOP);
    public static final ArticleResource _18 = new ArticleResource("http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0523/7963.html", "App开发架构指南（谷歌官方文档译文）", CATEGORY_BLOG, TAG_ARCHITECTURE);
    public static final ArticleResource _19 = new ArticleResource("https://techblog.toutiao.com/", "今日头条技术博客", CATEGORY_BLOG, TAG_COMPANY_BLOG_SITE);
    public static final ArticleResource _20 = new ArticleResource("http://blogs.360.cn/", "奇虎360技术博客", CATEGORY_BLOG, TAG_COMPANY_BLOG_SITE);
    //    public static final ArticleResource _21 = new ArticleResource("https://androidweekly.io/", "Android 开发技术周报", "Blog", "博客主页");
    public static final ArticleResource _22 = new ArticleResource("https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA", "一种极低成本的Android屏幕适配方式", CATEGORY_BLOG, TAG_OPTIMIZATION);
    public static final ArticleResource _23 = new ArticleResource("https://blog.csdn.net/Luoshengyang", "老罗的Android之旅(罗升阳CSDN主页)", CATEGORY_BLOG, TAG_PERSONAL_BLOG_SITE);
    public static final ArticleResource _24 = new ArticleResource("https://blog.csdn.net/eclipsexys", "eclipse_xu(徐宜生)", CATEGORY_BLOG, TAG_PERSONAL_BLOG_SITE);
    public static final ArticleResource _25 = new ArticleResource("http://weishu.me/", "田维术", CATEGORY_BLOG, TAG_PERSONAL_BLOG_SITE);
    public static final ArticleResource _26 = new ArticleResource("https://blog.csdn.net/weelyy/article/details/78987087", "Android AOP编程的四种策略探讨：Aspectj，cglib+dexmaker，Javassist，epic+dexposed", CATEGORY_BLOG, TAG_AOP);
    public static final ArticleResource _27 = new ArticleResource("https://blog.csdn.net/woshimalingyi/article/details/73252013", "AOP之@AspectJ技术原理详解", CATEGORY_BLOG, TAG_AOP);
    public static final ArticleResource _28 = new ArticleResource("https://www.jianshu.com/p/2412d00a0ce4", "Android 属性动画：这是一篇很详细的 属性动画 总结&攻略", CATEGORY_BLOG, TAG_UI);
    public static final ArticleResource _29 = new ArticleResource("https://www.jianshu.com/p/8f14679809b3", "App瘦身最佳实践", CATEGORY_BLOG, TAG_OPTIMIZATION);
    public static final ArticleResource _30 = new ArticleResource("https://github.com/tianzhijiexian/Android-Best-Practices", "Android最佳实践示例", CATEGORY_BLOG, TAG_OPTIMIZATION);
    public static final ArticleResource _31 = new ArticleResource("https://blog.csdn.net/yuzhou_zang/article/details/78410632", "JNI 实战全面解析", CATEGORY_BLOG, TAG_JNI);
    public static final ArticleResource _32 = new ArticleResource("https://www.jianshu.com/p/c71ec5d63f0d", "Android NDK 开发：CMake 使用", CATEGORY_BLOG, TAG_JNI);
    public static final ArticleResource _33 = new ArticleResource("https://www.jianshu.com/p/0ac7234dcefc", "Android相机开发——CameraView源码解析", CATEGORY_BLOG, TAG_DEVICE);
    public static final ArticleResource _34 = new ArticleResource("http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/gc01/index.html", "Hotspot JVM GC", CATEGORY_BLOG, TAG_JAVA);
    public static final ArticleResource _35 = new ArticleResource("https://www.jianshu.com/p/2b2e5d417e10", "WebView深度学习（二）之全面总结WebView遇到的坑及优化", CATEGORY_BLOG, TAG_OPTIMIZATION);
    public static final ArticleResource _36 = new ArticleResource("http://a.codekk.com/detail/Android/%E6%89%94%E7%89%A9%E7%BA%BF/Dagger%20%E6%BA%90%E7%A0%81%E8%A7%A3%E6%9E%90", "Dagger 源码解析", CATEGORY_BLOG, TAG_FRAMEWORK);
    public static final ArticleResource _37 = new ArticleResource("http://p.codekk.com/", "codekk[Trinea 创建并维护]", CATEGORY_BLOG, TAG_PERSONAL_BLOG_SITE);
    public static final ArticleResource _38 = new ArticleResource("http://www.trinea.cn/android/didi-internationalization-android-evolution/", "滴滴国际化项目 Android 端演进", CATEGORY_BLOG, TAG_ARCHITECTURE);
    public static final ArticleResource _39 = new ArticleResource("https://mp.weixin.qq.com/s/6Q818XA5FaHd7jJMFBG60w", "微信Android模块化架构重构实践 ", CATEGORY_BLOG, TAG_ARCHITECTURE);
    public static final ArticleResource _40 = new ArticleResource("https://tech.meituan.com/meituan_food_delivery_android_architecture_evolution.html", "美团外卖Android平台化架构演进实践 ", CATEGORY_BLOG, TAG_ARCHITECTURE);
    public static final ArticleResource _41 = new ArticleResource("https://blog.csdn.net/qq_17766199/article/details/78450007", "Android单元测试(二)：Mockito框架的使用 ", CATEGORY_BLOG, TAG_TEST, TAG_FRAMEWORK);
    public static final ArticleResource _42 = new ArticleResource("https://github.com/mockito/mockito/wiki", "mockito/wiki", CATEGORY_BLOG, TAG_TEST, TAG_FRAMEWORK);
    public static final ArticleResource _43 = new ArticleResource("https://github.com/powermock/powermock", "PowerMock ", CATEGORY_BLOG, TAG_TEST, TAG_FRAMEWORK);
    public static final ArticleResource _44 = new ArticleResource("https://www.jianshu.com/p/c6d91b8a491f", "Android单元测试之Robolectric", CATEGORY_BLOG, TAG_TEST, TAG_FRAMEWORK);
    public static final ArticleResource _45 = new ArticleResource("https://mr-cao.gitbooks.io/android/content/", "Android框架分析系列", CATEGORY_BLOG, TAG_ANDROID_FRAMEWORK);
    public static final ArticleResource _46 = new ArticleResource("https://blog.csdn.net/javazejian/article/details/52709857", "关于Android Service真正的完全详解，你需要知道的一切", CATEGORY_BLOG, TAG_ANDROID_FRAMEWORK);
    public static final ArticleResource _47 = new ArticleResource("https://developer.android.google.cn/guide/components/tasks-and-back-stack", "任务和返回栈", CATEGORY_BLOG, TAG_ANDROID_FRAMEWORK, TAG_ANDROID_DEVELOPER);
    public static final ArticleResource _48 = new ArticleResource("https://www.jianshu.com/p/b66c225c19e2", "Android WebView独立进程解决方案", CATEGORY_BLOG, TAG_OPTIMIZATION);
    public static final ArticleResource _49 = new ArticleResource("https://mp.weixin.qq.com/s/Z7oMv0IgKWNkhLon_hFakg", "Android内存优化杂谈", CATEGORY_BLOG, TAG_OPTIMIZATION);
    public static final ArticleResource _50 = new ArticleResource("https://mp.weixin.qq.com/s/QRIy_apwqAaL2pM8a_lRUQ", "Android安装包相关知识汇总", CATEGORY_BLOG, TAG_OPTIMIZATION);
    public static final ArticleResource _51 = new ArticleResource("https://developer.android.google.cn/guide/topics/graphics/opengl", "Android openGl", CATEGORY_BLOG, TAG_OPEN_GL);
    public static final ArticleResource _52 = new ArticleResource("https://blog.csdn.net/qq_32175491/article/details/79091647", "Android openGl开发详解(一)——绘制简单图形", CATEGORY_BLOG, TAG_OPEN_GL);
    public static final ArticleResource _53 = new ArticleResource("https://www.jianshu.com/p/f1aeb0369746", "知乎 Android 客户端组件化实践", "Blog", CATEGORY_BLOG, TAG_OPTIMIZATION);
    public static final ArticleResource _54 = new ArticleResource("https://www.jianshu.com/nb/29902765", "知乎移动平台专栏", CATEGORY_BLOG, TAG_COMPANY_BLOG_SITE);
    public static final ArticleResource _55 = new ArticleResource("https://bbs.pediy.com/thread-219107.htm", "Android安全项目入门篇", CATEGORY_BLOG, TAG_SECURITY);
    public static final ArticleResource _56 = new ArticleResource("https://bbs.pediy.com/forum-161.htm", "看雪论坛[Android安全]", CATEGORY_WEBSITE, TAG_SECURITY);
    public static final ArticleResource _57 = new ArticleResource("https://www.jianshu.com/p/aff499a5953c", "关于RecyclerView你知道的不知道的都在这了（上）", CATEGORY_BLOG, TAG_UI);
    public static final ArticleResource _58 = new ArticleResource("https://www.jianshu.com/p/c70989bd5f29", "最全的BAT大厂面试题整理", CATEGORY_BLOG, TAG_INTERVIEW);
    public static final ArticleResource _59 = new ArticleResource("https://www.jianshu.com/p/4115bcf9f92e", "Android(2017-2018)BAT面试题整理（Android篇，含答案）", CATEGORY_BLOG, TAG_INTERVIEW);
    public static final ArticleResource _60 = new ArticleResource("https://www.jianshu.com/p/5e7075f4875f", "Android：手把手教你构建 全面的WebView 缓存机制 & 资源加载方案", CATEGORY_BLOG, TAG_OPTIMIZATION);
    public static final ArticleResource _61 = new ArticleResource("https://github.com/mzlogin/awesome-adb", "ADB 用法大全", CATEGORY_BLOG, TAG_TEST);
    public static final ArticleResource _62 = new ArticleResource("https://www.jianshu.com/p/a3cb1e23c2c4", "Android vector 标签 pathData 详解", CATEGORY_BLOG, TAG_UI);
    public static final ArticleResource _63 = new ArticleResource("https://www.jianshu.com/p/e3614e7abc03", "Android Vector曲折的兼容之路", CATEGORY_BLOG, TAG_UI);
    public static final ArticleResource _64 = new ArticleResource("http://pingguohe.net/2017/08/02/tac-1.0.html", "天猫APP改版之全新大首页架构&开发模式全面升级-TAC", CATEGORY_BLOG, TAG_ARCHITECTURE);
    public static final ArticleResource _65 = new ArticleResource("http://pingguohe.net/2017/12/27/deep-into-virtualview-android-1.html", "VirtualView Android实现详解（一）—— 文件格式与模板编译", CATEGORY_BLOG, TAG_UI, TAG_FRAMEWORK);
    public static final ArticleResource _66 = new ArticleResource("https://www.jianshu.com/p/ea8bc4aaf057", "关于ContentProvider的知识都在这里了！", CATEGORY_BLOG, TAG_ANDROID_FRAMEWORK);
    public static final ArticleResource _67 = new ArticleResource("https://developer.android.google.cn/training/articles/keystore", "Android 密钥库系统", CATEGORY_BLOG, TAG_ANDROID_DEVELOPER);
    public static final ArticleResource _68 = new ArticleResource("https://developer.android.google.cn/guide/practices/screens_support", "支持多种屏幕", CATEGORY_BLOG, TAG_ANDROID_DEVELOPER);
    public static final ArticleResource _69 = new ArticleResource("https://developer.android.google.cn/guide/topics/providers/content-provider-basics", "内容提供程序基础知识 ", CATEGORY_BLOG, TAG_ANDROID_DEVELOPER);
    public static final ArticleResource _70 = new ArticleResource("https://guides.gradle.org/building-android-apps/", "gradle guides (Building Android Apps)", CATEGORY_WEBSITE, TAG_ANDROID_DEVELOPER);
    public static final ArticleResource _71 = new ArticleResource("https://www.jianshu.com/p/8a3eeeaf01e8", "Android 组件化 —— 路由设计最佳实践", CATEGORY_BLOG, TAG_FRAMEWORK, TAG_ARCHITECTURE);
    public static final ArticleResource _72 = new ArticleResource("https://www.infoq.cn/article/P99dKo0Lu9A5E8vI_NwQ", "51 信用卡 Android 架构演进实践", CATEGORY_BLOG, TAG_OPTIMIZATION, TAG_ARCHITECTURE);
    public static final ArticleResource _73 = new ArticleResource("http://atlas.taobao.org/docs/", "手机淘宝Atlas容器化框架文档", CATEGORY_WEBSITE, TAG_ARCHITECTURE);
    public static final ArticleResource _74 = new ArticleResource("https://flutterchina.club/flutter-for-android/", "flutter-for-android", CATEGORY_WEBSITE, TAG_ARCHITECTURE);
    public static final ArticleResource _75 = new ArticleResource("https://mp.weixin.qq.com/s/2MsEAR9pQfMr1Sfs7cPdWQ", "Android 内存优化总结&实践", CATEGORY_BLOG, TAG_OPTIMIZATION);
    public static final ArticleResource _76 = new ArticleResource("http://akarnokd.blogspot.com/", "Advanced Reactive Java", CATEGORY_BLOG, TAG_PERSONAL_BLOG_SITE, TAG_JAVA);
    public static final ArticleResource _77 = new ArticleResource("http://blog.nimbledroid.com/2016/04/06/slow-ClassLoader.getResourceAsStream-zh.html", "揭秘在安卓平台上奇慢无比的 ClassLoader.getResourceAsStream", CATEGORY_BLOG, TAG_OPTIMIZATION);
    public static final ArticleResource _78 = new ArticleResource("http://benjaminwhx.com/2018/05/05/%E8%AF%B4%E8%AF%B4%E9%98%9F%E5%88%97Queue/", "说说队列Queue", CATEGORY_BLOG, TAG_JAVA);
    public static final ArticleResource _79 = new ArticleResource("https://www.jianshu.com/u/1c68d0bad5a2", "得到团队组件化", CATEGORY_BLOG, TAG_ARCHITECTURE,TAG_COMPANY_BLOG_SITE);


    /**
     * 安卓开发者网站
     */
    public static final ArticleResource DEVELOPER_GUIDE = new ArticleResource("https://developer.android.google.cn/guide/", "guide", CATEGORY_WEBSITE, TAG_ANDROID_DEVELOPER);
    public static final ArticleResource DEVELOPER_IMAGES_AND_GRAPHICS = new ArticleResource("https://developer.android.google.cn/guide/topics/graphics/", "Images and graphics", CATEGORY_WEBSITE, TAG_ANDROID_DEVELOPER, TAG_UI);


    /**
     * mvvm:https://zhuanlan.zhihu.com/p/23772285?from=groupmessage
     *
     */

    //developKit
    /**
     * http://jakewharton.github.io/butterknife/
     */
    public static final ArticleResource DEVKIT_INJECT_BUTTERKNIFE_0 = new ArticleResource("http://jakewharton.github.io/butterknife/", "butterknife官网", CATEGORY_WEBSITE, TAG_FRAMEWORK);
    public static final ArticleResource DEVKIT_INJECT_BUTTERKNIFE_1 = new ArticleResource("https://blog.csdn.net/donkor_/article/details/77879630", "Android Butterknife（黄油刀） 使用方法总结", CATEGORY_BLOG, TAG_FRAMEWORK);

    /**
     * Dagger
     * <p>
     * https://www.jianshu.com/p/39d1df6c877d
     */
    public static final ArticleResource DEVKIT_INJECT_DAGGER_0 = new ArticleResource("https://google.github.io/dagger/users-guide", "dagger/users-guide", CATEGORY_BLOG, TAG_FRAMEWORK);
    public static final ArticleResource DEVKIT_INJECT_DAGGER_1 = new ArticleResource("https://www.jianshu.com/p/39d1df6c877d", "Dagger2从入门到放弃再到恍然大悟", CATEGORY_BLOG, TAG_FRAMEWORK);

    /**
     * AndroidAnnotations
     */
    public static final ArticleResource DEVKIT_INJECT_ANDROIDANNOTATIONS_0 = new ArticleResource("https://github.com/androidannotations/androidannotations/wiki", "androidannotations", CATEGORY_WEBSITE, TAG_FRAMEWORK);

    /**
     * https://bumptech.github.io/glide/
     */
    public static final ArticleResource DEVKIT_IMAGE_GLIDE_0 = new ArticleResource("https://bumptech.github.io/glide/", "Glide文档", CATEGORY_WEBSITE, TAG_FRAMEWORK);


    /**
     * rxjava:
     * http://gank.io/post/560e15be2dca930e00da1083
     * https://mcxiaoke.gitbooks.io/rxdocs/content/
     */
    public static final ArticleResource DEVKIT_REACTIVEX_RXJAVA_0 = new ArticleResource("http://reactivex.io/RxJava/2.x/javadoc/", "RxJava/2.x/javadoc", CATEGORY_WEBSITE, TAG_FRAMEWORK, TAG_JAVA);
    public static final ArticleResource DEVKIT_REACTIVEX_RXJAVA_1 = new ArticleResource("https://www.jianshu.com/p/0cd258eecf60", "这可能是最好的RxJava 2.x 教程", CATEGORY_BLOG, TAG_FRAMEWORK, TAG_JAVA);
    public static final ArticleResource DEVKIT_REACTIVEX_RXJAVA_2 = new ArticleResource("https://www.jianshu.com/p/b30de498c3cc", "Android - RxJava2.0 操作符整理归纳", CATEGORY_BLOG, TAG_FRAMEWORK, TAG_JAVA);
    public static final ArticleResource DEVKIT_REACTIVEX_RXJAVA_3 = new ArticleResource("https://juejin.im/post/5b1fbd796fb9a01e8c5fd847", "详解 RxJava 的消息订阅和线程切换原理", CATEGORY_BLOG, TAG_FRAMEWORK, TAG_JAVA);


    /**
     * Retrofit
     */
    public static final ArticleResource DEVKIT_RETROFIT_1 = new ArticleResource("https://square.github.io/retrofit/", "retrofit", CATEGORY_WEBSITE, TAG_FRAMEWORK);
    public static final ArticleResource DEVKIT_RETROFIT_2 = new ArticleResource("https://blog.csdn.net/jiankeufo/article/details/73186929", "Android 源码解析 Retrofit2 原理", CATEGORY_BLOG, TAG_FRAMEWORK);
    public static final ArticleResource DEVKIT_RETROFIT_3 = new ArticleResource("https://zhuanlan.zhihu.com/p/40097338", "知乎安卓客户端启动优化 - Retrofit 代理", CATEGORY_BLOG, TAG_FRAMEWORK, TAG_OPTIMIZATION);

    /**
     * 获取此类中所有的field
     *
     * @return
     */
    private static List<ArticleResource> list;

    public static List<ArticleResource> getAllArticles() {
        if (list != null && list.size() > 0) {
            return list;
        }
        Field[] fields = ArticleConstants.class.getDeclaredFields();
        list = new ArrayList<>();
        for (Field field : fields) {
            try {
//                Logger.i(field.get(null)+"  "+fields.length);
                if (field.get(null) instanceof ArticleResource) {
                    list.add((ArticleResource) field.get(null));
                }
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
                list = getAllArticles();
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

    public static List<ArticleResource> getListByFilter(String... filters) {
        if (list == null || filters == null) {
            return null;
        }
        if (filters.length == 0) {
            return list;
        }
        List<ArticleResource> listFilter = new ArrayList<>();
        for (ArticleResource resource : list) {
            String str = resource.toString().toUpperCase();
            for (String filter : filters) {
                if (str.contains(filter.toUpperCase())) {
                    listFilter.add(resource);
                }
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
        for (ArticleResource resource : getAllArticles()) {
            for (String tag : resource.getTag()) {
                if (tag != null && tag.length() > 0) {
                    if (!tags.contains(tag)) {
                        tags.add(tag);
                    }
                }
            }
        }
        return tags;
    }

    /**
     * 根据url获取文章完整信息
     *
     * @param url
     * @return
     */
    public static ArticleResource getArticleByUrl(String url) {

        if (list == null) {
            list = getAllArticles();
        }

        for (ArticleResource resource : list) {
            if (resource.getUrl().equals(url)) {
                return resource;
            }
        }

        return null;
    }


    /**
     * 根据传入的数组找出没有被包含在内的URL列表
     *
     * @param urls
     * @return
     */
    public static List<String> diffArticlesByUrl(Object[] urls) {
        if (urls == null || urls.length == 0) {
            return null;
        }
        List<String> diffUrls = new ArrayList<>();
        for (Object url : urls) {
            if (url instanceof String) {
                if (getArticleByUrl((String) url) == null) {
                    diffUrls.add((String) url);
                }
            }
        }
        return diffUrls;
    }
}
