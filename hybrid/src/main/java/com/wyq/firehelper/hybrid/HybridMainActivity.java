package com.wyq.firehelper.hybrid;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wyq.firehelper.base.navigation.NavigationManager;
import com.wyq.firehelper.hybrid.jsbridge.JsBridgeActivity;

import androidx.appcompat.app.AppCompatActivity;

@Route(path = NavigationManager.NAVIGATION_HYBRID_MAIN_ACTIVITY)
public class HybridMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JsBridgeActivity.instance(this);
        finish();
    }
}
