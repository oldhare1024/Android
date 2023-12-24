package com.example.orderqueue;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends Activity {
    private MySQLiteHelper mySql;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initSQL();
        TextView title = findViewById(R.id.title);
        TextView uid = findViewById(R.id.uid);
        TextView uphone = findViewById(R.id.uphone);
        TextView upwd = findViewById(R.id.upwd);
        TextView unum = findViewById(R.id.unum);
        TextView ulv = findViewById(R.id.ulv);
        Button btnBack = findViewById(R.id.btn_back);
        Intent intent=getIntent();
        String phone=intent.getStringExtra("phone");
        //获取用户信息
        String sql="select * from user where phone = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{phone});
        cursor.moveToNext();
        String[] resultArray = new String[cursor.getColumnCount()];
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            resultArray[i] = cursor.getString(i);
        }
        System.out.println(resultArray[0]);
        // 设置文本内容
        title.setText("用户信息表");
        uid.setText("用户ID: "+resultArray[0]);
        uphone.setText("用户手机号： "+resultArray[1]);
        upwd.setText("用户密码： "+resultArray[2]);
        unum.setText("用户号码： "+resultArray[3]);
        ulv.setText("用户等级： "+resultArray[4]);

        // 设置按钮点击事件
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 关闭当前Activity
            }
        });
    }
    private void initSQL(){
        mySql=new MySQLiteHelper(this,"user.db",null,1);
        db=mySql.getWritableDatabase();
    }
}

