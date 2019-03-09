package com.wyq.firehelper.media.video;

import android.content.Context;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import static android.os.Build.VERSION_CODES.O;

/**
 * @author Uni.W
 * @date 2018/11/22 15:09
 */
public class FirePlayerView extends SurfaceView implements MediaController.MediaPlayerControl {

    // all possible internal states
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARING = 1;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PLAYING = 3;
    private static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;

    // mCurrentState is a VideoView object's current state.
    // mTargetState is the state that a method caller intends to reach.
    // For instance, regardless the VideoView object's current state,
    // calling pause() intends to bring the object to a target state
    // of STATE_PAUSED.
    private int mCurrentState = STATE_IDLE;
    private int mTargetState = STATE_IDLE;

    /**
     * ************************************************************************
     * settable by the client
     */
    private Uri mUri;
    private Map<String, String> mHeaders;
    private boolean isAutoPlay;

    private IMediaPlayer.OnPreparedListener mOnPreparedListener;
    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener;
    private IMediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener;
    private IMediaPlayer.OnCompletionListener mOnCompletionListener;
    private IMediaPlayer.OnErrorListener mOnErrorListener;
    private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener;
    private IMediaPlayer.OnInfoListener mOnInfoListener;

    //    private SurfaceView surfaceView;
    private IjkMediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private AudioAttributes mAudioAttributes;
    private AudioFocusRequest mAudioFocusRequest;
    private int mAudioSession;
    private int mAudioFocusType = AudioManager.AUDIOFOCUS_GAIN; // legacy focus gain
    private Context mContext;

    private SurfaceHolder mSurfaceHolder = null;
//    private MediaController mMediaController;
    private FireVideoControllerView mMediaController;

    private int mVideoWidth;
    private int mVideoHeight;
    private int mSurfaceWidth;
    private int mSurfaceHeight;

    private int mCurrentBufferPercentage;
    private int mSeekWhenPrepared;  // recording the seek position while preparing

    public FirePlayerView(@NonNull Context context) {
        this(context, null);
    }

    public FirePlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FirePlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FirePlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredWidth = getDefaultSize(mVideoWidth, widthMeasureSpec);
        int measuredHeight = getDefaultSize(mVideoHeight, heightMeasureSpec);

        if (mVideoWidth > 0 && mVideoHeight > 0) {
            int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

            if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
                measuredWidth = widthSpecSize;
                measuredHeight = heightSpecSize;
            } else if (widthSpecMode == MeasureSpec.EXACTLY) {
                measuredWidth = widthSpecSize;
                measuredHeight = widthSpecSize / mVideoWidth * mVideoHeight;
            } else if (heightSpecMode == MeasureSpec.EXACTLY) {
                measuredHeight = heightSpecSize;
                measuredWidth = heightSpecSize / mVideoHeight + mVideoWidth;
            }
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    private void init(Context context) {
//        View view = LayoutInflater.from(context).inflate(R.layout.media_video_fire_player_view_fullscreen_layout,null,false);
//        View view = inflate(context, R.layout.media_video_fire_player_view_fullscreen_layout, this);

//        addView(view);
//        surfaceView = new SurfaceView(context);
//        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        surfaceView.setLayoutParams(params);
//        addView(surfaceView);

        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MOVIE).build();

        getHolder().addCallback(mSHCallback);

        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();

        attachMediaController();

        mCurrentState = STATE_IDLE;
        mTargetState = STATE_IDLE;
    }

    /**
     * Sets video path.
     *
     * @param path the path of the video.
     */
    public FirePlayerView setVideoPath(String path) {
        return setVideoURI(Uri.parse(path));
    }

    /**
     * Sets video URI.
     *
     * @param uri the URI of the video.
     */
    public FirePlayerView setVideoURI(Uri uri) {
        return setVideoURI(uri, null);
    }

    /**
     * Sets video URI using specific headers.
     *
     * @param uri     the URI of the video.
     * @param headers the headers for the URI request.
     *                Note that the cross domain redirection is allowed by default, but that can be
     *                changed with key/value pairs through the headers parameter with
     *                "android-allow-cross-domain-redirect" as the key and "0" or "1" as the value
     *                to disallow or allow cross domain redirection.
     */
    public FirePlayerView setVideoURI(Uri uri, Map<String, String> headers) {
        mUri = uri;
        mHeaders = headers;
        mSeekWhenPrepared = 0;
//        openVideo();
        requestLayout();
        invalidate();
        return this;
    }

    public FirePlayerView autoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
        return this;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Logger.i("onAttachedToWindow");
