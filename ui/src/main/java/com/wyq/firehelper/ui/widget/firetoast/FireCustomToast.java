package com.wyq.firehelper.ui.widget.firetoast;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import static android.os.Build.VERSION_CODES.KITKAT;

/**
 * 自定义的TOAST,可以在未打开通知权限的情况下显示，以及一些自定义功能
 * https://github.com/bboylin/UniversalToast/blob/master/toast/src/main/java/xyz/bboylin/universialtoast/CustomToast.java
 *
 * https://github.com/Blincheng/EToast2/blob/master/etoast2/src/main/java/com/mic/etoast2/EToast2.java
 *
 *
 * @author yuanqingwu
 * @date 2019/06/11
 */
public class FireCustomToast {



    public static boolean notificationEnabled(Context context){

//if(Build.VERSION.SDK_INT >= KITKAT){
////
//}
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }

    public FireCustomToast(Context context,String text,int duration){

        View view = Toast.makeText(context,text,duration).getView();


    }

}
