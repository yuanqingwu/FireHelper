package com.wyq.firehelper.developKit;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import com.google.gson.Gson;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;
import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;
import com.wyq.firehelper.developKit.placeholderview.HeadView;
import com.wyq.firehelper.developKit.placeholderview.ItemView;
import com.wyq.firehelper.developKit.placeholderview.data.DevelopKit;
import com.wyq.firehelper.developKit.placeholderview.data.KitInfo;
import com.wyq.firehelper.utils.CloseUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Uni.W on 2016/8/10.
 */
public class DevelopKitMainActivity extends Activity {

//    private String[] items = {
//            "依赖注入",
//            "butterKnife","Dagger","AndroidAnotations",
//            "响应式编程",
//            "RxJava" ,"RxAndroid",
//            "网络请求",
//            "Retrofit+OkHttp","Volley",
//            "图片处理",
//            "Glide","Picasso","Fresco",
//            "事件总线",
//            "EventBus","otto",
//            "网络解析",
//            "fastjson+Gson+jackson",
//            "HtmlPaser+Jsoup",
//            "Log日志",
//            "Logger","Hugo",
//            "性能优化",
//            "LeakCanary"};


    @BindView(R.id.activity_developkit_main_eph_view)
    public ExpandablePlaceHolderView ephView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.developkit_activity_main_layout);
        ButterKnife.bind(this);
        Logger.i("develop kit");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        for(DevelopKit kit : getKits()){
            ephView.addView(new HeadView(kit.getCategory()));
            for(KitInfo info : kit.getKitInfos()){
                ephView.addView(new ItemView(this,info.getName(),info.getDescription()));
            }
        }

    }

    private List<DevelopKit> getKits(){
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
