package com.wyq.firehelper.media.video;

import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseActivity;
import com.wyq.firehelper.developkit.glide.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoActivity extends BaseCaseActivity {

    @BindView(R.id.media_activity_video_jzvd)
    public JzvdStd jzvdStd;
    @BindView(R.id.media_activity_video_bt)
    public Button button;


    public static void instance(Context context){
        context.startActivity(new Intent(context,VideoActivity.class));
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.media_activity_video;
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
        jzvdStd.setUp("http://zealervideo-1254235226.file.myqcloud.com/ZEALER-MEDIA/%E7%A7%91%E6%8A%80%E7%9B%B8%E5%AF%B9%E8%AE%BA%E7%AC%AC%E4%BA%94%E5%AD%A3/1029%E7%AC%AC%E4%B8%89%E6%9C%9F%EF%BC%88%E5%AE%9A%EF%BC%89.mp4.f40.mp4", "科技相对论", Jzvd.SCREEN_WINDOW_NORMAL);
//        jzvdStd.thumbImageView.setImageURI(Uri.parse("http://img0.zealer.com/88/c9/a7/5892259ecd92bead93dba05781.jpg"));

        GlideApp.with(this).load("http://img0.zealer.com/88/c9/a7/5892259ecd92bead93dba05781.jpg").into(jzvdStd.thumbImageView);
    }

    @OnClick(R.id.media_activity_video_bt)
    public void onclick() {
        jzvdStd.startWindowTiny();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        if (Jzvd.backPress()) {
            return;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
