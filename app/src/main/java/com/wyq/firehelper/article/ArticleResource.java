package com.wyq.firehelper.article;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Uni.W on 2018/3/16.
 */

public class ArticleResource {

    private String url;
    private String title;
    private String category;//分类
    private List<String> tag;

    public ArticleResource(String url){
        this(url,"");
    }

    public ArticleResource(String url,String title){
       this(url,title,"");
    }

    public ArticleResource(String url,String title,String category){
        this(url,title,category,"");
    }


    public ArticleResource(String url,String title,String category,String... tags){
        this.title = title;
        this.url = url;
        this.category = category;
        this.tag = Arrays.asList(tags==null?new String[]{}:tags);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
       StringBuffer buffer = new StringBuffer(", tag=");
        for(String s : tag){
            buffer.append("'"+s+"\'");
        }
        return "ArticleResource{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                buffer +
                '}';
    }
}
