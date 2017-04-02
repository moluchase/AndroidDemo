package com.example.administrator.mushroom.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.mushroom.R;

/**
 * Created by Administrator on 2017/3/17.
 */

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView mEssayTitle,mEssayIntroduction,mEssayDate;

    public MyRecyclerViewHolder(View itemView) {
        super(itemView);
        mEssayTitle=(TextView)itemView.findViewById(R.id.essay_title);
        mEssayIntroduction=(TextView)itemView.findViewById(R.id.essay_introduction);
        mEssayDate=(TextView)itemView.findViewById(R.id.essay_date);
    }
}
