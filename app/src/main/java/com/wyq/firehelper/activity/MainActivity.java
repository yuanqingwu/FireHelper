package com.wyq.firehelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.architecture.mvp.translation.view.MvpActivity;
import com.wyq.firehelper.article.ArticleMainActivity;
import com.wyq.firehelper.connectivity.ConnectMainActivity;
import com.wyq.firehelper.developKit.DevelopKitMainActivity;
import com.wyq.firehelper.encryption.EncryptActivity;
import com.wyq.firehelper.ui.UiMainActivity;

/**
 * Created by Uni.W on 2016/8/10.
 */
public class MainActivity extends BaseActivity {

    private String[] items = { "Article" ,"Communication","UI","Encryption","DevelopKit","Architecture"};

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    case 5:
                        startActivity(new Intent(MainActivity.this,
                                MvpActivity.class));
                        break;
                        default:
                            break;

                }

            }
        });

    }

}
