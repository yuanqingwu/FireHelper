package com.wyq.firehelper.media.opengles;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class FireGlRenderer implements GLSurfaceView.Renderer {

    private Triangle triangle;

    private volatile float angle = 0f;

    //Model View Projection Matrix
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private final float[] mRotationMatrix = new float[16];

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 设置背景颜色
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        triangle = new Triangle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        /**
         * 透视投影：随观察点的距离变化而变化，观察点越远，视图越小，反之越大
         * Matrix.frustumM (float[] m,         //接收透视投影的变换矩阵
         *                 int mOffset,        //变换矩阵的起始位置（偏移量）
         *                 float left,         //相对观察点近面的左边距
         *                 float right,        //相对观察点近面的右边距
         *                 float bottom,       //相对观察点近面的下边距
         *                 float top,          //相对观察点近面的上边距
         *                 float near,         //相对观察点近面距离
         *                 float far)          //相对观察点远面距离
         */
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

//        triangle.draw();

        /**
         * Matrix.setLookAtM (float[] rm,      //接收相机变换矩阵
         *                 int rmOffset,       //变换矩阵的起始位置（偏移量）
         *                 float eyeX,float eyeY, float eyeZ,   //相机位置
         *                 float centerX,float centerY,float centerZ,  //观察点位置
         *                 float upX,float upY,float upZ)  //up向量在xyz上的分量
         */
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0f);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

//        triangle.draw(mMVPMatrix);

        //**********************************************
        // 创建一个旋转矩阵
        float[] rotation = new float[16];
//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int)time);
        Matrix.setRotateM(mRotationMatrix,0,angle,0,0,-1.0f);
        Matrix.multiplyMM(rotation,0,mMVPMatrix,0,mRotationMatrix,0);
        triangle.draw(rotation);
    }

    public void setAngle(float angle){
        this.angle = angle;
    }

    public float getAngle(){
        return angle;
    }

    public static int loadShader(int type, String shaderCode) {
        // 创造顶点着色器类型(GLES20.GL_VERTEX_SHADER)
        // 或者是片段着色器类型 (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
