package com.example.orderqueue;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static String ID ="id";
    public static String NAME ="phone";
    public static String PASS_WORD = "pword";
    public static String NUMBER = "unum";
    public static String LEVEL = "lv";
    public static String TABLE_NAME ="user";

    public MySQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "create table " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + NAME + " char,"
                + PASS_WORD + " char,"
                + NUMBER + " integer,"
                + LEVEL + " integer"
                + ")";
        Log.d("Tag","创建成功");
        db.execSQL(sql);
        String sql2="INSERT OR REPLACE INTO user (id, phone, pword, unum, lv) VALUES (1, 10086, 123, 0, 0);";
        db.execSQL(sql2);
        Log.d("Tag","管理员创建成功");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        Log.d("Tag","更新成功");
    }
}
