package com.wyq.firehelper.activity;



import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wyq.firehelper.R;
import com.wyq.firehelper.utils.DialogUtil;


public class MyWebViewActivity extends Activity {

	private Dialog requestDialog;

	private WebView mall_webview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mall_webview);


		WebSettings settings = mall_webview.getSettings();
		settings.setJavaScriptEnabled(true);

		mall_webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				dismissRequestDialog();
				super.onPageFinished(view, url);
			}
		});

		mall_webview.loadUrl("http://wqs.jd.com");
		showRequestDialog(null);
	}



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if ((keyCode == KeyEvent.KEYCODE_BACK) && mall_webview.canGoBack()) {
			mall_webview.goBack();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	public void dismissRequestDialog() {
		try {
			if (requestDialog != null && requestDialog.isShowing()) {
				requestDialog.dismiss();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showRequestDialog(String tip) {
		try {
			if (requestDialog == null) {
				requestDialog = DialogUtil.creatRequestDialog(this, tip);
			}
			requestDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
