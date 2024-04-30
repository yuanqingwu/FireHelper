package com.wyq.firehelper.media.video;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.wyq.firehelper.article.base.BaseCaseActivity;
import com.wyq.firehelper.media.databinding.MediaActivityVideoBinding;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoActivity extends BaseCaseActivity {

    public JzvdStd jzvdStd;

    public static void instance(Context context){
        context.startActivity(new Intent(context,VideoActivity.class));
    }

    @Override
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return MediaActivityVideoBinding.inflate(layoutInflater);
    }

    @Override
    public String getToolBarTitle() {
        return "Video";
    }

    @Override
    public List getArticleList() {
        return null;
    }

    @Override
    public void initView() {
        jzvdStd = ((MediaActivityVideoBinding)viewBinding).mediaActivityVideoJzvd;
        jzvdStd.setUp("http://zealervideo-1254235226.file.myqcloud.com/ZEALER-MEDIA/%E7%A7%91%E6%8A%80%E7%9B%B8%E5%AF%B9%E8%AE%BA%E7%AC%AC%E4%BA%94%E5%AD%A3/1029%E7%AC%AC%E4%B8%89%E6%9C%9F%EF%BC%88%E5%AE%9A%EF%BC%89.mp4.f40.mp4", "科技相对论", Jzvd.SCREEN_WINDOW_NORMAL);
//        jzvdStd.thumbImageView.setImageURI(Uri.parse("http://img0.zealer.com/88/c9/a7/5892259ecd92bead93dba05781.jpg"));

//        GlideApp.with(this).load("http://img0.zealer.com/88/c9/a7/5892259ecd92bead93dba05781.jpg").into(jzvdStd.thumbImageView);

        jzvdStd.setOnClickListener(view ->{
            jzvdStd.startWindowTiny();
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
