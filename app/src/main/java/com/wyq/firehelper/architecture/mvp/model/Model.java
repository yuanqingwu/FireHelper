package com.wyq.firehelper.architecture.mvp.model;

import com.orhanobut.logger.Logger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Model implements IModel {
    @Override
    public void queryInfo(final String word, Consumer<Translation> consumer) {
        Observable<Translation> observable = Observable.create(new ObservableOnSubscribe<Translation>() {
            @Override
            public void subscribe(final ObservableEmitter<Translation> emitter) throws Exception {
                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://fanyi.youdao.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                PostRequestInterface requestInterface = retrofit.create(PostRequestInterface.class);
                Call<Translation> call = requestInterface.getCall(word);
//                Logger.i(call.request().url().toString());
                call.enqueue(new Callback<Translation>() {
                    @Override
                    public void onResponse(Call<Translation> call, Response<Translation> response) {
                        emitter.onNext(response.body());
                        emitter.onComplete();
                    }

                    @Override
                    public void onFailure(Call<Translation> call, Throwable t) {
                        Logger.i("failed call");
                        emitter.onComplete();
                    }
                });


            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(consumer);
    }
}
