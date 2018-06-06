package com.wyq.firehelper.article;

import java.util.List;

/**
 * Created by Uni.W on 2018/3/16.
 */

public class ArticleResource {

    private String url;
    private String title;
    /**
     * 文章分类
     */
    private String category;
    private List<String> tag;

    public ArticleResource(String url){
        this.url = url;
    }

    public ArticleResource(String url,String title){
        this.title = title;
        this.url = url;
    }

    public ArticleResource(String url,String title,String category){
        this.title = title;
        this.url = url;
        this.category = category;
    }


    public ArticleResource(String url,String title,String category,List<String> tag){
        this.title = title;
        this.url = url;
        this.category = category;
        this.tag = tag;
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




}
