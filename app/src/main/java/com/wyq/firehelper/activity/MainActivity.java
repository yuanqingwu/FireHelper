package com.wyq.firehelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.wyq.firehelper.FireHelpApplication;
import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleMainActivity;
import com.wyq.firehelper.connectivity.ConnectMainActivity;
import com.wyq.firehelper.developKit.DevelopKitMainActivity;
import com.wyq.firehelper.encryption.EncryptActivity;
import com.wyq.firehelper.ui.UiMainActivity;

/**
 * Created by Uni.W on 2016/8/10.
 */
public class MainActivity extends BaseActivity {

    private String[] items = { "Article" ,"Communication","UI","Encryption","DevelopKit"};

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLogger();

        listView =(ListView)findViewById(R.id.activity_main_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this, android.R.layout.simple_list_item_1,
                items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this,
                                ArticleMainActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,
                                ConnectMainActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this,
                                UiMainActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this,
                                EncryptActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(MainActivity.this,
                                DevelopKitMainActivity.class));
                        break;
                        default:
                            break;

                }

            }
        });

    }

    /**
     * 初始化Logger日志框架
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
                .logStrategy(new LogcatLogStrategy()) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("Test")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                //true will print the log message, false will ignore it.
                return FireHelpApplication.isDebug;
            }
        });
    }
}
