package com.wyq.firehelper.device.PhoneInfo;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.utils.CommonUtils;

import butterknife.BindView;

public class PhoneInfoActivity extends BaseActivity {

    @BindView(R.id.device_activity_phone_info_tv)
    public TextView infoTv;

    @Override
    protected int attachLayoutRes() {
        return R.layout.device_activity_phone_info;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {
        StringBuilder builder = new StringBuilder();
        builder.append("Screen:\n");
        builder.append("\nwidth:"+CommonUtils.getScreenWidth(this)+" px");
        builder.append("\nheight:"+CommonUtils.getScreenHeight(this)+" px");
        builder.append("\nScreenWidth:"+CommonUtils.getWinWidth(this)+" px");
        builder.append("\nScreenHeight:"+CommonUtils.getWinHeight(this)+" px");
        builder.append("\nNavigationBarHeight:"+CommonUtils.getNavigationBarHeight(this)+" px");
        builder.append("\nStatusBarHeight:"+CommonUtils.getStatusBarHeight(this)+" px");
        infoTv.setText(builder.toString());
    }


    public static void instance(Context context){
        context.startActivity(new Intent(context,PhoneInfoActivity.class));
    }
}
