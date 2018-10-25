package com.wyq.firehelper.article;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;
import com.wyq.firehelper.article.entity.ArticleResource;
import com.wyq.firehelper.article.entity.ArticleSaveEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.activity_webview_webview)
    public WebView webView;
    @BindView(R.id.activity_webview_progress_bar)
    public ProgressBar progressBar;
    @BindView(R.id.activity_webview_toolbar)
    public Toolbar toolbar;
    @BindView(R.id.activity_webview_toolbar_nail)
    public ImageView nailImage;

    private static final String PARAM_NAME = "URL";
    private float xStart = -1;
    private float yStart = -1;
    private String url;//原始的URL，对应ArticleConstants
    private String newUrl = "";//经过多次点击后的URL
    public boolean canDrag = false;//默认此页面不支持右滑返回

    private boolean isSaved = false;

    private Bitmap webFavicon;
    private String webTitle;

    public WebViewClient webViewClient;
    public WebChromeClient webChromeClient;
    public static final String WEBVIEW_CACHE_DIR = "webViewCache/";

    public static void instance(Context context, String url) {
        Intent intent = new Intent();
        intent.putExtra(PARAM_NAME, url);
        ArticleRepository.getInstance().commitHotDegree(url);
        intent.setClass(context, WebViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_layout);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra(PARAM_NAME);
        Logger.d(url);
        initView();
    }

    public void initView() {
        toolbar.setTitle(url);
        setSupportActionBar(toolbar);
        initToolbarNav(toolbar);

        initWebView();
    }

    protected void initToolbarNav(Toolbar toolbar) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        isSaved = checkIsSaved(url);

        nailImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isSaved) {
                    cancelSave();
                } else {
                    if (saveArticle()) {
                        Toast.makeText(getApplicationContext(), "已收藏", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "收藏失败", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    /**
     * 保存文章到MMKV
     *
     * @return
     */
    public boolean saveArticle() {
        ArticleResource resource = ArticleConstants.getArticleByUrl(url);
        int scrollY = webView.getScrollY();
        float scaleSize = webView.getScaleY();
        Bitmap webIcon = webFavicon == null ? BitmapFactory.decodeResource(getResources(), R.drawable.ic_image_place_holder_128px) : webFavicon;
        String title = (webTitle == null || webTitle.isEmpty()) ? url : webTitle;
        ArticleSaveEntity saveEntity = new ArticleSaveEntity(resource, title, webIcon, newUrl, scrollY, scaleSize, "", System.currentTimeMillis());
        boolean result = ArticleRepository.getInstance().save(url, saveEntity);
        if (result) {
            isSaved = true;
            nailImage.setImageDrawable(getDrawable(R.drawable.ic_nail_purple_64px));
        }
        return result;
    }

    /**
     * 检查是否是已收藏文章，如果已收藏则恢复上次保存位置
     *
     * @param url
     * @return
     */
    public boolean checkIsSaved(String url) {
        boolean hasSaved = ArticleRepository.getInstance().contains(url);
//        Logger.i("hasSaved:" + hasSaved + " url:" + url);
        if (hasSaved) {
            nailImage.setImageDrawable(getDrawable(R.drawable.ic_nail_purple_64px));
            //恢复上次阅读位置
            recoverLocation();
        } else {
            nailImage.setImageDrawable(getDrawable(R.drawable.ic_nail_white_64px));
        }

        return hasSaved;
    }

    public void cancelSave() {
        if (ArticleRepository.getInstance().delete(url)) {
            isSaved = false;
            nailImage.setImageDrawable(getDrawable(R.drawable.ic_nail_white_64px));
        }
    }

    /**
     * 恢复上次保存位置
     */
    private void recoverLocation() {
        ArticleSaveEntity entity = ArticleRepository.getInstance().get(url);
        if (entity != null) {
            String savedUrl = entity.getNewUrl();
            if (savedUrl != null && !savedUrl.isEmpty() && !savedUrl.equals(url)) {
                setNewUrl(savedUrl);
            }

            int scrollY = entity.getScrollY();
            float scale = entity.getScaleSize();
            if (webView != null) {
                webView.setScaleY(scale);
                webView.scrollTo(0, scrollY);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        Logger.i("WebViewActivity onNewIntent");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //避免webview内存泄漏
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.removeAllViews();
            webView.destroy();
        }
        if (webViewClient != null) {
            webViewClient = null;
        }
        if (webChromeClient != null) {
            webChromeClient = null;
        }
        super.onDestroy();

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void initWebView() {
        webViewClient = new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                //可以加载一个本地的错误提示页面
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();//表示等待证书响应
                // handler.cancel();      //表示挂起连接，为默认方式
                // handler.handleMessage(null);    //可做其他处理
                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                setNewUrl(url);
            }
        };
        webView.setWebViewClient(webViewClient);
        webChromeClient = new WebChromeClient() {
            //获得网页的加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }

            //获取Web页中的标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title != null && !title.isEmpty()) {
                    toolbar.setTitle(title);
                    webTitle = title;
                }
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                if (icon != null) {
                    webFavicon = icon;
                    Logger.i("receive icon:" + icon.getByteCount());
                }
            }

            //支持javascript的警告框
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            //支持javascript的确认框
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            //支持javascript输入框
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        };

        webView.setWebChromeClient(webChromeClient);

        // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDomStorageEnabled(true);
        String webViewCacheDir = getFilesDir().getAbsolutePath() + WEBVIEW_CACHE_DIR;
        settings.setAppCachePath(webViewCacheDir);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);

        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        //缩放操作
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        if (getUrl() != null && !getUrl().isEmpty()) {
            webView.loadUrl(getUrl());
            Logger.d(getUrl());
        }
    }

    /**
     * 获取要加载的URL
     *
     * @return
     */
    private String getUrl() {
        return newUrl.isEmpty() ? url : newUrl;
    }

    private void setNewUrl(String url) {
        this.newUrl = url;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (canDrag) {
            int width = getResources().getDisplayMetrics().widthPixels;

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xStart = event.getX(0);
                    yStart = event.getY(0);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (event.getPointerCount() == 1) {
                        float xNow = event.getX(0);
                        if (xStart < width / 4 && Math.abs(xNow - xStart) > 100 && Math.abs(event.getY(0) - yStart) < 100) {
                            float xDist = xNow - xStart - 100;
                            float alpha = 1 - xDist / width;
                            webView.setX(xDist);
                            webView.setAlpha(alpha);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (event.getPointerCount() == 1) {
                        float xEnd = event.getX(0);
                        float yEnd = event.getY(0);

//                Logger.i(" x dis:" + (xEnd - xStart)+" start:"+xStart+" end:"+xEnd);
//                Logger.i(" y dis:" + (yEnd - yStart)+" start:"+yStart+" end:"+yEnd);
                        //&& Math.abs(yEnd - yStart) < 100
                        if (xStart < width / 4 && xEnd - xStart > width / 2) {
//                    EventBus.getDefault().post(new EventBusMessage(EventBusMessage.WEBVIEW_GO_BACK));
                            if (webView.canGoBack()) {
                                webView.goBack();
                                webView.setAlpha(1);
                                webView.setX(0);
//                        webView.setTranslationX(0);
                            } else {
                                //如果第一页则直接返回
                                webView.setAlpha(0);
                                finish();
                            }

                            return true;
                        } else if (xStart > width * 3 / 4 && xEnd - xStart < -100) {
                            if (webView.canGoForward()) {
                                webView.goForward();
                            }
                            return true;
                        } else {
                            webView.setAlpha(1);
                            webView.setX(0);
                        }
                    }
                    break;

                default:
                    break;
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
