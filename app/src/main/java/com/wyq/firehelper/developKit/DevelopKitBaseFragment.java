package com.wyq.firehelper.developKit;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wyq.firehelper.article.ArticleResource;
import com.wyq.firehelper.article.WebViewActivity;
import com.wyq.firehelper.base.BaseFragment;
import com.wyq.firehelper.ui.layout.placeholderview.data.DevelopKit;
import com.wyq.firehelper.utils.CloseUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DevelopKitBaseFragment extends BaseFragment {

    /**
     * 关联界面与数据
     */
    public Map<TextView, ArticleResource> resourceList = new HashMap<>();

    public DevelopKitBaseFragment() {
        resourceList.clear();
//        Logger.i("resourceList.clear()");
    }

    /**
     * 构建文章阅读板块
     *
     * @param context
     */
    public void browserArticle(final Context context) {
        if (resourceList != null && resourceList.size() > 0) {
            for (TextView v : resourceList.keySet()) {
                v.setText(resourceList.get(v).getTitle());
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(context, resourceList.get(v).getUrl());
                    }
                });
            }
        }

    }

    /**
     * 跳转浏览器界面
     *
     * @param context
     * @param url
     */
    public void startActivity(Context context, String url) {
        Intent intent = new Intent();
        intent.putExtra("url", url);
        intent.setClass(context, WebViewActivity.class);
        startActivity(intent);
    }


    public List<DevelopKit> getKits(){
        List<DevelopKit> kitsList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JSONArray kitsArray = new JSONArray(readKitsFromAssets());
            for(int i=0; i<kitsArray.length();i++){
                DevelopKit kit = gson.fromJson(kitsArray.getString(i),DevelopKit.class);
                kitsList.add(kit);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return kitsList;
    }

    private String readKitsFromAssets(){
        InputStream is = null;
        try {
            is = getResources().getAssets().open("developKit.json");
            if(is != null){
                int len = is.available();
                byte[] buffer = new byte[len];
                is.read(buffer);
                return new String(buffer,"UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            CloseUtils.closeIO(is);
        }
        return null;
    }
}
