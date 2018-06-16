package com.example.wuyining20180616.tuyi.mvp;

import okhttp3.ResponseBody;


public class SousuoPresenter implements SousuoZiP {

    private SousuoModel sousuoModel;
    private SousuoView sousuoView;

    public SousuoPresenter(){
        sousuoModel = new SousuoModel(this);
    }

    public void attachView(SousuoView iDuanZiView){
        this.sousuoView = iDuanZiView;
    }

    public void dettachView(){
        if (sousuoView != null){
            sousuoView = null;
        }
    }

    public void getData(String url,String key,int page){
        sousuoModel.getData(url,key,page);
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        sousuoView.onSuccess(responseBody);
    }
}