package com.wyq.firehelper.base.utils.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    /**
     * 创建目录
     *
     * @param path
     */
    public static void createDirs(File path) {
        if (path != null && !path.exists()) {
            path.mkdirs();
        }
    }

    /**
     * 文件是否存在
     *
     * @param file
     * @return
     */
    public static boolean isFileExist(File file) {
        return file != null && file.exists();
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

    /**
     * �?��sdcard是否可用
     *
     * @return true为可用，否则为不可用
     */
    public static boolean sdCardIsAvailable() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 验证手机号格式是否正�?
     *
     * @param mobileNumber
     * @return
     */
    public static boolean validateMobileNumber(String mobileNumber) {
        return matchingText("^(13[0-9]|15[0-9]|18[7|8|9|6|5])\\d{4,8}$", mobileNumber);
    }

    /**
     * 验证字符�?是否适合某种格式
     *
     * @param expression 正则表达�?
     * @param text       操作的字符串
     * @return
     */
    private static boolean matchingText(String expression, String text) {
        Pattern p = Pattern.compile(expression); // 正则表达�?
        Matcher m = p.matcher(text); // 操作的字符串
        boolean b = m.matches();
        return b;
    }

    /**
     * �?��网络状�?
     */
    public static boolean hasNetwork(Context context) {
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    /**
     * �?��网络状�?2
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); //获取系统网络连接管理�?
        if (connectivity == null) { //如果网络管理器为null
            return false; //返回false表明网络无法连接
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo(); //获取�?��的网络连接对�?
            if (info != null) { //网络信息不为null�?
                for (int i = 0; i < info.length; i++) { //遍历网路连接对象
                    if (info[i].isConnected()) { //当有�?��网络连接对象连接上网络时
                        return true; //返回true表明网络连接正常
                    }
                }
            }
        }
        return false;
    }

    public static boolean isMobileNetworkAvailable(Context context) {
        //获取应用上下�?
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取系统的连接服�?
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        //获取网络的连接情�?
        //判断3G�?
        return activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 版本�?
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            String name = appInfo.metaData.getString("version_name");
            if (name != null) {
                return name;
            }
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 渠道�?
     *
     * @param context
     * @param metaName
     * @return
     */
    public static int getChannel(Context context, String metaName) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getInt(metaName);

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 字符串转成int
     *
     * @param str
     * @return
     */
    public static int parseStr2Int(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * 字符串转成int
     *
     * @param str
     * @return
     */
    public static float parseStr2Float(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * 判断字符串是否是合法�?6进制�?
     *
     * @param str
     * @return
     * @author: Xue Wenchao
     * @return: boolean
     * @date: 2014-1-21 上午10:13:23
     */
    public static boolean isHexString(String str) {
        if (str == null) {
            return false;
        }
        return Pattern.matches("^[0-9a-fA-F]++$", str);
    }

    /**
     * 字符串转成Long
     *
     * @param str
     * @return
     */
    public static long parseStr2Long(String str) {
        if (str == null) {
            return -1;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * 隐藏输入键盘
     *
     * @param view
     * @param context
     */
    public static void hideSoftInput(EditText view, Context context) {
        InputMethodManager inputMeMana = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMeMana.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示软键�?
     */
    public static void showSoftInput(Context context) {
        InputMethodManager inputMeMana = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMeMana.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 判断字符串是否为�?
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() <= 0;
    }

    /**
     * 计算字符个数，一个汉字算两个
     *
     * @param s
     * @return
     */
    public static int countWord(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int n = s.length(), a = 0, b = 0;
        int len = 0;
        char c;
        for (int i = 0; i < n; i++) {
            c = s.charAt(i);
            if (Character.isSpaceChar(c)) {
                ++b;
            } else if (isAscii(c)) {
                ++a;
            } else {
                ++len;
            }
        }
        return len + (int) Math.ceil((a + b) / 2.0);
    }

    public static boolean isAscii(char c) {
        return c <= 0x7f;
    }

    /**
     * 验证邮箱地址是否合法
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    /**
     * 过滤文本中的html脚本信息
     *
     * @param inputString
     * @return
     */
    public static String Html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;
        Pattern p_html1;
        Matcher m_html1;
        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{�?script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{�?style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); // 过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;// 返回文本字符�?
    }

    /**
     * 写图片到SD�?
     *
     * @param bitmap
     * @throws IOException
     */
    public static void saveBitmap(Bitmap bitmap, String filePath) {
        File file = new File(filePath);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            LogUtils.printStackTrace(e);
        } catch (IOException e) {
            LogUtils.printStackTrace(e);
        }
    }

    /**
     * 从网络下载图片并保存到指定路�?
     *
     * @param imgUrl
     * @param filePath
     */
    public static void downloadImageAndSave(String imgUrl, String filePath) {
        URL url;
        InputStream is = null;
        FileOutputStream fos = null;
        URLConnection conn;
        try {
            url = new URL(imgUrl);
            conn = url.openConnection();
            is = conn.getInputStream();
            fos = new FileOutputStream(new File(filePath));
            CommonUtils.copyStream(is, fos);
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        } finally {
            try {
                is.close();
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 拷贝�?
     *
     * @param is
     * @param os
     */
    public static void copyStream(InputStream is, OutputStream os) throws IOException {
        if (is == null || os == null) {
            return;
        }
        BufferedInputStream bufIs;
        boolean shouldClose = false;
        if (is instanceof BufferedInputStream) {
            bufIs = (BufferedInputStream) is;
        } else {
            bufIs = new BufferedInputStream(is);
            shouldClose = true;
        }

        int bufLen = 102400;
        byte[] buf = new byte[bufLen];
        int len;
        while (true) {
            len = bufIs.read(buf);
            if (len < 0) {
                break;
            }
            os.write(buf, 0, len);
        }
        if (shouldClose) {
            bufIs.close();
        }
    }



    public static int calculateCharLength(String src) {
        int counter = -1;
        if (src != null) {
            counter = 0;
            final int len = src.length();
            for (int i = 0; i < len; i++) {
                char sigleItem = src.charAt(i);
                if (isAlphanumeric(sigleItem)) {
                    counter++;
                } else if (Character.isLetter(sigleItem)) {
                    counter = counter + 2;
                } else {
                    counter++;
                }
            }
        } else {
            counter = -1;
        }

        return counter;
    }

    /**
     * 判断字符是否为英文字母或者阿拉伯数字.
     *
     * @param ch char字符
     * @return true or false
     */
    public static boolean isAlphanumeric(char ch) {
        // 常量定义
        final int DIGITAL_ZERO = 0;
        final int DIGITAL_NINE = 9;
        final char MIN_LOWERCASE = 'a';
        final char MAX_LOWERCASE = 'z';
        final char MIN_UPPERCASE = 'A';
        final char MAX_UPPERCASE = 'Z';

        return (ch >= DIGITAL_ZERO && ch <= DIGITAL_NINE) || (ch >= MIN_LOWERCASE && ch <= MAX_LOWERCASE)
                || (ch >= MIN_UPPERCASE && ch <= MAX_UPPERCASE);
    }

    /**
     * decode js用escape编码的字符串
     *
     * @param src
     * @return
     * @method: unEscape
     * @description: TODO
     * @author: DongFuhai
     * @return: String
     * @date: 2013-10-14 下午5:57:56
     */
    public static String unEscape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }
}
