package com.example.wuyining20180616.tuyi.api;

import android.os.Environment;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class RetrofitHelper {
    public static OkHttpClient okHttpClient;
    public static ApiService apiService;

    static {
        getOkHttpClient();
    }

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null){
            synchronized (OkHttpClient.class){
                if (okHttpClient == null){
                    File file = new File(Environment.getExternalStorageDirectory(),"cahce");
                    long fileSize = 10*1024*1024;
                    okHttpClient = new OkHttpClient.Builder()
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15,TimeUnit.SECONDS)
                            .connectTimeout(15,TimeUnit.SECONDS)
                            .cache(new Cache(file,fileSize))
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    public static ApiService getApiService(String url){
        if (apiService == null){
            synchronized (OkHttpClient.class){
                apiService = createApiService(ApiService.class,url);
            }
        }
        return apiService;
    }

    private static <T>T createApiService(Class<T> tClass, String url) {
        T t = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(tClass);
        return t;
    }
}
