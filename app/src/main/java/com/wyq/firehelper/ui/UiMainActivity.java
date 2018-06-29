package com.wyq.firehelper.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wyq.firehelper.R;

public class UiMainActivity extends Activity {

    /**
     * FlexboxLayout
     * PhotoView
     *
     */
    private String[] items = { "MetroStyle[MyImageView]" ,"ZoomImageView[MyZoomImageView]","CircleBlurImageView[MyCircleImageView]"};

    private ListView homeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ui_activity_main);

        homeList =(ListView)findViewById(R.id.home_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                UiMainActivity.this, android.R.layout.simple_list_item_1,
                items);
        homeList.setAdapter(adapter);
        homeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(UiMainActivity.this,
                                MetroStyleActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(UiMainActivity.this,
                                ZoomImageViewActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(UiMainActivity.this,
                                CircleBlurImageViewActivity.class));
                        break;
                }

            }
        });

    }


}
