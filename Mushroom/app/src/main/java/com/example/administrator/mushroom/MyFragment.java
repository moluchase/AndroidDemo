package com.example.administrator.mushroom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mushroom.adapter.MyRecyclerViewAdapter;
import com.example.administrator.mushroom.bean.DataBean;
import com.example.administrator.mushroom.net.GetDataFromURL;
import com.example.administrator.mushroom.sql.SqliteOperate;
import com.example.administrator.mushroom.utils.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 * 参考
 * SwipeRefreshLayout ：https://github.com/hanks-zyh/SwipeRefreshLayout
 */

public class MyFragment extends Fragment implements MyRecyclerViewAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyRecyclerViewAdapter mMyRecyclerViewAdapter;

    private List<DataBean> mDataforRefresh;



    //不要写构造函数，要通过Bundle传参数（很重要）



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.frag_main,container,false);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefreshLayout=(SwipeRefreshLayout)mView.findViewById(R.id.id_swiperefreshlayout);
        mRecyclerView=(RecyclerView)mView.findViewById(R.id.id_recyclerview);

        //这是SwipeRefreshLayout中的RecyclerView部分

        mLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        mMyRecyclerViewAdapter=new MyRecyclerViewAdapter(getActivity(),(String)getArguments().get("url"),(String)getArguments().get("title"));
        mMyRecyclerViewAdapter.setOnItemClickListener(this);//当该界面可见时，实现监听
        mRecyclerView.setAdapter(mMyRecyclerViewAdapter);//设置适配器

        mRecyclerView.setLayoutManager(mLayoutManager);//设置布局管理器

        //下面这是SwipeRefreshLayout的刷新部分

        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.darker_gray,
                android.R.color.holo_blue_bright,
                android.R.color.holo_blue_dark,
                android.R.color.holo_red_light
        );

        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);

        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onItemClick(View view, String href,String title) {
        //短点击寻找网址，打开网页:如果不能联网，报告错误，不跳转；否则，跳转

            boolean isConnected=GetDataFromURL.isConnectNetwork(getActivity());
            if(!isConnected){
                SnackbarUtil.show(view,"无法访问，请检查网络是否可用！");
            }else{

                //关于颜色的设置：http://www.cnblogs.com/bluestorm/p/3644669.html
                //为解决点击事件后，item会变成灰色，知道销毁
                view.setBackgroundColor(0xfff7f1f1);

                //fragment中转到Activity
                Intent intent=new Intent(getActivity(),EssayActivity.class);
                intent.putExtra("href",href);
                intent.putExtra("title",title);
                startActivity(intent);
            }



    }

    @Override
    public void onItemLongClick(Context context,View view, int id,String type,int position) {
        //长点击，如果不为收藏板块，则修改DateBean的mType为收藏板块，否则，删除数据
        if(!getArguments().get("title").equals("收藏板块")){
            if(type.equals("收藏板块")){
                SnackbarUtil.show(view,"已经收藏过啦！");
            }else{
                SqliteOperate.updateData(context,id);
                mMyRecyclerViewAdapter.mDatas.get(position).setType("收藏板块");
                SnackbarUtil.show(view,"已收藏");//这个view是可以直接用mRecyclerView代替的
            }

        }else{
            SqliteOperate.deleteData(context,id,null);
        //    mMyRecyclerViewAdapter.mDatas.remove(position);
       //     mMyRecyclerViewAdapter.notifyDataSetChanged();//通知适配器数据改变
            SnackbarUtil.show(view,"已删除,刷新可见");
        }

    }
//刷新
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {





                //当为收藏板块时，获取数据库中的收藏板块记录进行更新
                //当为其他板块时，访问网络进行更新

                if(getArguments().get("title").equals("收藏板块")){
//                    mDataforRefresh.clear();
                    mDataforRefresh=SqliteOperate.readData(getActivity(),"收藏板块");
                    mMyRecyclerViewAdapter.mDatas=mDataforRefresh;


                }else{
                    if(!GetDataFromURL.isConnectNetwork(getActivity())){
                        SnackbarUtil.show(mRecyclerView,"无法访问，请检查网络是否可用！");

                    }else{
                        long time=System.currentTimeMillis();
                        long time2;
                        //使用SharedPreferences存储时间
                        //参考http://www.cnblogs.com/linjiqin/archive/2011/05/26/2059133.html
                        //https://developer.android.com/guide/topics/data/data-storage.html
                        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("mushroom_info",Context.MODE_PRIVATE);
                        if((time2=sharedPreferences.getLong("time"+getArguments().getInt("flag"),1))==1||time-time2>7200000) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putLong("time"+getArguments().getInt("flag"), time);
                            editor.commit();
                            SqliteOperate.deleteData(getActivity(), -1, (String) getArguments().get("title"));//在数据库中删除该板块的内容
                            // 重新获取
                            if (getArguments().get("title").equals("湖北专版")) {
                                GetDataFromURL.importDataIntoDB2((String) getArguments().get("url"), (String) getArguments().get("title"), getActivity());
                            } else if (getArguments().get("title").equals("蘑菇食谱") || getArguments().get("title").equals("菇菌营养")) {
                                GetDataFromURL.importDataIntoDB3((String) getArguments().get("url"), (String) getArguments().get("title"), getActivity());
                            } else {
                                GetDataFromURL.importDataIntoDB((String) getArguments().get("url"), (String) getArguments().get("title"), getActivity());
                            }
                            mDataforRefresh = SqliteOperate.readData(getActivity(), (String) getArguments().get("title"));
                            mMyRecyclerViewAdapter.mDatas = mDataforRefresh;
                        }

                    }

                }
                mSwipeRefreshLayout.setRefreshing(false);//见样例，让圆圈消失
                mMyRecyclerViewAdapter.notifyDataSetChanged();//数据改变,让RecyclerView变化的条件是mMyRecyclerViewAdapter中的mDatas发生改变

            }
        },3000);

       // mSwipeRefreshLayout.setRefreshing(false);//见样例，让圆圈消失
    }
}
