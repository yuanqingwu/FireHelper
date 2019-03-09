package com.wyq.firehelper.media.opengles;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Java的缓冲区数据存储结构为大端字节序(BigEdian)，而OpenGl的数据为小端字节序（LittleEdian）,因为数据存储结构的差异，所以，在Android中使用OpenGl的时候必须要进行下转换。
 */
public class BufferUtils {

    public static IntBuffer intBuffer(int[] arr) {
        IntBuffer mBuffer;
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        // 数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asIntBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    public static FloatBuffer floatBuffer(float[] arr) {
        FloatBuffer mBuffer;
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个float占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        // 数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());
        // 从ByteBuffer创建一个浮点缓冲区
        mBuffer = qbb.asFloatBuffer();
        // 将坐标添加到FloatBuffer
        mBuffer.put(arr);
        // 设置缓冲区来读取第一个坐标
        mBuffer.position(0);
        return mBuffer;
    }

    public static ShortBuffer shortBufferUtil(short[] arr) {
        ShortBuffer mBuffer;
        ByteBuffer dlb = ByteBuffer.allocateDirect(arr.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        mBuffer = dlb.asShortBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }
}
