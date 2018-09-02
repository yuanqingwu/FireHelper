package com.wyq.firehelper.ui.widget.FireToast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wyq.firehelper.R;

public class FireToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_activity_fire_toast_layout);
        FireToast.showCustomToast(this,"i am toast",FireToast.WARN_YELLOW);
        FireToast.showCustomToastContinuous(this,"i am toast",FireToast.ERROR_RED,5000);
    }
}
