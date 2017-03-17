package com.example.administrator.androidnewwidgetsdemo;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.androidnewwidgetsdemo.adapter.MyViewPagerAdapter;
import com.example.administrator.androidnewwidgetsdemo.utils.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private DrawerLayout mDrawerLayout;
    private CoordinatorLayout mCoordinatorLayout;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton mFloatingActionButton;
    private NavigationView mNavigationView;

    private String[] mTitles;
    private List<Fragment> mFragments;
    private MyViewPagerAdapter mViewPagerAdapter;

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

    }



    private void initData() {
        //在values中建string-array（名为tab_titles），里面再建item
        mTitles=getResources().getStringArray(R.array.tab_titles);
        Log.i("array",":"+mTitles[0]+","+mTitles[1]);
        mFragments=new ArrayList<>();
        for(int i=0;i<mTitles.length;i++){
            Bundle mBundle=new Bundle();
            mBundle.putInt("flag",i);
            //关于为什么要用Bundle传参，而不是通过构造函数传递参数，
            // 有博客分析到在横竖屏等操作时会导致activity重建，而通过无参的构造函数构建fragment
            MyFragment mFragment=new MyFragment();
            mFragment.setArguments(mBundle);//设置
            mFragments.add(i,mFragment);

        }
    }

    private void configViews(){

        setSupportActionBar(mToolbar);//添加Toolbar

        /*
         * ActionBarDrawerToggle参数
         * 显示抽屉的Activity对象
         * DrawerLayout对象
         * 一个用来指示抽屉的drawable资源
         * 一个用来描述打开抽屉的文本
         * 一个用来描述关闭抽屉的文本
         */
        //添加左上角点击跳出抽屉,关联在toolbar上，
        ActionBarDrawerToggle mActionBarDrawerToggle=new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.open,R.string.close);
        mActionBarDrawerToggle.syncState();//将抽屉指示器的状态和当前的DrawerLayout同步，便于判断抽屉是否跳出与关闭
      //  mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);


         // 可以在xml中使用app:headerLayout="@layout/header_nav"
         // app:menu="@menu/menu_nav"

        mNavigationView.inflateHeaderView(R.layout.header_nav);//填充NavigationView的顶部区域
        mNavigationView.inflateMenu(R.menu.menu_nav);//填充Menu菜单

        //设置NavigationView中menu的item被选中后执行的操作
        onNavgationViewMenuItemSelected(mNavigationView);

        //控制fragment的Manager，mTitles为pageTitle，这个和mFragments对应，而当TabLayout与ViewPager关联时，mTitles会自动添加到TabLayout的显示上
        mViewPagerAdapter=new MyViewPagerAdapter(getSupportFragmentManager(),mTitles,mFragments);
        mViewPager.setAdapter(mViewPagerAdapter);

        mViewPager.setOffscreenPageLimit(5);//设置Viewpager最大缓存的页面个数
        mViewPager.addOnPageChangeListener(this);//给viewpager添加页面动态监听器

        /*
        TabMode:布局中Tab的行为模式（behavior mode），有两种值：MODE_FIXED 和 MODE_SCROLLABLE。
          MODE_FIXED:固定tabs，并同时显示所有的tabs。
          MODE_SCROLLABLE：可滚动tabs，显示一部分tabs，在这个模式下能包含长标签和大量的tabs，最好用于用户不需要直接比较tabs
         顺便给出GabGravity的
         TabGravity:放置Tab的Gravity,有GRAVITY_CENTER 和 GRAVITY_FILL两种效果。
         顾名思义，一个是居中，另一个是尽可能的填充（注意，GRAVITY_FILL需要和MODE_FIXED一起使用才有效果）
         */
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        //TabLayout位于Toolbar下面，相当于ViewPager的标题，随着ViewPager的左右滑动来关联
        mTabLayout.setupWithViewPager(mViewPager);//tablayout和viewpager关联
        //mTabLayout.setTabsFromPagerAdapter(mViewPagerAdapter);

        mFloatingActionButton.setOnClickListener(this);

    }

    private void onNavgationViewMenuItemSelected(NavigationView mNav) {
        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                String msgString="";

                switch(item.getItemId()){
                    case R.id.nav_menu_home:
                        msgString=(String)item.getTitle();
                        break;
                    case R.id.nav_menu_categories:
                        msgString=(String)item.getTitle();
                        break;
                    case R.id.nav_menu_feedback:
                        msgString=(String)item.getTitle();
                        break;
                    case R.id.nav_menu_setting:
                        msgString=(String)item.getTitle();
                        break;
                }
                //Menu item点击后选中，并关闭Drawerlayout
                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                SnackbarUtil.show(mViewPager,msgString,0);

                return true;
            }
        });
    }

    private void initViews() {
        mDrawerLayout=(DrawerLayout)findViewById(R.id.id_drawerlayout);
        mCoordinatorLayout=(CoordinatorLayout)findViewById(R.id.id_coordinatorlayout);
        mAppBarLayout=(AppBarLayout)findViewById(R.id.id_appbarlayout);
        mToolbar=(Toolbar)findViewById(R.id.id_toolbar);
        mTabLayout=(TabLayout)findViewById(R.id.id_tablayout);
        mViewPager=(ViewPager)findViewById(R.id.id_viewpager);
        mFloatingActionButton=(FloatingActionButton)findViewById(R.id.id_floatingactionbutton);
        mNavigationView=(NavigationView)findViewById(R.id.id_navigationview);
    }

    /**
     * 下面两个函数是针对菜单的,會出現在Toolbar上，
     */
    //加载选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my,menu);
        return true;
    }

    //选项菜单选项监控
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //对应的是mFloatingActionButton.setOnClickListener
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_floatingactionbutton:
                SnackbarUtil.show(v,getString(R.string.plusone),0);
        }
    }

    //下面的三个对应的是mViewPager.addOnPageChangeListener
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
