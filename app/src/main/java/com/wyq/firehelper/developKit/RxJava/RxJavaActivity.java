package com.wyq.firehelper.developKit.RxJava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.article.WebViewActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class RxJavaActivity extends Activity {

    private TextView articleTv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_developkit_rxjava_layout);

        articleTv = (TextView)findViewById(R.id.activity_developkit_rxjava_tv);
        articleTv.setText(ArticleConstants._5_0.getTitle());
        articleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("url",ArticleConstants._5_0.getUrl());
                intent.setClass(RxJavaActivity.this,WebViewActivity.class);
                startActivity(intent);
            }
        });

//        Flowable.just("Hello world")
//                .subscribe(new Consumer<String>() {
//                    @Override public void accept(String s) {
//                        System.out.println(s);
//                    }
//                });

        Disposable d = Observable.just("Hello world!","123")
                .delay(1, TimeUnit.SECONDS)
                .subscribeWith(new DisposableObserver<String>() {
                    @Override public void onStart() {
                        System.out.println("Start!");
                    }
                    @Override public void onNext(String t) {
                        System.out.println(t);
                    }
                    @Override public void onError(Throwable t) {
                        t.printStackTrace();
                    }
                    @Override public void onComplete() {
                        System.out.println("Done!");
                    }
                });




    }
}
