package com.wyq.firehelper.media.video;

import android.view.View;
import android.widget.Button;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.developkit.glide.GlideApp;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * Author: Uni.W
 * Time: 2018/10/31 11:42
 * Desc:
 */
public class VideoFragment extends BaseCaseFragment {

    @BindView(R.id.media_activity_video_jzvd)
    public JzvdStd jzvdStd;
    @BindView(R.id.media_activity_video_bt)
    public Button button;

    @Override
    public String[] getArticleFilters() {
        return null;
    }

    @Override
    public String getToolBarTitle() {
        return "Video";
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.media_activity_video;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        jzvdStd.setUp("http://zealervideo-1254235226.file.myqcloud.com/ZEALER-MEDIA/%E7%A7%91%E6%8A%80%E7%9B%B8%E5%AF%B9%E8%AE%BA%E7%AC%AC%E4%BA%94%E5%AD%A3/1029%E7%AC%AC%E4%B8%89%E6%9C%9F%EF%BC%88%E5%AE%9A%EF%BC%89.mp4.f40.mp4", "科技相对论", Jzvd.SCREEN_WINDOW_NORMAL);
//        jzvdStd.thumbImageView.setImageURI(Uri.parse("http://img0.zealer.com/88/c9/a7/5892259ecd92bead93dba05781.jpg"));

        GlideApp.with(getActivity()).load("http://img0.zealer.com/88/c9/a7/5892259ecd92bead93dba05781.jpg").into(jzvdStd.thumbImageView);
    }

    @OnClick(R.id.media_activity_video_bt)
    public void onclick() {
        jzvdStd.startWindowTiny();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
            Jzvd.releaseAllVideos();
    }
}
