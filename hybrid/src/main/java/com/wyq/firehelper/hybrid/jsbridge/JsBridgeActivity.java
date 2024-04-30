package com.wyq.firehelper.hybrid.jsbridge;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.hybrid.databinding.ActivityJsBridgeBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

public class JsBridgeActivity extends BaseActivity implements View.OnClickListener {

    public Toolbar toolbar;
    public BridgeWebView bridgeWebView;
    public Button operateBt;
    public TextView operateTv;

    private final int RESULT_CODE = 0;
    private ValueCallback<Uri> mUploadMessage;

    private ValueCallback<Uri[]> mUploadMessageArray;


    public static void instance(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, JsBridgeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return ActivityJsBridgeBinding.inflate(layoutInflater);
    }

    @Override
    public void initToolBar() {
        initToolBar(toolbar, "JsBridge", true);
    }

    @Override
    public void initView() {
        toolbar = ((ActivityJsBridgeBinding)viewBinding).jsBridgeActivityToolBar.toolbarArticle;
        bridgeWebView = ((ActivityJsBridgeBinding)viewBinding).jsBridgeActivityWebview;
        operateBt = ((ActivityJsBridgeBinding)viewBinding).jsBridgeActivityOperateBt;
        operateTv = ((ActivityJsBridgeBinding)viewBinding).jsBridgeActivityOperateTv;

        operateTv.setMovementMethod(ScrollingMovementMethod.getInstance());
        operateBt.setOnClickListener(this);

        initWebView();
    }

    private void initWebView() {
//        bridgeWebView.setDefaultHandler(new DefaultHandler());
        bridgeWebView.setDefaultHandler(new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                operateTv.append("\nhandle JS 无名函数：" + data);
                function.onCallBack("DefaultHandler:" + data);
            }
        });

        bridgeWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadMessageArray = filePathCallback;
                pickFile();
                return true;
            }
        });

        bridgeWebView.loadUrl("file:///android_asset/demo.html");
        bridgeWebView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                operateTv.append("\n成功调用java方法，java返回:" + data);
                function.onCallBack("\n成功调用java方法:" + data);
            }
        });

        bridgeWebView.callHandler("functionInJs", "我从java来~", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                operateTv.append("\n成功调用JS方法，JS返回:" + data);
            }
        });

        bridgeWebView.send("hello web");
    }

    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, RESULT_CODE);
    }

    @Override
    public void onClick(View v) {

        bridgeWebView.send(operateBt.getText().toString(), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                operateTv.append("\n" + data);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == RESULT_CODE) {
            if (null == mUploadMessage && null == mUploadMessageArray) {
                return;
            }
            if (null != mUploadMessage && null == mUploadMessageArray) {
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }

            if (null == mUploadMessage && null != mUploadMessageArray) {
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUploadMessageArray.onReceiveValue(new Uri[]{result});
                mUploadMessageArray = null;
            }

        }
    }
}
