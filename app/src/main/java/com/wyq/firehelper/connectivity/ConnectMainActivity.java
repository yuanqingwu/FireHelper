package com.wyq.firehelper.connectivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.connectivity.bluetoothChat.BtActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Uni.W on 2017/10/26.
 */

public class ConnectMainActivity extends BaseActivity {

    private String[] items = {"Bluetooth"};

    @BindView(R.id.home_list)
    public ListView homeListView;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle("Communication");
        setSupportActionBar(toolbar);
        initToolbarNav(toolbar);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                ConnectMainActivity.this, android.R.layout.simple_list_item_1,
                items);
        homeListView.setAdapter(adapter);
        homeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
