package com.wyq.firehelper.media.video;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.media.databinding.MediaFragmentVideoBinding;

/**
 * Author: Uni.W
 * Time: 2018/10/31 11:42
 * Desc:
 */
public class VideoFragment extends BaseCaseFragment {

    public VideoView videoView;

    public FirePlayerView firePlayerView;

    private final String videoUri = "http://zealervideo-1254235226.file.myqcloud.com/ZEALER-MEDIA/%E7%A7%91%E6%8A%80%E7%9B%B8%E5%AF%B9%E8%AE%BA%E7%AC%AC%E4%BA%94%E5%AD%A3/1029%E7%AC%AC%E4%B8%89%E6%9C%9F%EF%BC%88%E5%AE%9A%EF%BC%89.mp4.f40.mp4";
    private final String coverUri = "http://img0.zealer.com/88/c9/a7/5892259ecd92bead93dba05781.jpg";

    @Override
    public String[] getArticleFilters() {
        return null;
    }

    @Override
    public String getToolBarTitle() {
        return "Video";
    }

    @Override
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return MediaFragmentVideoBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

        videoView = ((MediaFragmentVideoBinding)binding).mediaFragmentVideoView;
        firePlayerView = ((MediaFragmentVideoBinding)binding).mediaFragmentVideoPlayerView;

        Uri uri = Uri.parse(videoUri);


        FireVideoControllerView fireVideoControllerView = new FireVideoControllerView(getActivity());
        fireVideoControllerView.setMediaPlayer(firePlayerView);
        firePlayerView.setMediaController(fireVideoControllerView);

        firePlayerView.setVideoURI(uri).autoPlay(true).openVideo(true);

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
        firePlayerView.pause();
        videoView.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        firePlayerView.release();
        videoView.stopPlayback();
    }
}
