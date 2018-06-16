package com.example.wuyining20180616.tuer.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;



public interface ApiServicei {
    @GET
    Observable<ResponseBody> doGet(@Url String url, @QueryMap Map<String, String> map);
}
