package com.wyq.firehelper.developKit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.developKit.ButterKnife.ButterKnifeActivity;
import com.wyq.firehelper.developKit.Glide.GlideActivity;
import com.wyq.firehelper.developKit.RxJava.RxJavaActivity;

/**
 * Created by Uni.W on 2016/8/10.
 */
public class DevelopKitMainActivity extends Activity {

    private String[] items = {
            "依赖注入",
            "butterKnife","Dagger","AndroidAnotations",
            "响应式编程",
            "RxJava" ,"RxAndroid",
            "网络请求",
            "Retrofit+OkHttp","Volley",
            "图片处理",
            "Glide","Picasso","Fresco",
            "事件总线",
            "EventBus","otto",
            "网络解析",
            "fastjson+Gson+jackson",
            "HtmlPaser+Jsoup",
            "Log日志",
            "Logger","Hugo",
            "性能优化",
            "LeakCanary"};

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        listView =(ListView)findViewById(R.id.activity_main_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                DevelopKitMainActivity.this, android.R.layout.simple_list_item_1,
                items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(DevelopKitMainActivity.this,
                                RxJavaActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(DevelopKitMainActivity.this,
                                ButterKnifeActivity.class));
                        break;
                    case 2:
//                        startActivity(new Intent(DevelopKitMainActivity.this,
//                                UiMainActivity.class));
                        break;
                    case 11:
                        startActivity(new Intent(DevelopKitMainActivity.this,
                                GlideActivity.class));
                        break;
                        default:
                            break;

                }

            }
        });

    }


}
