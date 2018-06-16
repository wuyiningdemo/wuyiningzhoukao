package com.example.wuyining20180616.tuyi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuyining20180616.R;

import com.example.wuyining20180616.tuyi.api.Api;
import com.example.wuyining20180616.tuyi.mvp.TianjiaPresenter;
import com.example.wuyining20180616.tuyi.mvp.TianjiaView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import okhttp3.ResponseBody;

public class SpxqActivity extends AppCompatActivity implements TianjiaView {

    @BindView(R.id.spxq_sim)
    SimpleDraweeView spxq_sim;
    @BindView(R.id.spxq_textView1)
    TextView spxq_textView1;
    @BindView(R.id.spxq_textView2)
    TextView spxq_textView2;
    @BindView(R.id.spxq_button)
    Button spxqButton;
    private String pid;
    private TianjiaPresenter tianjiaPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spxq);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String images = intent.getStringExtra("images");
        if (images.indexOf("|") != -1) {
            String result = images.substring(0, images.indexOf("|"));
            //加载图片  url=result
            spxq_sim.setImageURI(result);
        } else {
            //加载图片  url=iamges
            spxq_sim.setImageURI(images);
        }
        String subhead = intent.getStringExtra("subhead");
        spxq_textView1.setText(subhead);
        String title = intent.getStringExtra("title");
        spxq_textView2.setText(title);
        pid = intent.getStringExtra("pid");
        String pscid = intent.getStringExtra("pscid");
        String price = intent.getStringExtra("price");

        Log.d("SpxqActivity", pid);
        Log.d("SpxqActivity", pscid);
        Log.d("SpxqActivity", price);
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();

            Log.d("SpxqActivity___", string.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.spxq_button)
    public void onViewClicked() {
        tianjiaPresenter = new TianjiaPresenter();
        tianjiaPresenter.attachView(this);
        tianjiaPresenter.getData(Api.DUANZI_API2, pid);

    }
    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tianjiaPresenter == null) {
            tianjiaPresenter.dettachView();
        }
    }
}
