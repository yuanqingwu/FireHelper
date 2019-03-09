package com.wyq.firehelper.architecture.archcomponents;

import java.io.IOException;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//FIXME ApiResponse 是对于 Retrofit2.Call 类的简单封装，用以将其响应转换为 LiveData。
public class ApiResponse<T> implements Call<T> {


    public String errorMessage;
    public T body;

    public Call<T> call;

    public Response response;

    public boolean isSuccessFul() {
        if (response != null) {
            return response.isSuccessful();
        }
        return false;
    }

    @Override
    public Response<T> execute() throws IOException {
        response = call.execute();
        return response;
    }

    @Override
    public void enqueue(Callback<T> callback) {
        call.enqueue(callback);
    }

    @Override
    public boolean isExecuted() {
        return call.isExecuted();
    }

    @Override
    public void cancel() {
        call.cancel();
    }

    @Override
    public boolean isCanceled() {
        return call.isCanceled();
    }

    @Override
    public Call<T> clone() {
        return call.clone();
    }

    @Override
    public Request request() {
        return call.request();
    }
}
