package com.example.administrator.androidnewwidgetsdemo;

import android.os.Bundle;
import android.os.Handler;
import android.sax.RootElement;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.AndroidCharacter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.androidnewwidgetsdemo.adapter.MyRecyclerViewAdapter;
import com.example.administrator.androidnewwidgetsdemo.adapter.MyStaggeredViewAdapter;
import com.example.administrator.androidnewwidgetsdemo.adapter.OnItemClickListener;
import com.example.administrator.androidnewwidgetsdemo.utils.SnackbarUtil;

/**
 * Created by Administrator on 2017/3/15.
 */

public class MyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,OnItemClickListener {
    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyRecyclerViewAdapter mRecyclerViewAdapter;
    private MyStaggeredViewAdapter mStaggeredViewAdapter;

    private static final int VERTICAL_LIST=0;
    private static final int HORIZONTAL_LIST=1;
    private static final int VERTICAL_GRID=2;
    private static final int HORIZONTAL_GRID=3;
    private static final int STAGGERED_GRID=4;

    private static final int SPAN_COUNT=2;
    private int flag=0;

    //在onCreateView中创建frag_main布局对应的显示view视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.frag_main,container,false);
        return mView;
    }

    //在其中完成基础性的工作,这个方法是在Activity中的onCreate执行后才执行的
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefreshLayout=(SwipeRefreshLayout)mView.findViewById(R.id.id_swiperefreshlayout);
        mRecyclerView=(RecyclerView)mView.findViewById(R.id.id_recyclerview);

        flag=(int)getArguments().get("flag");//getArguments()返回的是Bundle对象
        configRecyclerView();

        //刷新时，设置下拉圆圈上的颜色变化,蓝色，绿色，橙色，红色
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //设置手势下拉刷新的监听，对应的下面的onRefresh(),
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void configRecyclerView() {
        switch (flag){
            //以垂直或水平滚动列表方式显示项目
            case VERTICAL_LIST:
                //参数：显示的Activity(Fragment可以调用操控其的Activity)，显示的方式，滑动的方向（layouts from end to start），false表示从顶部往下，true表示从底部往上
                mLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                break;
            case HORIZONTAL_LIST:
                mLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
                break;
            //在网格中显示项目
            case VERTICAL_GRID:
                //第二个参数表示的是分为两列
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.VERTICAL, false);
                break;
            case HORIZONTAL_GRID:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.HORIZONTAL, false);
                break;
            //这个官网上说是在分散对齐网格中显示，实践的时候，每次刷新产生一个item，和网格显示时一样的
            case STAGGERED_GRID:
                mLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
                break;
        }

        //设置适配器
        if(flag!=STAGGERED_GRID){
            mRecyclerViewAdapter=new MyRecyclerViewAdapter(getActivity());
            mRecyclerViewAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
        }else{
            mStaggeredViewAdapter=new MyStaggeredViewAdapter(getActivity());
            mStaggeredViewAdapter.setOnItemClickListener(this);
            mRecyclerView.setAdapter(mStaggeredViewAdapter);
        }
        //设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    //onreflashlistener监听，当手势下拉的情况下才会触发
    @Override
    public void onRefresh() {

        //将信息加载到消息队列，通过looper来处理消息，即：过1s后执行Runnable()中的
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //设置是否显示进度条，这个要填false，不然这个进度条会一直在，下次便不能手动刷新
                mSwipeRefreshLayout.setRefreshing(false);
                int temp=(int)(Math.random()*10);
                if(flag!=STAGGERED_GRID){
                    mRecyclerViewAdapter.mDatas.add(0,"new"+temp);
                    mRecyclerViewAdapter.notifyDataSetChanged();//数据改变
                }else{
                    mStaggeredViewAdapter.mDatas.add(0,"new"+temp);
                    mStaggeredViewAdapter.mHeights.add(0,(int)(Math.random()*300)+200);
                    mStaggeredViewAdapter.notifyDataSetChanged();
                }
            }
        },1000);
    }


    @Override
    public void onItemClick(View view, int position) {
        SnackbarUtil.show(mRecyclerView,getString(R.string.item_clicked),0);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        SnackbarUtil.show(mRecyclerView,getString(R.string.item_longclicked),0);
    }
}
