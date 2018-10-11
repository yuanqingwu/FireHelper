package com.wyq.firehelper.base.home;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class FireModule {
    @SerializedName("title_en")
    private String titleEn;
    @SerializedName("title_zh")
    private String titleZh;
    @SerializedName("headImage")
    private String headImage;


    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleZh() {
        return titleZh;
    }

    public void setTitleZh(String titleZh) {
        this.titleZh = titleZh;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public static FireModule convertFromJson(String json){
        return new Gson().fromJson(json,FireModule.class);
    }
}
