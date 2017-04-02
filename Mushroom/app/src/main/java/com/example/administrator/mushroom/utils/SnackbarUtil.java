package com.example.administrator.mushroom.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Administrator on 2017/3/18.
 */

public class SnackbarUtil {
    private static Snackbar mSnackbar;

    public static void show(View view, String msg){
        //获取snackbar,这个view是snackbar要在其上显示的view
        mSnackbar=Snackbar.make(view,msg,Snackbar.LENGTH_LONG);

        mSnackbar.show();//显示
        //设置行为
        mSnackbar.setAction("close", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbar.dismiss();//释放，和show()一样，都是通过SnackbarManager来操作的
            }
        });
    }
}

