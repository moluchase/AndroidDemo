package com.example.administrator.mushroom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mushroom.bean.DataBean;
import com.example.administrator.mushroom.R;
import com.example.administrator.mushroom.net.GetDataFromURL;
import com.example.administrator.mushroom.sql.SqliteOperate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {

    public Context mContext;
    public List<DataBean> mDatas;
    public LayoutInflater mLayoutInflater;

    public OnItemClickListener mOnItemClickListener;

    //需要传入context，即activity
    public MyRecyclerViewAdapter(Context mContext,String url,String title){
        this.mContext=mContext;
        mLayoutInflater=LayoutInflater.from(mContext);
        mDatas=new ArrayList<>();
        //收藏板块不参与，再判断数据库是否为空，然后才决定是否导入数据
        if(!title.equals("收藏板块")&&SqliteOperate.judgeTableNull(mContext,title)==0){
            if(title.equals("湖北专版")){
                GetDataFromURL.importDataIntoDB2(url,title,mContext);
            }else if(title.equals("蘑菇食谱")||title.equals("菇菌营养")){
                GetDataFromURL.importDataIntoDB3(url,title,mContext);
            }else{
                GetDataFromURL.importDataIntoDB(url,title,mContext);
            }
        }
        mDatas=SqliteOperate.readData(mContext,title);
        //传入数据:参数为URL
       // mDatas=GetDataFromURL.importDataIntoDB(url,title,mContext);
//        for (int i=0;i<6;i++){
//            DataBean dataBean=new DataBean(1,"3232323","45454534","343435463434","34343435454353453454535","dsdsdsfvsdfvs");
//            mDatas.add(dataBean);
//        }

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener=listener;
    }

    //创建ViewHolder,返回后传入到onBindViewHolder中
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView=mLayoutInflater.inflate(R.layout.item_main,parent,false);
        MyRecyclerViewHolder mViewHolder=new MyRecyclerViewHolder(mView);
        return mViewHolder;
    }

    //对ViewHolder绑定,监听，加载数据
    @Override
    public void onBindViewHolder(final MyRecyclerViewHolder holder, final int position) {
        //不为空表示装载的fragment
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mOnItemClickListener.onItemClick(holder.itemView,mDatas.get(position).getHref(),mDatas.get(position).getTitle());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(mContext,holder.itemView,mDatas.get(position).getId(),mDatas.get(position).getType(),position);
                    return true;
                }
            });
        }
        //加载数据
        holder.mEssayTitle.setText(mDatas.get(position).getTitle());
        holder.mEssayIntroduction.setText(mDatas.get(position).getIntroduction());
        holder.mEssayDate.setText(" "+mDatas.get(position).getDate());

    }



    @Override
    public int getItemCount() {
        return mDatas.size();
    }



    public interface OnItemClickListener{
        void onItemClick(View view,String href,String title);
        void onItemLongClick(Context context,View view,int id,String type,int position);
    }
}