//        openVideo(true);
//        requestAudio();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Logger.i("onDetachedFromWindow");
//        releaseAudio();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return super.onTouchEvent(event);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);

        if (changedView instanceof FirePlayerView && visibility == View.VISIBLE) {
            if (isVisible(true) && mCurrentState == STATE_IDLE && isAutoPlay) {
                openVideo(false);
            } else {
                pause();
            }
        }
        Logger.i("onVisibilityChanged: " + changedView.getClass().getName() + "  visibility:" + visibility);
    }

    /**
     * 判断视图是否可见
     *
     * @param whole 是否全部可见
     * @return
     */
    public boolean isVisible(boolean whole) {
        boolean isVisible = false;

        Rect rect = new Rect();
        isVisible = getGlobalVisibleRect(rect);
        if (!isVisible) {
            return false;
        }
        if (whole) {
            if (rect.width() < getMeasuredWidth() || rect.height() < getMeasuredHeight()) {
                return false;
            }

        }
        return isVisible;
    }


    /**
     * Sets which type of audio focus will be requested during the playback, or configures playback
     * to not request audio focus. Valid values for focus requests are
     * {@link AudioManager#AUDIOFOCUS_GAIN}, {@link AudioManager#AUDIOFOCUS_GAIN_TRANSIENT},
     * {@link AudioManager#AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK}, and
     * {@link AudioManager#AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE}. Or use
     * {@link AudioManager#AUDIOFOCUS_NONE} to express that audio focus should not be
     * requested when playback starts. You can for instance use this when playing a silent animation
     * through this class, and you don't want to affect other audio applications playing in the
     * background.
     *
     * @param focusGain the type of audio focus gain that will be requested, or
     *                  {@link AudioManager#AUDIOFOCUS_NONE} to disable the use audio focus during playback.
     */
    public void setAudioFocusRequest(int focusGain) {
        if (focusGain != AudioManager.AUDIOFOCUS_NONE
                && focusGain != AudioManager.AUDIOFOCUS_GAIN
                && focusGain != AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
                && focusGain != AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
                && focusGain != AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE) {
            throw new IllegalArgumentException("Illegal audio focus type " + focusGain);
        }
        mAudioFocusType = focusGain;
    }

    public void setOnPreparedListener(IMediaPlayer.OnPreparedListener onPreparedListener) {
        this.mOnPreparedListener = onPreparedListener;
    }

    public void setOnInfoListener(IMediaPlayer.OnInfoListener onInfoListener) {
        this.mOnInfoListener = onInfoListener;
    }

    public void setOnVideoSizeChangedListener(IMediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener) {
        this.mOnVideoSizeChangedListener = onVideoSizeChangedListener;
    }

    public void setOnErrorListener(IMediaPlayer.OnErrorListener onErrorListener) {
        this.mOnErrorListener = onErrorListener;
    }

    public void setOnBufferingUpdateListener(IMediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener) {
        this.mOnBufferingUpdateListener = onBufferingUpdateListener;
    }

    public void setOnCompletionListener(IMediaPlayer.OnCompletionListener onCompletionListener) {
        this.mOnCompletionListener = onCompletionListener;
    }

    public void setOnSeekCompleteListener(IMediaPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        this.mOnSeekCompleteListener = onSeekCompleteListener;
    }

    /**
     * 获取音频焦点
     */
    private void requestAudio() {
        if (Build.VERSION.SDK_INT >= O) {
            mAudioFocusRequest = new AudioFocusRequest.Builder(mAudioFocusType)
                    .setOnAudioFocusChangeListener(onAudioFocusChangeListener)
                    .setAcceptsDelayedFocusGain(true)
                    .setAudioAttributes(mAudioAttributes)
                    .build();
            mAudioManager.requestAudioFocus(mAudioFocusRequest);
        } else {
            mAudioManager.requestAudioFocus(null, mAudioFocusType, 0);
        }
    }

    /**
     * 释放音频焦点
     */
    private void releaseAudio() {
        if (mAudioManager == null || mAudioFocusType == AudioManager.AUDIOFOCUS_NONE) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (mAudioFocusRequest != null) {
                mAudioManager.abandonAudioFocusRequest(mAudioFocusRequest);
            }
        } else {
            mAudioManager.abandonAudioFocus(null);
        }
    }

    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    release();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    //开始播放
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    break;
                default:
                    break;
            }
        }
    };

    public void setMediaController(FireVideoControllerView controller) {
        if (mMediaController != null) {
            mMediaController.hide();
        }
        mMediaController = controller;
        attachMediaController();
    }

    private void attachMediaController() {
        if (mMediaController == null) {
            mMediaController = new FireVideoControllerView(getContext());
        }

        mMediaController.setMediaPlayer(this);
        View anchorView = this.getParent() instanceof View ?
                (View) this.getParent() : this;
        mMediaController.setAnchorView(anchorView);
        mMediaController.setEnabled(true);
        mMediaController.setEnabled(isInPlaybackState());
    }

    public void openVideo(boolean isMultiPlayer) {
        if (mUri == null || mSurfaceHolder == null) {
            return;
        }
        //先释放资源再重新创建播放器
        if (!isMultiPlayer) {
            release();
        }
        createPlayer(isMultiPlayer);

        //获取音频焦点
        if (mAudioFocusType != AudioManager.AUDIOFOCUS_NONE) {
            requestAudio();
        }

        mAudioSession = mMediaPlayer.getAudioSessionId();

        //设置需要的回调接口
        mMediaPlayer.setOnPreparedListener(onPreparedListener);
        mMediaPlayer.setOnCompletionListener(onCompletionListener);
        mMediaPlayer.setOnVideoSizeChangedListener(onVideoSizeChangedListener);
        mMediaPlayer.setOnSeekCompleteListener(onSeekCompleteListener);

        mMediaPlayer.setOnBufferingUpdateListener(onBufferingUpdateListener);
        mCurrentBufferPercentage = 0;
        //设置内容源
        try {
            mMediaPlayer.setDataSource(mContext, mUri, mHeaders);
        } catch (IOException e) {
            e.printStackTrace();
            mCurrentState = STATE_ERROR;
            mTargetState = STATE_ERROR;
            onErrorListener.onError(mMediaPlayer, IjkMediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
            return;
        }
        //设置surfaceHolder,显示图像
        mMediaPlayer.setDisplay(mSurfaceHolder);
        mMediaPlayer.setAudioStreamType(AudioAttributes.CONTENT_TYPE_MOVIE);
        mMediaPlayer.setScreenOnWhilePlaying(true);

        //准备播放，准备好后会回调
        mMediaPlayer.prepareAsync();
        attachMediaController();

        mCurrentState = STATE_PREPARING;
    }

    private void createPlayer(boolean isMulti) {
        if (mMediaPlayer != null && !isMulti) {
            mMediaPlayer.stop();
            mMediaPlayer.setDisplay(null);
            mMediaPlayer.release();
        }
        mMediaPlayer = new IjkMediaPlayer();
    }

    SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mSurfaceHolder = holder;
            if (isAutoPlay) {
                openVideo(false);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            mSurfaceWidth = width;
            mSurfaceHeight = height;
            boolean isValidState = (mTargetState == STATE_PLAYING);
            boolean hasValidSize = (mVideoWidth == width && mVideoHeight == height);
            if (mMediaPlayer != null && isValidState && hasValidSize) {
                if (mSeekWhenPrepared > 0) {
                    seekTo(mSeekWhenPrepared);
                }
                start();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mSurfaceHolder = null;
            if (mMediaController != null) {
                mMediaController.hide();
            }
        }
    };

    IjkMediaPlayer.OnPreparedListener onPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer iMediaPlayer) {
            if (mOnPreparedListener != null) {
                mOnPreparedListener.onPrepared(iMediaPlayer);
            }

            mCurrentState = STATE_PREPARED;

//            IjkMediaMeta mediaMeta = IjkMediaMeta.parse(mMediaPlayer.getMediaMeta());

            mVideoWidth = iMediaPlayer.getVideoWidth();
            mVideoHeight = iMediaPlayer.getVideoHeight();

            if (mMediaController != null) {
                mMediaController.setEnabled(true);
            }

            if (mSeekWhenPrepared > 0) {
                seekTo(mSeekWhenPrepared);
            }

            if (mVideoWidth != 0 && mVideoHeight != 0) {
                getHolder().setFixedSize(mVideoWidth, mVideoHeight);

                if (mSurfaceWidth == mVideoWidth && mSurfaceHeight == mVideoHeight) {
                    if (mTargetState == STATE_PLAYING) {
                        start();
                        if (mMediaController != null) {
                            mMediaController.show(0);
                        } else if (!isPlaying() && (mSeekWhenPrepared != 0 || getCurrentPosition() > 0)) {
                            if (mMediaController != null) {
                                mMediaController.show(0);
                            }
                        }
                    }
                }
            } else {
                if (mTargetState == STATE_PLAYING) {
                    start();
                }
            }
        }
    };

    IjkMediaPlayer.OnVideoSizeChangedListener onVideoSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {
            Logger.i("onVideoSizeChanged:" + i + " " + i1 + " " + i2 + " " + i3);
            if (mOnVideoSizeChangedListener != null) {
                mOnVideoSizeChangedListener.onVideoSizeChanged(iMediaPlayer, i, i1, i2, i3);
            }
        }
    };

    IjkMediaPlayer.OnInfoListener onInfoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
            if (mOnInfoListener != null) {
                mOnInfoListener.onInfo(iMediaPlayer, i, i1);
            }
            return false;
        }
    };

    IjkMediaPlayer.OnErrorListener onErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
            if (mOnErrorListener != null) {
                mOnErrorListener.onError(iMediaPlayer, i, i1);
            }
            return false;
        }
    };

    IjkMediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
            if (mOnBufferingUpdateListener != null) {
                mOnBufferingUpdateListener.onBufferingUpdate(iMediaPlayer, i);
            }
            mCurrentBufferPercentage = i;
        }
    };

    IjkMediaPlayer.OnCompletionListener onCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer iMediaPlayer) {
            if (mOnCompletionListener != null) {
                mOnCompletionListener.onCompletion(iMediaPlayer);
            }

            mCurrentState = STATE_PLAYBACK_COMPLETED;
            mTargetState = STATE_PLAYBACK_COMPLETED;

            if (mAudioFocusType != AudioManager.AUDIOFOCUS_NONE) {
                releaseAudio();
            }
        }
    };

    IjkMediaPlayer.OnSeekCompleteListener onSeekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(IMediaPlayer iMediaPlayer) {
            if (mOnSeekCompleteListener != null) {
                mOnSeekCompleteListener.onSeekComplete(iMediaPlayer);
            }
            iMediaPlayer.seekTo(getCurrentPosition());
        }
    };

    private boolean isInPlaybackState() {
        return (mMediaPlayer != null &&
                mCurrentState != STATE_ERROR &&
                mCurrentState != STATE_IDLE &&
                mCurrentState != STATE_PREPARING);
    }

    @Override
    public void start() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

    @Override
    public void pause() {
        if (isInPlaybackState()) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                mCurrentState = STATE_PAUSED;
            }
            mTargetState = STATE_PAUSED;
        }
    }

    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;

            releaseAudio();
            mCurrentState = STATE_IDLE;
            mTargetState = STATE_IDLE;
        }
    }

    @Override
    public int getDuration() {
        if (isInPlaybackState()) {
            return (int) mMediaPlayer.getDuration();
        }
        return -1;
    }

    @Override
    public int getCurrentPosition() {
        if (isInPlaybackState()) {
            return (int) mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public void seekTo(int pos) {
        if (isInPlaybackState()) {
            mMediaPlayer.seekTo(pos);
            mSeekWhenPrepared = 0;
        } else {
            mSeekWhenPrepared = pos;
        }
    }

    @Override
    public boolean isPlaying() {
        return isInPlaybackState() && mMediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        if (mMediaPlayer != null) {
            return mCurrentBufferPercentage;
        }
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        if (mAudioSession == 0) {
            mAudioSession = mMediaPlayer.getAudioSessionId();
        }
        return mAudioSession;
    }
}
