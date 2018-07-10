package com.wyq.firehelper.developKit.RxJava;

import android.os.Bundle;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.developKit.DevelopKitBaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class RxJavaActivity extends DevelopKitBaseActivity {

    @BindView(R.id.activity_developkit_rxjava_tv)
    public TextView articleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developkit_activity_rxjava_layout);

        ButterKnife.bind(this);

        Disposable d = Observable.just("Hello world!", "123")
                .delay(1, TimeUnit.SECONDS)
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onStart() {
                        System.out.println("Start!");
                    }

                    @Override
                    public void onNext(String t) {
                        System.out.println(t);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Done!");
                    }
                });
    }

    @Override
    public void initData() {
        resourceList.put(articleTv, ArticleConstants.DEVKIT_REACTIVEX_RXJAVA_0);
    }

    @Override
    public void initView() {
        browserArticle(RxJavaActivity.this);
    }
}
