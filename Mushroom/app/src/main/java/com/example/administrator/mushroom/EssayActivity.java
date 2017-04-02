package com.example.administrator.mushroom;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.administrator.mushroom.net.GetDataFromURL;


import java.io.InputStream;
import java.net.URL;

/**
 * Created by Administrator on 2017/3/20.
 */

public class EssayActivity extends AppCompatActivity {

    private TextView mTextView;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:

                    mTextView.setText((Spanned)(msg.obj));
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.essay_layout);

        mToolbar=(Toolbar)findViewById(R.id.essay_toolbar);
        //设置toolbar
        setSupportActionBar(mToolbar);

        //设置标题
        mCollapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.essay_collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle(getIntent().getStringExtra("title"));

        mTextView=(TextView)findViewById(R.id.essay_scrollview_text);



        mTextView.setMovementMethod(LinkMovementMethod.getInstance());//
        //int width=getWindowManager().getDefaultDisplay().getWidth();
        //解决在onCreate()中无法获得TextView的长宽的问题，见http://blog.csdn.net/johnny901114/article/details/7839512
        ViewTreeObserver vto=mTextView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //getIntent()是在fragment中启动的Intent传递的参数
                getTextEssay(getIntent().getStringExtra("href"),mTextView.getWidth());
            }
        });

    }

    public void getTextEssay(final String href, final int x){
        Thread thread=
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                message.what=1;
                String html=GetDataFromURL.importEssayfromDB(href);

                    //这个是将含标签的字符串转化为文本，会出现空格，空行等实现，api24弃用
                    Spanned spanned=Html.fromHtml(html,new Html.ImageGetter(){
                        @Override
                        public Drawable getDrawable(String source) {
                            InputStream is=null;
                            try{
                                is=(InputStream)new URL(source).getContent();
                                Drawable d=Drawable.createFromStream(is,"src");
                                d.setBounds(0,0,x,x);
                                is.close();
                                return d;
                            }catch (Exception e){
                                return null;
                            }
                        }
                    },null);
                    message.obj=spanned;
                    mHandler.sendMessage(message);

            }
        });
        thread.start();
        //关于Activity的生命周期的文章 http://www.jianshu.com/p/606321b9a30e
        //下面的目的是当文章加载完成后才执行onCreate后面的语句（onStarted（）开始，布局会在前台显示），为的是当界面显示时有数据，而不是空白
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
