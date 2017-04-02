package com.example.administrator.mushroom.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/3/19.
 * 参考：
 * http://blog.csdn.net/ahuier/article/details/10417777
 * https://developer.android.com/guide/topics/data/data-storage.html
 * http://www.cnblogs.com/ywnwa/p/4566042.html
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String DATA_DISPLAY="CREATE TABLE mushroom_display_data(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "href VARCHAR(50)," +
            "title VARCHAR(100)," +
            "introduction VARCHAR(600)," +
            "type VARCHAR(16)," +
            "date VARCHAR(12))";






    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATA_DISPLAY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //
    }
}
