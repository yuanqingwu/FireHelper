package com.wyq.firehelper.ui;

import java.util.List;

public class UiBean {
    private String category;
    private List<UiInfoBean> info;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<UiInfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<UiInfoBean> info) {
        this.info = info;
    }
}
