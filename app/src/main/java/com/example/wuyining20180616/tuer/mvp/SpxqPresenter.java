package com.example.wuyining20180616.tuer.mvp;

import okhttp3.ResponseBody;

public class SpxqPresenter implements SpxqZiP {

    private SpxqModel spxqModel;
    private SpxqView spxqView;

    public SpxqPresenter(){
        spxqModel = new SpxqModel(this);
    }

    public void attachView(SpxqView iDuanZiView){
        this.spxqView = iDuanZiView;
    }

    public void dettachView(){
        if (spxqView != null){
            spxqView = null;
        }
    }

    public void getData(String url,String key){
        spxqModel.getData(url,key);
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        spxqView.onSuccess(responseBody);
    }
}