package com.wyq.firehelper.developKit.mmkv;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.tencent.mmkv.MMKV;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.utils.FireHelperUtils;

import butterknife.BindView;

public class MMKVActivity extends BaseActivity {

    @BindView(R.id.developkit_activity_mmkv_tv)
    public TextView textView;

    public MMKV mmkv;
    public SharedPreferences preferences;

    public String data;


    @Override
    protected int attachLayoutRes() {
        return R.layout.developkit_activity_mmkv;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {
        data = FireHelperUtils.readAssets2String(this, "developKit.json");

        textView.setText("MMKV \nwrite:" + initMMKV() + " ns\n" + "read :" + readMMKV() + " ns\n");
        textView.append("\n\nSharedPreferences \nwrite:" + initSharedPreferences() + " ns\n" + "read :" + readSharedPreferences() + " ns\n");

        textView.append("count:" + mmkv.count() + " totalSize:" + mmkv.totalSize());
    }

    public long initMMKV() {
        mmkv = MMKV.defaultMMKV();
        mmkv.clearAll();

        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++)
            mmkv.encode(i + "", data);
        return System.nanoTime() - start;

//        Logger.i(""+mmkv.count()+"  "+mmkv.totalSize());
    }

    public long readMMKV() {
        long start = System.nanoTime();
        for (int i = 0; i < mmkv.count(); i++) {
            mmkv.decodeString("" + i);
        }
        return System.nanoTime() - start;
    }

    public long initSharedPreferences() {
        preferences = getSharedPreferences("mmkvtest", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++)
            editor.putString(i + "", data);
        editor.apply();
        return System.nanoTime() - start;
    }

    public long readSharedPreferences() {
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++)
            preferences.getString(i + "", "");
        return System.nanoTime() - start;
    }
}
