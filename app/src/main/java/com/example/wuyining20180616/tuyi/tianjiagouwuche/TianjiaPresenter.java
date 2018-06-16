package com.example.wuyining20180616.tuyi.tianjiagouwuche;

import okhttp3.ResponseBody;


public class TianjiaPresenter implements TianjiaZiP {

    private TianjiaModel sousuoModel;
    private TianjiaView sousuoView;

    public TianjiaPresenter(){
        sousuoModel = new TianjiaModel(this);
    }

    public void attachView(TianjiaView iDuanZiView){
        this.sousuoView = iDuanZiView;
    }

    public void dettachView(){
        if (sousuoView != null){
            sousuoView = null;
        }
    }

    public void getData(String url,String key){
        sousuoModel.getData(url,key);
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        sousuoView.onSuccess(responseBody);
    }
}