package com.example.administrator.androidnewwidgetsdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.androidnewwidgetsdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class MyStaggeredViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {



    public OnItemClickListener mOnItemClickListener;

    public Context mContext;
    public List<String> mDatas;
    public List<Integer> mHeights;
    public LayoutInflater mLayoutInflater;

    public MyStaggeredViewAdapter(Context mContext){
        this.mContext=mContext;
        mLayoutInflater=LayoutInflater.from(mContext);
        mDatas=new ArrayList<>();
        mHeights=new ArrayList<>();
        for (int i=0;i<mDatas.size();i++){
            mDatas.add((char)i+"");
        }
        for (int i=0;i<mDatas.size();i++){
            mHeights.add((int)(Math.random()*300)+200);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener=listener;
    }

    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView=mLayoutInflater.inflate(R.layout.item_main,parent,false);
        MyRecyclerViewHolder mViewHolder=new MyRecyclerViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyRecyclerViewHolder holder, final int position) {
        if(mOnItemClickListener!=null){
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

        ViewGroup.LayoutParams mLayoutParams=holder.mTextView.getLayoutParams();
        mLayoutParams.height=mHeights.get(position);
        holder.mTextView.setLayoutParams(mLayoutParams);
        holder.mTextView.setText(mDatas.get(position));
    }



    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
