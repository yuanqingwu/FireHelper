package com.wyq.firehelper.ui.layout.placeholderview.data;

import java.util.List;

public class DevelopKit {

    private String category;

    private List<KitInfo> kitInfos;

    public DevelopKit(){
        this.category = "";
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<KitInfo> getKitInfos() {
        return kitInfos;
    }

    public void setKitInfos(List<KitInfo> kitInfos) {
        this.kitInfos = kitInfos;
    }


}
