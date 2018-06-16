package com.example.wuyining20180616.tuyi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuyining20180616.R;

import java.util.zip.Inflater;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TuYi extends AppCompatActivity {
    @BindView(R.id.edit)
    EditText editText;
    @BindView(R.id.tv_sou)
    TextView tv;
    @BindView(R.id.id_flowlayout)
    FlowLayout mFlowLayout;
    @BindView(R.id.clear)
    Button clear;
    private String[] mVals = new String[]{};
    private LayoutInflater mInflater;
    private String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tu_yi);
        mInflater = LayoutInflater.from(this);
        ButterKnife.bind(this);
        //设置默认显示
        for (int i = 0; i < mVals.length; i++) {
            tv = (TextView) mInflater.inflate(R.layout.search_label_tv, mFlowLayout, false);
            tv.setText(mVals[i]);
            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TuYi.this, "你点击了" + str, Toast.LENGTH_SHORT).show();
                }
            });
            mFlowLayout.addView(tv);//添加到父View
        }
    }

    @OnClick({R.id.tv_sou, R.id.id_flowlayout, R.id.clear})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.tv_sou:
                s = editText.getText().toString();

                tv = (TextView) mInflater.inflate(
                        R.layout.search_label_tv, mFlowLayout, false);
                tv.setText(s);
                final String str = tv.getText().toString();
                //点击事件
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TuYi.this, "00你点击了" + str, Toast.LENGTH_SHORT).show();
                    }
                });
                mFlowLayout.addView(tv);//添加到父View
                Intent intent = new Intent(TuYi.this, SousuoActivity.class);
                intent.putExtra("keywords",s);
                startActivity(intent);
                break;
            case R.id.id_flowlayout:
                break;
            case R.id.clear:
                mFlowLayout.removeAllViews();
                break;
        }
    }
}
