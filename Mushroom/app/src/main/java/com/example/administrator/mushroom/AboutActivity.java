package com.example.administrator.mushroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/3/28.
 */

public class AboutActivity extends AppCompatActivity{

    TextView mTextView;

    String about="<br><br><ul><font color='#FF0000' face='verdana'>使用技巧：</font><br>" +
            "<li>(1)下拉刷新</li><br>" +
            "<li>(2)长按保存模块，在收藏板块刷新后可见</li></ul><br>" +
            "<ui><font color='#FF0000' face='verdana'>未解决的问题：</font><br>" +
            "<li>(1)需要在确定能访问网络的情况下，使用该应用，不然会出现闪退的现象</li><br>" +
            "<li>(2)未解决刷新速度问题</li></ul>";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);
        mTextView=(TextView)findViewById(R.id.about_text);
        mTextView.setText(Html.fromHtml(about));

    }
}
