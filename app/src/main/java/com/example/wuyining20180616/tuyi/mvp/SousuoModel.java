package com.example.wuyining20180616.tuyi.mvp;



import com.example.wuyining20180616.tuyi.api.Api;
import com.example.wuyining20180616.tuyi.api.RetrofitHelper;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class SousuoModel {
    private SousuoZiP sousuoZiP;

    public SousuoModel(SousuoZiP sousuoZiP) {
        this.sousuoZiP = sousuoZiP;
    }

    public void getData(String url,String key, int page) {
        Map<String, String> parmars = new HashMap<>();
        parmars.put("keywords", key);
        parmars.put("page", page+"");
        RetrofitHelper.getApiService(Api.BASE_API).doGet(url, parmars)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        sousuoZiP.onSuccess(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

