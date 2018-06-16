package com.example.wuyining20180616.tuyi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.wuyining20180616.R;
import com.example.wuyining20180616.tuyi.api.Api;
import com.example.wuyining20180616.tuyi.mvp.SousuoPresenter;
import com.example.wuyining20180616.tuyi.mvp.SousuoView;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import okhttp3.ResponseBody;

public class SousuoActivity extends AppCompatActivity implements SousuoView {
    int page = 1;
    @BindView(R.id.recy)
    RecyclerView mRecy;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private SousuoPresenter sousuoPresenter;
    private String keywords;
    private List<SousuoBean.DataBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sousuo);
        ButterKnife.bind(this);
        keywords = getIntent().getStringExtra("keywords");
        Log.d("SousuoActivity", keywords);

        sousuoPresenter = new SousuoPresenter();
        sousuoPresenter.attachView(this);
        sousuoPresenter.getData(Api.DUANZI_API, keywords, page);

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page = page + 1;
                Log.d("SousuoActivity", "page:" + page);
                sousuoPresenter.getData(Api.DUANZI_API, keywords, page);
                refreshLayout.finishLoadMore(2000);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                sousuoPresenter.getData(Api.DUANZI_API, keywords, page);
                refreshLayout.finishRefresh(2000);
            }
        });
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {

        try {
            String string = responseBody.string();
            SousuoBean sousuoBean = new Gson().fromJson(string, SousuoBean.class);
            data = sousuoBean.getData();
            Log.d("SousuoActivity", "data:" + data);
            MyAdapter myAdapter = new MyAdapter(this, data);
            mRecy.setAdapter(myAdapter);
            mRecy.setLayoutManager(new LinearLayoutManager(this));
            myAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    yunxing(position);
                }

                @Override
                public void onLongClick(int position) {
                    yunxing(position);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void yunxing(int position) {
        String images = data.get(position).getImages();
        String subhead = data.get(position).getSubhead();
        String title = data.get(position).getTitle();
        int pid = data.get(position).getPid();
        double pscid = data.get(position).getPscid();
        double price = data.get(position).getPrice();
        Intent intent = new Intent(SousuoActivity.this, SpxqActivity.class);
        intent.putExtra("images", images);
        intent.putExtra("subhead", subhead);
        intent.putExtra("title", title);
        intent.putExtra("pid", pid+"");
        intent.putExtra("pscid", pscid+"");
        intent.putExtra("price", price+"");
        startActivity(intent);

    }
    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sousuoPresenter == null) {
            sousuoPresenter.dettachView();
        }
    }
}
