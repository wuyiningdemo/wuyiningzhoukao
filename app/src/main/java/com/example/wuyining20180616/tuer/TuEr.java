package com.example.wuyining20180616.tuer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.wuyining20180616.R;
import com.example.wuyining20180616.tuer.api.Apii;
import com.example.wuyining20180616.tuer.mvp.SpxqPresenter;
import com.example.wuyining20180616.tuer.mvp.SpxqView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;

import okhttp3.ResponseBody;

public class TuEr extends AppCompatActivity implements SpxqView,MyExpandAdapter.ModifyGoodsItemNumberListener,MyExpandAdapter.CheckGroupItemListener{
    @BindView(R.id.btnBack)
    TextView mBtnBack;
    @BindView(R.id.btnEditor)
    TextView mBtnEditor;
    @BindView(R.id.expandList)
    ExpandableListView mExpandList;
    @BindView(R.id.btnCheckAll)
    CheckBox mBtnCheckAll;
    @BindView(R.id.tvTotalPrice)
    TextView mTvTotalPrice;
    @BindView(R.id.btnAmount)
    TextView mBtnAmount;
    //默认是false
    private boolean flag;
    //购买商品的总数量
    private int totalNum = 0;
    //购买商品的总价
    private double totalPrice = 0.00;
    private List<ShoppCarBean.DataBean> list;
    private MyExpandAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tu_er);
        initView();
        getSupportActionBar().hide();
        mExpandList.setGroupIndicator(null);
        SpxqPresenter spxqPresenter = new SpxqPresenter();
        spxqPresenter.attachView(this);
        spxqPresenter.getData(Apii.DUANZI_API, "15157");
        adapter = new MyExpandAdapter(this);
        mExpandList.setAdapter(adapter);
        adapter.setModifyGoodsItemNumberListener(this);
        //设置商家以及商品是否被选中的监听
        adapter.setCheckGroupItemListener(this);
        mBtnCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isChoosedAll(((CheckBox) view).isChecked());
                //计算商品总价
                statisticsPrice();
            }
        });
        mBtnEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flag) {//编辑 -> 完成\
                    flag = true;
                    mBtnEditor.setText("完成");
                    adapter.showDeleteButton(flag);
                } else {
                    flag = false;
                    mBtnEditor.setText("编辑");
                    adapter.showDeleteButton(flag);
                }
            }
        });
    }
    private void initView() {
        mBtnBack = (TextView) findViewById(R.id.btnBack);
        mBtnEditor = (TextView) findViewById(R.id.btnEditor);
        mExpandList = (ExpandableListView) findViewById(R.id.expandList);
        mBtnCheckAll = (CheckBox) findViewById(R.id.btnCheckAll);
        mTvTotalPrice = (TextView) findViewById(R.id.tvTotalPrice);
        mBtnAmount = (TextView) findViewById(R.id.btnAmount);
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            ShoppCarBean shoppCarBean = new Gson().fromJson(string, ShoppCarBean.class);
            List<ShoppCarBean.DataBean> data = shoppCarBean.getData();

            this.list = data;
            adapter.setList(list);
            defaultExpand();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void defaultExpand() {
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            mExpandList.expandGroup(i);
        }
    }
    @Override
    public void doIncrease(int groupPosition, int childPosition, View view) {
        ShoppCarBean.DataBean.ListBean listBean = list.get(groupPosition).getList().get(childPosition);
        //取出当前的商品数量
        int currentNum = listBean.getNum();
        //商品++
        currentNum++;
        //将商品数量设置javabean上
        listBean.setNum(currentNum);

        //刷新适配器
        adapter.notifyDataSetChanged();


        //计算商品价格
        statisticsPrice();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View view) {
        ShoppCarBean.DataBean.ListBean listBean = list.get(groupPosition).getList().get(childPosition);
        //取出当前的商品数量
        int currentNum = listBean.getNum();
        //直接结束这个方法
        if (currentNum == 1) {
            return;
        }

        currentNum--;
        listBean.setNum(currentNum);
        //刷新适配器
        adapter.notifyDataSetChanged();

        //计算商品价格
        statisticsPrice();

    }

    @Override
    public void checkGroupItem(int groupPosition, boolean isChecked) {
        ShoppCarBean.DataBean dataBean = list.get(groupPosition);
        dataBean.setGroupChoosed(isChecked);

        //遍历商家里面的商品，将其置为选中状态
        for (ShoppCarBean.DataBean.ListBean listBean : dataBean.getList()) {
            listBean.setChildChoosed(isChecked);
        }

        //底部结算那个checkbox状态(全选)
        if (isCheckAll()) {
            mBtnCheckAll.setChecked(true);
        } else {
            mBtnCheckAll.setChecked(false);
        }

        //刷新适配器
        adapter.notifyDataSetChanged();

        //计算价格
        statisticsPrice();
    }

    @Override
    public void checkChildItem(int groupPosition, int childPosition, boolean isChecked) {
        ShoppCarBean.DataBean dataBean = list.get(groupPosition);//商家那一层
        List<ShoppCarBean.DataBean.ListBean> listBeans = dataBean.getList();
        ShoppCarBean.DataBean.ListBean listBean = listBeans.get(childPosition);

        //你点击商家的商品条目将其选中状态记录
        listBean.setChildChoosed(isChecked);

        //检测商家里面的每一个商品是否被选中，如果被选中，返回boolean
        boolean result = isGoodsCheckAll(groupPosition);
        if (result) {
            dataBean.setGroupChoosed(result);
        } else {
            dataBean.setGroupChoosed(result);
        }

        //底部结算那个checkbox状态(全选)
        if (isCheckAll()) {
            mBtnCheckAll.setChecked(true);
        } else {
            mBtnCheckAll.setChecked(false);
        }


        //刷新适配器
        adapter.notifyDataSetChanged();

        //计算总价
        statisticsPrice();

    }
    //购物车商品是否全部选中
    private boolean isCheckAll() {

        for (ShoppCarBean.DataBean dataBean : list) {
            if (!dataBean.isGroupChoosed()) {
                return false;
            }
        }
        return true;
    }
    //全选与反选
    private void isChoosedAll(boolean isChecked) {

        for (ShoppCarBean.DataBean dataBean : list) {
            dataBean.setGroupChoosed(isChecked);
            for (ShoppCarBean.DataBean.ListBean listBean : dataBean.getList()) {
                listBean.setChildChoosed(isChecked);
            }
        }
        //刷新适配器
        adapter.notifyDataSetChanged();
    }
    /**
     * 检测某个商家的商品是否都选中，如果都选中的话，商家CheckBox应该是选中状态
     */
    private boolean isGoodsCheckAll(int groupPosition) {
        List<ShoppCarBean.DataBean.ListBean> listBeans = this.list.get(groupPosition).getList();
        //遍历某一个商家的每个商品是否被选中
        for (ShoppCarBean.DataBean.ListBean listBean : listBeans) {
            if (listBean.isChildChoosed()) {//是选中状态
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
    private void statisticsPrice() {
        //初始化值
        totalNum = 0;
        totalPrice = 0.00;
        for (ShoppCarBean.DataBean dataBean : list) {

            for (ShoppCarBean.DataBean.ListBean listBean : dataBean.getList()) {
                if (listBean.isChildChoosed()) {
                    totalNum++;
                    System.out.println("number : " + totalNum);
                    totalPrice += listBean.getNum() * listBean.getPrice();
                }
            }
        }
        //设置文本信息 合计、结算的商品数量
        mTvTotalPrice.setText("合计:￥" + totalPrice);
        mBtnAmount.setText("结算(" + totalNum + ")");
    }
}
