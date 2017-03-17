package com.example.administrator.androidnewwidgetsdemo.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.administrator.androidnewwidgetsdemo.R;

/**
 * Created by Administrator on 2017/3/15.
 */

public class SnackbarUtil {
    private static Snackbar mSnackbar;

    public static void show(View view,String msg,int flag){
        //获取snackbar,这个view是snackbar要在其上显示的view
        if(flag==0){
            mSnackbar=Snackbar.make(view,msg,Snackbar.LENGTH_SHORT);
        }else{
            mSnackbar=Snackbar.make(view,msg,Snackbar.LENGTH_LONG);
        }
        mSnackbar.show();//显示
        //设置行为
        mSnackbar.setAction(R.string.close, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbar.dismiss();//释放，和show()一样，都是通过SnackbarManager来操作的
            }
        });
    }
}
