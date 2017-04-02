package com.example.administrator.mushroom.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by Administrator on 2017/3/18.
 */

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles;
    private List<Fragment> mFragments;

    public MyViewPagerAdapter(FragmentManager fm, String[] mTitles,List<Fragment> mFragments) {
        super(fm);
        this.mTitles=mTitles;
        this.mFragments=mFragments;
//        Log.i("pageradapter","construction");
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
