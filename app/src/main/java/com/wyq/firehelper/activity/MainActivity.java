package com.wyq.firehelper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wyq.firehelper.R;

public class MainActivity extends Activity {

    private String[] items = { "MetroStyle[MyImageView]" ,"ZoomImageView[MyZoomImageView]","CircleBlurImageView[MyCircleImageView]"};

    private ListView home_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        home_list=(ListView)findViewById(R.id.home_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                MainActivity.this, android.R.layout.simple_list_item_1,
                items);
        home_list.setAdapter(adapter);
        home_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this,
                                MetroStyleActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this,
                                ZoomImageViewActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this,
                                CircleBlurImageViewActivity.class));
                        break;
                }

            }
        });

    }


}
