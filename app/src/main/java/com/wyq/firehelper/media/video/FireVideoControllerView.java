package com.wyq.firehelper.media.video;


import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.developkit.glide.GlideApp;

import java.util.Formatter;
import java.util.Locale;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * @author uni.w
 * @date 2018/11/27 18:41
 */
public class FireVideoControllerView extends FrameLayout {

    //******************控制层控件***********
    /**
     * 简单模式，提供暂停控件
     */
    private static final int MODE_SIMPLE = 0;
    /**
     * 全屏播放模式，提供全量的控制控件
     */
    private static final int MODE_FULL_SCREEN = 1;
    /**
     * 悬浮窗模式
     */
    private static final int MODE_FLOAT_WINDOW = 2;

    @IntDef({MODE_SIMPLE, MODE_FULL_SCREEN, MODE_FLOAT_WINDOW})
    public @interface ViewMode {
    }

    //上

    private ImageView backIv;
    private TextView titleTv;
    private TextView timeTv;
    private ImageView batteryIv;
    private Button clarityBt;
    private PopupWindow clarityPopupWindow;
    private ImageView settingIv;

    //中

    private ImageView coverIv;
    private ImageView lockControllLayerIv;
    private ProgressBar loadingProgressBar;
    private Button retryBt;
    private ImageView pauseIv;

    //下

    private TextView currentVideoTime;
    private ProgressBar mProgress;
    private TextView totalVideoTime;


    private boolean mShowing;
    private boolean mDragging;
    private static final int sDefaultTimeout = 3000;
    private final AccessibilityManager mAccessibilityManager;

    private MediaController.MediaPlayerControl mPlayer;
    private final Context mContext;
    private View mAnchor;
    private View mRoot;
    private WindowManager mWindowManager;
    private Window mWindow;
    private View mDecor;
    private WindowManager.LayoutParams mDecorLayoutParams;
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;

    public FireVideoControllerView(@NonNull Context context) {
        this(context, null);
    }

