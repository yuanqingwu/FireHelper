package com.wyq.firehelper.media.opengles;

import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.view.LayoutInflater;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.media.databinding.MediaActivityOpenglesBinding;

public class OpenGLESActivity extends BaseActivity implements SurfaceTexture.OnFrameAvailableListener {


    public GLSurfaceView glSurfaceView;

    private FireGlRenderer glRenderer;


    @Override
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return MediaActivityOpenglesBinding.inflate(layoutInflater);
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {
        glSurfaceView = ((MediaActivityOpenglesBinding)viewBinding).mediaActivityOpenglesGlSv;

        glRenderer = new FireGlRenderer();
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(glRenderer);

        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        glSurfaceView.requestRender();
    }

    private float startX = 0;
    private float startY = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                x = event.getX();
//                y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - startX;
                float dy = y - startY;

                if (x < glSurfaceView.getWidth() / 2) {
                    dy = -1 * dy;
                }
                if (y > glSurfaceView.getHeight() / 2) {
                    dx = -1 * dx;
                }

                glRenderer.setAngle(glRenderer.getAngle() - (dx / 9 + dy / 16) * 5);
//                Logger.i("" + glRenderer.getAngle() + (dx + dy) * 180f / 320);

                glSurfaceView.requestRender();
                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }
        startX = x;
        startY = y;
        return true;
    }

    public static void instance(Context context) {
        context.startActivity(new Intent(context, OpenGLESActivity.class));
    }
}
