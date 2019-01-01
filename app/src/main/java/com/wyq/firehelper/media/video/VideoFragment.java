package com.wyq.firehelper.media.video;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: Uni.W
 * Time: 2018/10/31 11:42
 * Desc:
 */
public class VideoFragment extends BaseCaseFragment {

//    @BindView(R.id.media_fragment_video_player_view)
//    public FireVideoControllerView fireVideoControllerView;
    @BindView(R.id.media_fragment_video_view)
    public VideoView videoView;

//    @BindView(R.id.media_fragment_video_recycler_view)
//    public RecyclerView recyclerView;

    @BindView(R.id.media_fragment_video_bt)
    public Button button;

    @BindView(R.id.media_fragment_video_player_view)
    public FirePlayerView firePlayerView;

    private String videoUri = "http://zealervideo-1254235226.file.myqcloud.com/ZEALER-MEDIA/%E7%A7%91%E6%8A%80%E7%9B%B8%E5%AF%B9%E8%AE%BA%E7%AC%AC%E4%BA%94%E5%AD%A3/1029%E7%AC%AC%E4%B8%89%E6%9C%9F%EF%BC%88%E5%AE%9A%EF%BC%89.mp4.f40.mp4";
    private String coverUri = "http://img0.zealer.com/88/c9/a7/5892259ecd92bead93dba05781.jpg";

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
        return R.layout.media_fragment_video;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

        Uri uri = Uri.parse(videoUri);


        FireVideoControllerView fireVideoControllerView = new FireVideoControllerView(getActivity());
        fireVideoControllerView.setMediaPlayer(firePlayerView);
        firePlayerView.setMediaController(fireVideoControllerView);

        firePlayerView.setVideoURI(uri).autoPlay(true).openVideo(true);
        

//        MediaController controller = new MediaController(getActivity());
//        videoView.setMediaController(controller);
//        controller.setMediaPlayer(videoView);
//        videoView.setVideoURI(uri);
//        videoView.start();

//        List uriList = new ArrayList();
//        for(int i = 0;i<10;i++){
//            uriList.add(uri);
//        }
//        VideoRecyclerViewAdapter adapter = new VideoRecyclerViewAdapter(uriList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
//        recyclerView.setAdapter(adapter);

    }

    @OnClick(R.id.media_fragment_video_bt)
    public void onclick() {

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
