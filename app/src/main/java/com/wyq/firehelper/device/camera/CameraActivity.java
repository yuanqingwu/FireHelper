package com.wyq.firehelper.device.camera;

import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wyq.firehelper.R;

public class CameraActivity extends AppCompatActivity {

    public CameraManager cameraManager;
    public CameraDevice cameraDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_activity_camera);
    }

}
