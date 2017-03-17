package com.example.administrator.androidnewwidgetsdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/3/15.
 */

public class RoundedImageView extends ImageView {
    public RoundedImageView(Context context) {
        super(context);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable=getDrawable();//返回view的drawable，如果没有被分配则返回空

        if(drawable==null){
            return;
        }

        if(getWidth()==0||getHeight()==0){
            return;
        }

        Bitmap b=((BitmapDrawable)drawable).getBitmap();//返回Drawable要呈现的位图
        Bitmap bitmap=b.copy(Bitmap.Config.ARGB_8888,true);//根据位图的尺寸创建新的位图，返回新的位图

        int w=getWidth();
        Bitmap roundBitmap=getCroppedBitmap(bitmap,w);
        canvas.drawBitmap(roundBitmap,0,0,null);//用指定的画笔在指定的left，top的位置画bitmap

    }

    private static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;

        if(bmp.getWidth()!=radius||bmp.getHeight()!=radius){
            sbmp=Bitmap.createScaledBitmap(bmp,radius,radius,false);
        }else{
            sbmp=bmp;
        }

        //config有4个标准，用来指定创建图片的质量，ARGB_8888指定图片质量最高
        Bitmap output=Bitmap.createBitmap(sbmp.getWidth(),sbmp.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(output);//添加要要在其上绘制的位图，即创建一个canvas然后绑定bitmap

        final Paint paint=new Paint();
        final Rect rect=new Rect(0,0,sbmp.getWidth(),sbmp.getHeight());//矩形

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        canvas.drawARGB(0,0,0,0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(sbmp.getWidth()/2+0.7f,sbmp.getHeight()/2+0.7f,sbmp.getWidth()/2+0.1f,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //将sbmp绘制在output上
        canvas.drawBitmap(sbmp,rect,rect,paint);//参数：位图，要裁剪的区域，图片的显示位置；用原矩形填充目标矩形

        return output;
    }
}
