package com.wyq.firehelper.article;

import com.wyq.firehelper.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ArticleConstants {

    public static final ArticleResource _1 = new ArticleResource("https://juejin.im/post/5b0f51166fb9a009fd0e9811","Android RxJava： 这是一份全面的 操作符 使用汇总 （含详细实例讲解）");
    public static final ArticleResource _2 = new ArticleResource("https://juejin.im/post/5b0e6e6a5188251570336972","在Android中高效的加载大图");


    public static List<ArticleResource> getAllFiled(){
        Field[] fields = ArticleConstants.class.getDeclaredFields();
        List<ArticleResource> list = new ArrayList<>();
        for(Field field : fields){
            try {
                LogUtils.i("Test",field.get(null)+"  "+fields.length);
                if(field.get(null) instanceof ArticleResource)
                list.add((ArticleResource) field.get(null));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
