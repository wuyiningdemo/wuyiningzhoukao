package com.example.wuyining20180616.tuyi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wuyining20180616.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;






public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private List<SousuoBean.DataBean> list;
    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public MyAdapter(Context context, List<SousuoBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder;
        View view = View.inflate(context, R.layout.sousuo_recy_item, null);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tv1.setText(list.get(position).getSubhead());
        holder.tv2.setText(list.get(position).getTitle());
        String icon = (String) list.get(position).getImages();
        if (icon.indexOf("|") != -1) {
            String result = icon.substring(0, icon.indexOf("|"));
            //加载图片  url=result
            holder.touXiang.setImageURI(result);
        } else {
            //加载图片  url=iamges
            holder.touXiang.setImageURI(icon);
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView touXiang;
        TextView tv1;
        TextView tv2;


        public ViewHolder(View itemView) {
            super(itemView);
            touXiang = itemView.findViewById(R.id.item_simple);
            tv1 = itemView.findViewById(R.id.item_textView);
            tv2 = itemView.findViewById(R.id.item_textView2);

        }
    }
}