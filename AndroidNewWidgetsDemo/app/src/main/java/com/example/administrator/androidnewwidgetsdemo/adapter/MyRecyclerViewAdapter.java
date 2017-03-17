package com.example.administrator.androidnewwidgetsdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.administrator.androidnewwidgetsdemo.MyFragment;
import com.example.administrator.androidnewwidgetsdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {




    public OnItemClickListener mOnItemClickListener;//item监听器接口

    public Context mContext;
    public List<String> mDatas;
    public LayoutInflater mLayoutInflater;

    public MyRecyclerViewAdapter(Context mContext){
        this.mContext=mContext;
        mLayoutInflater=LayoutInflater.from(mContext);//获取实例
        mDatas=new ArrayList<>();
        for (int i='A';i<='z';i++){
            mDatas.add((char)i+"");
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener=listener;
    }


    //创建ViewHolder
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView=mLayoutInflater.inflate(R.layout.item_main,parent,false);//获取加载父布局的view
        MyRecyclerViewHolder mViewHolder=new MyRecyclerViewHolder(mView);
        return mViewHolder;
    }

    //绑定ViewHolder，给item中的控件设置点击事件和数据,注意这个final，内部类调用外部形参
    @Override
    public void onBindViewHolder(final MyRecyclerViewHolder holder, final int position) {
        if(mOnItemClickListener!=null){
            //holder为MyRecyclerViewHolder，itemView继承自ViewHolder中的final属性，即为上个函数的mView
            //view的点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.itemView,position);
                    return true;
                }
            });
        }
        holder.mTextView.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
