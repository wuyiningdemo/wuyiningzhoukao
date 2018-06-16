package com.example.wuyining20180616.tuyi.tianjiagouwuche;

import android.util.Log;

import com.example.wuyining20180616.tuyi.api.Api;
import com.example.wuyining20180616.tuyi.api.RetrofitHelper;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class TianjiaModel {
    private TianjiaZiP sousuoZiP;

    public TianjiaModel(TianjiaZiP sousuoZiP) {
        this.sousuoZiP = sousuoZiP;
    }

    // https://www.zhaoapi.cn/product/addCart?uid=15157&pid=80&token=C7C24A80854F96DB50620EB5507F0878
    public void getData(String url, String key) {
        Map<String, String> parmars = new HashMap<>();
        parmars.put("uid", "15157");
        parmars.put("pid", key);
        parmars.put("token", "C7C24A80854F96DB50620EB5507F0878");
        parmars.put("source", "android");

        RetrofitHelper.getApiService(Api.BASE_API).doGet(url, parmars)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("TianjiaModel3", "失败");
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        sousuoZiP.onSuccess(responseBody);
                        Log.d("TianjiaModel", "cg");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TianjiaModel2", "失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TianjiaModel1", "失败");
                    }
                });
    }
}

