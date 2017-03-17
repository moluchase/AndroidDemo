package com.example.administrator.androidnewwidgetsdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.androidnewwidgetsdemo.R;

/**
 * Created by Administrator on 2017/3/15.
 */

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder{

    public TextView mTextView;

    public MyRecyclerViewHolder(View itemView) {
        super(itemView);
        mTextView=(TextView)itemView.findViewById(R.id.id_textview);//CardView
    }
}
