package com.example.administrator.mushroom.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.mushroom.bean.DataBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/19.
 * 参考 ：
 * http://blog.csdn.net/ahuier/article/details/10418147
 * http://www.hollischuang.com/archives/1308
 */

public class SqliteOperate {
    private static MySQLiteHelper helper;//数据库帮助类，放回数据库类对象
    private static SQLiteDatabase mSQLiteDatabase;//数据库类

    //标志位，检测数据库是否有记录
    public static int judgeTableNull(Context context,String title){
        String sql="SELECT * FROM mushroom_display_data WHERE type=?";
        int flag=0;
        try{
            mSQLiteDatabase=SqliteOperate.getSQLiteHelper(context).getReadableDatabase();

            //获取游标
            Cursor mCursor=mSQLiteDatabase.rawQuery(sql,new String[]{title});
            if(mCursor.getCount()==0){
                flag=0;
            }else{
                flag=1;
            }
        }catch (Exception e){
            //
        }finally {
            if(mSQLiteDatabase!=null){
                mSQLiteDatabase.close();
            }
        }
        return flag;
    }


    public static MySQLiteHelper getSQLiteHelper(Context context){
        //多线程的双重检测
        if(helper==null){
            synchronized (context){
                if(helper==null){
                    helper=new MySQLiteHelper(context,"mushroom_data.db",null,1);
                }
            }
        }

        return helper;
    }

    //增
    public static void addData(Context context, ArrayList<DataBean> dataBeans){
        String sql="INSERT INTO mushroom_display_data(href,title,introduction,type,date) values(?,?,?,?,?)";
        try{
            mSQLiteDatabase=SqliteOperate.getSQLiteHelper(context).getWritableDatabase();
            if(dataBeans.get(0).getType().equals("国内动态")){
                for (DataBean dataBean:dataBeans) {
                    mSQLiteDatabase.execSQL(sql,new Object[]{dataBean.getHref(),dataBean.getTitle(),dataBean.getIntroduction(),dataBean.getType(),dataBean.getDate()});
                }
            }else{
                for (DataBean dataBean:dataBeans) {
                    mSQLiteDatabase.execSQL(sql,new Object[]{dataBean.getHref(),dataBean.getTitle(),null,dataBean.getType(),dataBean.getDate()});
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(mSQLiteDatabase!=null){
                mSQLiteDatabase.close();
            }
        }



    }



    //删
    public static void deleteData(Context context,int id,String type){
        String sql="DELETE FROM mushroom_display_data WHERE id=? ";
        String sql2="DELETE FROM mushroom_display_data WHERE type=? ";
        try{
            mSQLiteDatabase=SqliteOperate.getSQLiteHelper(context).getWritableDatabase();
            if(id!=-1){
                mSQLiteDatabase.execSQL(sql,new Object[]{id});
            }else{
                mSQLiteDatabase.execSQL(sql2,new Object[]{type});
            }

        }catch (Exception e){
            //
        }finally {
            if(mSQLiteDatabase!=null){
                mSQLiteDatabase.close();
            }
        }
    }

    //查
    public static ArrayList<DataBean> readData(Context context,String mType){
        ArrayList<DataBean> dataBeans=new ArrayList<>();
        String sql="SELECT * FROM mushroom_display_data WHERE type=? ";
        try{
            mSQLiteDatabase=SqliteOperate.getSQLiteHelper(context).getReadableDatabase();

            //获取游标，moveToNext移动到下一条记录
            Cursor mCursor=mSQLiteDatabase.rawQuery(sql, new String[]{mType});
            while(mCursor.moveToNext()){
                DataBean dataBean=new DataBean();
                dataBean.setId(mCursor.getInt(mCursor.getColumnIndex("id")));
                dataBean.setTitle(mCursor.getString(mCursor.getColumnIndex("title")));
                dataBean.setDate(mCursor.getString(mCursor.getColumnIndex("date")));
                dataBean.setHref(mCursor.getString(mCursor.getColumnIndex("href")));
                dataBean.setType(mCursor.getString(mCursor.getColumnIndex("type")));
                if(mType.equals("国内动态")){
                    dataBean.setIntroduction(mCursor.getString(mCursor.getColumnIndex("introduction")));
                }
                dataBeans.add(dataBean);
            }
        }catch (Exception e){
            //
        }finally {
            if(mSQLiteDatabase!=null){
                mSQLiteDatabase.close();
            }
        }
        return dataBeans;
    }



    //改
    public static void updateData(Context context,int id){
        String sql="UPDATE mushroom_display_data SET type=? WHERE id=? ";
        try{
            mSQLiteDatabase=SqliteOperate.getSQLiteHelper(context).getWritableDatabase();
            mSQLiteDatabase.execSQL(sql,new Object[]{"收藏板块",id});
        }catch (Exception e){
            //
        }finally {
            if(mSQLiteDatabase!=null){
                mSQLiteDatabase.close();
            }
        }
    }



}
