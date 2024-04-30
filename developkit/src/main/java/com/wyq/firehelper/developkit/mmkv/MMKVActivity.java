package com.wyq.firehelper.developkit.mmkv;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.tencent.mmkv.MMKV;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.article.base.BaseCaseActivity;
import com.wyq.firehelper.base.utils.FireHelperUtils;
import com.wyq.firehelper.developkit.databinding.DevelopkitActivityMmkvBinding;

import java.util.List;

public class MMKVActivity extends BaseCaseActivity {

    public TextView textView;

    public MMKV mmkv;
    public SharedPreferences preferences;

    public String data;

    @Override
    public String getToolBarTitle() {
        return "MMKV";
    }

    @Override
    public List getArticleList() {
        return ArticleConstants.getListByFilter("MMKV");
    }

    @Override
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return DevelopkitActivityMmkvBinding.inflate(layoutInflater);
    }

    @Override
    public void initView() {
        textView = ((DevelopkitActivityMmkvBinding)viewBinding).developkitActivityMmkvTv;
        data = FireHelperUtils.readAssets2String(this, "developKit.json");

        textView.setText("MMKV \nwrite:" + initMMKV() + " ns\n" + "read :" + readMMKV() + " ns\n");
        textView.append("\n\nSharedPreferences \nwrite:" + initSharedPreferences() + " ns\n" + "read :" + readSharedPreferences() + " ns\n");

        textView.append("count:" + mmkv.count() + " totalSize:" + mmkv.totalSize());
    }

    public long initMMKV() {
        mmkv = MMKV.defaultMMKV();
        mmkv.clearAll();

        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            mmkv.encode(i + "", data);
        }
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
        for (int i = 0; i < 1000; i++) {
            editor.putString(i + "", data);
        }
        editor.apply();
        return System.nanoTime() - start;
    }

    public long readSharedPreferences() {
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            preferences.getString(i + "", "");
        }
        return System.nanoTime() - start;
    }
}
