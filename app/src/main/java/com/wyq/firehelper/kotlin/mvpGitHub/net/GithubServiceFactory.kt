package com.wyq.firehelper.kotlin.mvpGitHub.net

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object GithubServiceFactory {

    private val BASE_URL: String = "https://api.github.com/"

    private fun makeOkHttpClient(): OkHttpClient {

        //apply函数：调用某对象的apply函数，在函数范围内，可以任意调用该对象的任意方法，并返回该对象
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder().apply {
            networkInterceptors().add(Interceptor { chain ->
                val original = chain.request()
                val request = original
                        .newBuilder()
                        .method(original.method(), original.body())
                        .build()
                chain.proceed(request)
            })
            addInterceptor(interceptor)
        }.build()
    }

    fun makeRetrofit(): GitHubService {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
                .client(makeOkHttpClient())
                .build()
        return retrofit.create(GitHubService::class.java)
    }
}