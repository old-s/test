package com.demo.ctw.rx;

import android.util.Log;


import com.demo.ctw.base.BaseApplication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qiu on 2018/9/6.
 */

public class AppClient {

    private static Retrofit retrofit;

    private static Retrofit retrofit() {
        //使用自己创建的OkHttp，拦截日志
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.API_SERVICE)
                .addConverterFactory(GsonConverterFactory.create())     //json转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //rxjava转换器
                .client(okHttpClient())//使用自己创建的OkHttp，拦截日志
                .build();
        return retrofit;
    }

    private static OkHttpClient okHttpClient() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("qwj", message);
            }
        });
        loggingInterceptor.setLevel(level);
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);
        httpClientBuilder.connectTimeout(8000, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String token = BaseApplication.getInstance().getToken();
                Log.i("qwj", "token         " + token);
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Authorization", token);
                return chain.proceed(builder.build());
            }
        });
        return httpClientBuilder.build();
    }

    public static ApiService getApiService() {
        return retrofit().create(ApiService.class);
    }
}
