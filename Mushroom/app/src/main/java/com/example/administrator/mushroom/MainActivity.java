package com.example.administrator.mushroom;


import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import com.example.administrator.mushroom.adapter.MyViewPagerAdapter;
import com.example.administrator.mushroom.utils.SnackbarUtil;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private String[] mTitles;//标题栏
    private String[] mUrls;//标题相关内容的网站
    private List<Fragment> mFragments;//viewPager中要显示的Fragment集

    private MyViewPagerAdapter mMyViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化各种控件
        initViews();
        //获取数据
        initData();
        //对各种控件进行设置，适配，填充数据
        configViews();

//        Log.i("catlist_li","start");
//        GetDataFromURL g=new GetDataFromURL();
//        g.importDataIntoDB();
//        Log.i("catlist_li","end");
    }



    private void initViews() {
        mCoordinatorLayout=(CoordinatorLayout)findViewById(R.id.id_coordinatorlayout);
        mAppBarLayout=(AppBarLayout)findViewById(R.id.id_appbarlayout);
        mToolbar=(Toolbar)findViewById(R.id.id_toolbar);
        mTabLayout=(TabLayout)findViewById(R.id.id_tablayout);
        mViewPager=(ViewPager)findViewById(R.id.id_viewpager);
    }


    private void initData() {
        //在values中建string-array（名为tab_titles），里面再建item;用于TabLayout标题栏
        mTitles=getResources().getStringArray(R.array.tab_titles);
        mUrls=getResources().getStringArray(R.array.tab_urls);
        mFragments=new ArrayList<>();
        for (int i=0;i<mTitles.length;i++){
            Bundle mBundle=new Bundle();
            mBundle.putString("url",mUrls[i]);
            mBundle.putString("title",mTitles[i]);
            mBundle.putInt("flag",i);
            MyFragment myFragment=new MyFragment();
            myFragment.setArguments(mBundle);
            mFragments.add(i,myFragment);
        }
    }

    private void configViews() {
        setSupportActionBar(mToolbar);

        mMyViewPagerAdapter=new MyViewPagerAdapter(getSupportFragmentManager(),mTitles,mFragments);
        mViewPager.setAdapter(mMyViewPagerAdapter);


        mViewPager.setOffscreenPageLimit(5);
        mViewPager.addOnPageChangeListener(this);

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        mTabLayout.setupWithViewPager(mViewPager);


//        ConnectivityManager con= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo nei=con.getActiveNetworkInfo();
//        Log.i("catlist_li",nei.toString()+"dsdsdsdsds"+nei.isConnected());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
//                SnackbarUtil.show(mViewPager,"sdsdlksdlskld");
                Intent intent=new Intent(this,AboutActivity.class);
                startActivity(intent);
        }
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
