package com.wyq.firehelper.article;

import android.app.Fragment;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
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
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;
import com.wyq.firehelper.developKit.eventBus.EventBusMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class WebViewFragment extends Fragment implements View.OnTouchListener {

    private WebView webView;
    private ProgressBar progressBar;
    private float xStart = -1;
    private float yStart = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_webview, container, false);
        webView = (WebView) view.findViewById(R.id.fragment_article_webview);
        progressBar = (ProgressBar) view.findViewById(R.id.fragment_article_progress_bar);

        initWebView();
//        view.setOnTouchListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMessage message) {
        Logger.i("onMessageEvent");
        switch (message.getMessage()) {
            case EventBusMessage.WEBVIEW_GO_BACK:
                if (webView.canGoBack()) {
                    webView.goBack();
                }
                break;
            case EventBusMessage.WEBVIEW_GO_FORWARD:
                if (webView.canGoForward()) {
                    webView.goForward();
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        //避免webview内存泄漏
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    private void initWebView() {
        webView.setOnTouchListener(this);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                //App里面使用webview控件的时候遇到了诸如404这类的错误的时候，若也显示浏览器里面的那种错误提示页面就显得很丑陋了，那么这个时候我们的app就需要加载一个本地的错误提示页面
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
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
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
        });

        // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        //缩放操作
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        if (getUrl() != null && !getUrl().isEmpty())
            webView.loadUrl(getUrl());
    }

    private String getUrl() {
        String url = getArguments().getString("url");
        return url;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xStart = event.getRawX();
                yStart = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                float xEnd = event.getRawX();
                float yEnd = event.getRawY();
//                Logger.i(" x dis:" + (xEnd - xStart)+" start:"+xStart+" end:"+xEnd);
//                Logger.i(" y dis:" + (yEnd - yStart)+" start:"+yStart+" end:"+yEnd);
                if (xEnd - xStart > 100 && Math.abs(yEnd - yStart) < 100) {
//                    EventBus.getDefault().post(new EventBusMessage(EventBusMessage.WEBVIEW_GO_BACK));
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        //如果第一页则直接返回
                        getActivity().onBackPressed();
                    }
                } else if (xEnd - xStart < -100 && Math.abs(yEnd - yStart) < 100) {
                    if (webView.canGoForward()) {
                        webView.goForward();
                    }
                }
                break;

            default:
                break;
        }
        return false;
    }
}