    public FireVideoControllerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FireVideoControllerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FireVideoControllerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        mRoot = this;
        initFloatingWindowLayout();
        initFloatingWindow();
        mAccessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);

    }

    private void initFloatingWindow() {
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//        mWindow = new PhoneWindow(mContext);
        Dialog dialog = new Dialog(mContext);
        mWindow = dialog.getWindow();
        mWindow.setWindowManager(mWindowManager, null, null);
        mWindow.requestFeature(Window.FEATURE_NO_TITLE);
        mDecor = mWindow.getDecorView();
        mDecor.setOnTouchListener(mOnTouchListener);
        mWindow.setContentView(this);
        mWindow.setBackgroundDrawableResource(android.R.color.transparent);

        // While the media controller is up, the volume control keys should
        // affect the media stream type
        mWindow.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        setFocusable(true);
        setFocusableInTouchMode(true);
        setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        requestFocus();
    }

    private void initFloatingWindowLayout() {
        mDecorLayoutParams = new WindowManager.LayoutParams();
        WindowManager.LayoutParams p = mDecorLayoutParams;
        p.gravity = Gravity.TOP | Gravity.LEFT;
        p.height = LayoutParams.WRAP_CONTENT;
        p.x = 0;
        p.format = PixelFormat.TRANSLUCENT;
        p.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        p.flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM |
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_SPLIT_TOUCH;
        p.token = null;
        p.windowAnimations = 0;// android.R.style.DropDownAnimationDown;

    }

    /**
     * Update the dynamic parts of mDecorLayoutParams
     * Must be called with mAnchor != NULL.
     */
    private void updateFloatingWindowLayout() {
        int[] anchorPos = new int[2];
        mAnchor.getLocationOnScreen(anchorPos);

        mDecor.measure(MeasureSpec.makeMeasureSpec(mAnchor.getWidth(), MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(mAnchor.getHeight(), MeasureSpec.AT_MOST));
        WindowManager.LayoutParams p = mDecorLayoutParams;
        p.x = anchorPos[0] + (mAnchor.getWidth() - p.width) / 2;
        p.y = anchorPos[1] + mAnchor.getHeight() - mDecor.getMeasuredHeight();
    }

    // This is called whenever mAnchor's layout bound changes
    private final OnLayoutChangeListener mOnLayoutChangeListener = new OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            updateFloatingWindowLayout();
            if(mShowing){
                mWindowManager.updateViewLayout(mDecor,mDecorLayoutParams);
            }
        }
    };

    /**
     * Set the view that acts as the anchor for the control view.
     * This can for example be a VideoView, or your Activity's main view.
     * When VideoView calls this method, it will use the VideoView's parent
     * as the anchor.
     * @param view The view to which to anchor the controller when it is visible.
     */
    public void setAnchorView(View view) {
        if(mAnchor != null){
            mAnchor.removeOnLayoutChangeListener(mOnLayoutChangeListener);
        }
        mAnchor = view;
        if(mAnchor != null){
            mAnchor.addOnLayoutChangeListener(mOnLayoutChangeListener);
        }

        FrameLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        removeAllViews();

        View v = makeControllerView();
        addView(v,layoutParams);
    }

    private View makeControllerView(){
        mRoot = LayoutInflater.from(mContext).inflate(R.layout.media_video_fire_player_view_fullscreen_layout, null, false);
        initControllerView(mRoot);
        return mRoot;
    }

    private void initControllerView(View v) {
        loadingProgressBar = v.findViewById(R.id.media_video_fire_player_view_center_loading_progress_bar);
        mProgress = v.findViewById(R.id.media_video_fire_player_view_bottom_seekbar);
        coverIv = v.findViewById(R.id.media_video_fire_player_view_cover_iv);
        pauseIv = v.findViewById(R.id.media_video_fire_player_view_center_pause_img);


        pauseIv.setOnClickListener(mPauseListener);
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

    }

    public void setMediaPlayer(MediaController.MediaPlayerControl player){
        mPlayer = player;
        updatePausePlay();
    }

    public void show(int timeout) {
        if (!mShowing && mAnchor != null) {
            setProgress();
            if (pauseIv != null) {
                pauseIv.requestFocus();
            }
//            disableUnsupportedButtons();
            updateFloatingWindowLayout();
            mWindowManager.addView(mDecor, mDecorLayoutParams);
            mShowing = true;
        }
        updatePausePlay();

        // cause the progress bar to be updated even if mShowing
        // was already true.  This happens, for example, if we're
        // paused with the progress bar showing the user hits play.
        post(mShowProgress);

        if (timeout != 0 && !mAccessibilityManager.isTouchExplorationEnabled()) {
            removeCallbacks(mFadeOut);
            postDelayed(mFadeOut, timeout);
        }
    }

    public void hide() {
        if (mAnchor == null) {
            return;
        }

        if (mShowing) {
            try {
                removeCallbacks(mShowProgress);
                mWindowManager.removeView(mDecor);
            } catch (IllegalArgumentException ex) {
            }
            mShowing = false;
        }
    }

    private final Runnable mShowProgress = new Runnable() {
        @Override
        public void run() {
            int pos = setProgress();
            if (!mDragging && mShowing && mPlayer.isPlaying()) {
                postDelayed(mShowProgress, 1000 - (pos % 1000));
            }
        }
    };

    private final Runnable mFadeOut = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    private int setProgress() {
        if (mPlayer == null || mDragging) {
            return 0;
        }
        int position = mPlayer.getCurrentPosition();
        int duration = mPlayer.getDuration();
        if (mProgress != null) {
            if (duration > 0) {
                // use long to avoid overflow
                long pos = 1000L * position / duration;
                mProgress.setProgress((int) pos);
            }
            int percent = mPlayer.getBufferPercentage();
            mProgress.setSecondaryProgress(percent * 10);
        }

        if (totalVideoTime != null) {
            totalVideoTime.setText(stringForTime(duration));
        }
        if (currentVideoTime != null) {
            currentVideoTime.setText(stringForTime(position));
        }

        return position;
    }

    /**
     * 执行暂停或者播放
     */
    private void doPauseResume(){
        if(mPlayer.isPlaying()){
            mPlayer.pause();
        }else{
            mPlayer.start();
        }

        updatePausePlay();
    }

    /**
     * 更新暂停图标
     */
    private void updatePausePlay(){
        if(mRoot == null || pauseIv == null){
            return;
        }
        if(mPlayer.isPlaying()){
            pauseIv.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ic_vd_pause_circle_outline_gray_24dp));
        }else{
            pauseIv.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ic_vd_play_circle_outline_gray_24dp));
        }
    }

    OnClickListener mPauseListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            doPauseResume();
            show(sDefaultTimeout);
        }
    };

    IMediaPlayer.OnPreparedListener onPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer iMediaPlayer) {
            loadingProgressBar.setVisibility(GONE);
            coverIv.setVisibility(GONE);
            hide();
        }
    };

    IMediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
            mProgress.setProgress(i);
            if (i > 0) {
                loadingProgressBar.setVisibility(GONE);
                coverIv.setVisibility(GONE);
            }
        }
    };

//    public FirePlayerView getFirePlayerView(Uri uri) {
//        firePlayerView.setVideoURI(uri);
//        return firePlayerView;
//    }
//
//    public FireVideoControllerView autoPlay() {
//        firePlayerView.autoPlay(true);
//        pauseIv.setVisibility(GONE);
//        return this;
//    }

    private final OnTouchListener mOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (mShowing) {
                    hide();
                }
            }
            return false;
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                show(0); // show until hide is called
                break;
            case MotionEvent.ACTION_UP:
                show(sDefaultTimeout); // start timeout
                break;
            case MotionEvent.ACTION_CANCEL:
                hide();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 设置封面
     *
     * @param coverUri
     * @return
     */
    public FireVideoControllerView cover(String coverUri) {
        GlideApp.with(getContext()).load(coverUri).centerCrop().into(coverIv);
        return this;
    }

    public void showLoadingView() {
        loadingProgressBar.setVisibility(VISIBLE);
    }

    public void hideLoadingView() {
        loadingProgressBar.setVisibility(GONE);
    }

    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }
}
