package com.wyq.firehelper.connectivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.connectivity.bluetoothChat.BtActivity;

/**
 * Created by Uni.W on 2017/10/26.
 */

public class ConnectMainActivity extends Activity {

    private String[] items = { "BlueTooth" };

    private ListView home_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ui_activity_main);

        home_list=(ListView)findViewById(R.id.home_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                ConnectMainActivity.this, android.R.layout.simple_list_item_1,
                items);
        home_list.setAdapter(adapter);
        home_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(ConnectMainActivity.this,
                                BtActivity.class));
                        break;
                   default:
                       break;
                }

            }
        });

    }
}
