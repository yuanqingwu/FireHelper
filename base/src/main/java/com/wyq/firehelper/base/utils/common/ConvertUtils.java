 /*		
 * ===========================================================================================
 * = COPYRIGHT		          
 *          PAX Computer Technology(Shenzhen) CO., LTD PROPRIETARY INFORMATION		
 *   This software is supplied under the terms of a license agreement or nondisclosure 	
 *   agreement with PAX Computer Technology(Shenzhen) CO., LTD and may not be copied or 
 *   disclosed except in accordance with the terms in that agreement.   		
 *     Copyright (C) 2017-? PAX Computer Technology(Shenzhen) CO., LTD All rights reserved.
 * Description: // Detail description about the function of this module,		
 *             // interfaces with the other modules, and dependencies. 		
 * Revision History:		
 * Date	                 Author	                Action
 * 2017-03-14  	         steven.w           			Create
 * ===========================================================================================
 */

 package com.wyq.firehelper.base.utils.common;


 import android.content.Context;
 import android.util.TypedValue;

 public class ConvertUtils {
    private static final String TAG = ConvertUtils.class.getSimpleName();

    private ConvertUtils() {
    }

     /**
      * 根据手机分辨率从dp转成px
      *
      * @param context
      * @param dpValue
      * @return
      */
     public static int dip2px(Context context, float dpValue) {
         final float scale = context.getResources().getDisplayMetrics().density;
         return (int) (dpValue * scale + 0.5f);
     }

     private float dipToPx(Context context,float dip) {
         return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                 context.getResources().getDisplayMetrics());
     }

     /**
      * 根据手机的分辨率�?px(像素) 的单�?转成�?dp
      */
     public static int px2dip(Context context, float pxValue) {
         final float scale = context.getResources().getDisplayMetrics().density;
         return (int) (pxValue / scale + 0.5f) - 15;
     }

     /**
      * 将px值转换为sp值，保证文字大小不变
      *
      * @param pxValue
      * @param fontScale（DisplayMetrics类中属�?scaledDensity�?
      * @return
      */
     public static int px2sp(float pxValue, float fontScale) {
         return (int) (pxValue / fontScale + 0.5f);
     }

     /**
      * 将sp值转换为px值，保证文字大小不变
      *
      * @param spValue
      * @param fontScale（DisplayMetrics类中属�?scaledDensity�?
      * @return
      */
     public static int sp2px(float spValue, float fontScale) {
         return (int) (spValue * fontScale + 0.5f);
     }

    public static String bcdToStr(byte[] b) throws IllegalArgumentException {
        if (b == null) {
            throw new IllegalArgumentException("bcdToStr input arg is null");
        }

        char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    
    public static byte[] strToBcd(String str, EPaddingPosition paddingPosition) throws IllegalArgumentException {
        if (str == null || paddingPosition == null) {
            throw new IllegalArgumentException("strToBcd input arg is null");
        }

        int len = str.length();
        int mod = len % 2;
        if (mod != 0) {
            if (paddingPosition == EPaddingPosition.PADDING_RIGHT) {
                str = str + "0";
            } else {
                str = "0" + str;
            }
            len = str.length();
        }
        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }
        byte bbt[] = new byte[len];
        abt = str.getBytes();
        int j, k;
        for (int p = 0; p < str.length() / 2; p++) {
            if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else if ((abt[2 * p] >= 'A') && (abt[2 * p] <= 'Z')) {
                j = abt[2 * p] - 'A' + 0x0a;
            } else {
                j = abt[2 * p] - '0';
            }

            if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else if ((abt[2 * p + 1] >= 'A') && (abt[2 * p + 1] <= 'Z')) {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            } else {
                k = abt[2 * p + 1] - '0';
            }

            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    
    public static void longToByteArray(long l, byte[] to, int offset, EEndian endian) throws IllegalArgumentException {
        if (to == null || endian == null) {
            throw new IllegalArgumentException("longToByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            to[offset] = (byte) ((l >>> 56) & 0xff);
            to[offset + 1] = (byte) ((l >>> 48) & 0xff);
            to[offset + 2] = (byte) ((l >>> 40) & 0xff);
            to[offset + 3] = (byte) ((l >>> 32) & 0xff);
            to[offset + 4] = (byte) ((l >>> 24) & 0xff);
            to[offset + 5] = (byte) ((l >>> 16) & 0xff);
            to[offset + 6] = (byte) ((l >>> 8) & 0xff);
            to[offset + 7] = (byte) (l & 0xff);
        } else {
            to[offset + 7] = (byte) ((l >>> 56) & 0xff);
            to[offset + 6] = (byte) ((l >>> 48) & 0xff);
            to[offset + 5] = (byte) ((l >>> 40) & 0xff);
            to[offset + 4] = (byte) ((l >>> 32) & 0xff);
            to[offset + 3] = (byte) ((l >>> 24) & 0xff);
            to[offset + 2] = (byte) ((l >>> 16) & 0xff);
            to[offset + 1] = (byte) ((l >>> 8) & 0xff);
            to[offset] = (byte) (l & 0xff);
        }
    }


    public static byte[] longToByteArray(long l, EEndian endian) throws IllegalArgumentException {
        if (endian == null) {
            throw new IllegalArgumentException("longToByteArray input arg is null");
        }

        byte[] to = new byte[8];

        if (endian == EEndian.BIG_ENDIAN) {
            to[0] = (byte) ((l >>> 56) & 0xff);
            to[1] = (byte) ((l >>> 48) & 0xff);
            to[2] = (byte) ((l >>> 40) & 0xff);
            to[3] = (byte) ((l >>> 32) & 0xff);
            to[4] = (byte) ((l >>> 24) & 0xff);
            to[5] = (byte) ((l >>> 16) & 0xff);
            to[6] = (byte) ((l >>> 8) & 0xff);
            to[7] = (byte) (l & 0xff);
        } else {
            to[7] = (byte) ((l >>> 56) & 0xff);
            to[6] = (byte) ((l >>> 48) & 0xff);
            to[5] = (byte) ((l >>> 40) & 0xff);
            to[4] = (byte) ((l >>> 32) & 0xff);
            to[3] = (byte) ((l >>> 24) & 0xff);
            to[2] = (byte) ((l >>> 16) & 0xff);
            to[1] = (byte) ((l >>> 8) & 0xff);
            to[0] = (byte) (l & 0xff);
        }

        return to;
    }

    public static void intToByteArray(int i, byte[] to, int offset, EEndian endian) throws IllegalArgumentException {
        if (to == null || endian == null) {
            throw new IllegalArgumentException("longToByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            to[offset] = (byte) ((i >>> 24) & 0xff);
            to[offset + 1] = (byte) ((i >>> 16) & 0xff);
            to[offset + 2] = (byte) ((i >>> 8) & 0xff);
            to[offset + 3] = (byte) (i & 0xff);
        } else {
            to[offset] = (byte) (i & 0xff);
            to[offset + 1] = (byte) ((i >>> 8) & 0xff);
            to[offset + 2] = (byte) ((i >>> 16) & 0xff);
            to[offset + 3] = (byte) ((i >>> 24) & 0xff);
        }
    }

    public static byte[] intToByteArray(int i, EEndian endian) throws IllegalArgumentException {
        if (endian == null) {
            throw new IllegalArgumentException("intToByteArray input arg is null");
        }

        byte[] to = new byte[4];

        if (endian == EEndian.BIG_ENDIAN) {
            to[0] = (byte) ((i >>> 24) & 0xff);
            to[1] = (byte) ((i >>> 16) & 0xff);
            to[2] = (byte) ((i >>> 8) & 0xff);
            to[3] = (byte) (i & 0xff);
        } else {
            to[0] = (byte) (i & 0xff);
            to[1] = (byte) ((i >>> 8) & 0xff);
            to[2] = (byte) ((i >>> 16) & 0xff);
            to[3] = (byte) ((i >>> 24) & 0xff);
        }

        return to;
    }

    public static void shortToByteArray(short s, byte[] to, int offset, EEndian endian) throws IllegalArgumentException {
        if (to == null || endian == null) {
            throw new IllegalArgumentException("shortToByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            to[offset] = (byte) ((s >>> 8) & 0xff);
            to[offset + 1] = (byte) (s & 0xff);
        } else {
            to[offset] = (byte) (s & 0xff);
            to[offset + 1] = (byte) ((s >>> 8) & 0xff);
        }
    }

    public static byte[] shortToByteArray(short s, EEndian endian) throws IllegalArgumentException {
        if (endian == null) {
            throw new IllegalArgumentException("shortToByteArray input arg is null");
        }

        byte[] to = new byte[2];

        if (endian == EEndian.BIG_ENDIAN) {
            to[0] = (byte) ((s >>> 8) & 0xff);
            to[1] = (byte) (s & 0xff);
        } else {
            to[0] = (byte) (s & 0xff);
            to[1] = (byte) ((s >>> 8) & 0xff);
        }

        return to;
    }

    public static long longFromByteArray(byte[] from, int offset, EEndian endian) throws IllegalArgumentException {
        if (from == null || endian == null) {
            throw new IllegalArgumentException("longFromByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            return ((from[offset] << 56) & 0xff00000000000000L) | ((from[offset + 1] << 48) & 0xff000000000000L)
                    | ((from[offset + 2] << 40) & 0xff0000000000L) | ((from[offset + 3] << 32) & 0xff00000000L)
                    | ((from[offset + 4] << 24) & 0xff000000) | ((from[offset + 5] << 16) & 0xff0000)
                    | ((from[offset + 6] << 8) & 0xff00) | (from[offset + 7] & 0xff);
        } else {
            return ((from[offset + 7] << 56) & 0xff00000000000000L) | ((from[offset + 6] << 48) & 0xff000000000000L)
                    | ((from[offset + 5] << 40) & 0xff0000000000L) | ((from[offset + 4] << 32) & 0xff00000000L)
                    | ((from[offset + 3] << 24) & 0xff000000) | ((from[offset + 2] << 16) & 0xff0000)
                    | ((from[offset + 1] << 8) & 0xff00) | (from[offset] & 0xff);
        }
    }

    public static int intFromByteArray(byte[] from, int offset, EEndian endian) throws IllegalArgumentException {
        if (from == null || endian == null) {
            throw new IllegalArgumentException("intFromByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            return ((from[offset] << 24) & 0xff000000) | ((from[offset + 1] << 16) & 0xff0000)
                    | ((from[offset + 2] << 8) & 0xff00) | (from[offset + 3] & 0xff);
        } else {
            return ((from[offset + 3] << 24) & 0xff000000) | ((from[offset + 2] << 16) & 0xff0000)
                    | ((from[offset + 1] << 8) & 0xff00) | (from[offset] & 0xff);
        }
    }

    public static short shortFromByteArray(byte[] from, int offset, EEndian endian) throws IllegalArgumentException {
        if (from == null || endian == null) {
            throw new IllegalArgumentException("shortFromByteArray input arg is null");
        }

        if (endian == EEndian.BIG_ENDIAN) {
            return (short) (((from[offset] << 8) & 0xff00) | (from[offset + 1] & 0xff));
        } else {
            return (short) (((from[offset + 1] << 8) & 0xff00) | (from[offset] & 0xff));
        }
    }

    public static String stringPadding(String src, char paddingChar, long expLength, EPaddingPosition paddingpos)
            throws IllegalArgumentException {
        if (src == null || paddingpos == null) {
            throw new IllegalArgumentException("stringPadding input arg is null");
        }

        if (src.length() >= expLength) {
            return src;
        }

        if (paddingpos == EPaddingPosition.PADDING_RIGHT) {

            StringBuffer sb = new StringBuffer(src);
            for (int i = 0; i < expLength - src.length(); i++) {
                sb.append(paddingChar);
            }

            return sb.toString();
        } else {

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < expLength - src.length(); i++) {
                sb.append(paddingChar);
            }

            sb.append(src);
            return sb.toString();
        }
    }

    /**
     * padding position
     * 
     */
    public static enum EPaddingPosition {
        /**
         * padding left
         */
        PADDING_LEFT,
        /**
         * padding right
         */
        PADDING_RIGHT
    }

    /**
     * endian type
     * 
     */
    public static enum EEndian {
        /**
         * little endian
         */
        LITTLE_ENDIAN,
        /**
         * big endian
         */
        BIG_ENDIAN
    }
}
