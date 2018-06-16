package com.example.wuyining20180616.tuer.mvp;



import com.example.wuyining20180616.tuyi.api.Api;
import com.example.wuyining20180616.tuyi.api.RetrofitHelper;

import java.util.HashMap;
import java.util.Map;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class SpxqModel {
    private SpxqZiP sousuoZiP;

    public SpxqModel(SpxqZiP sousuoZiP) {
        this.sousuoZiP = sousuoZiP;
    }

    public void getData(String url,String key) {
        Map<String, String> parmars = new HashMap<>();
        parmars.put("uid", "15157");
        parmars.put("source", "android");
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
