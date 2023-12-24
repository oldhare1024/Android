package com.example.orderqueue;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class RegistActivity extends Activity {
    private MySQLiteHelper mySql;
    private SQLiteDatabase db;
    // 声明控件变量
    private EditText TextUname;
    private EditText TextPword;
    private Button buttonRegister;
    private Button buttonBack;
    private String UName;
    private String UPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist); // 设置布局文件
        initSQL();
        // 初始化控件变量
        TextUname = findViewById(R.id.username);
        TextPword = findViewById(R.id.password);
        buttonRegister = findViewById(R.id.btn_regist);
        buttonBack = findViewById(R.id.btn_back);

        // 为注册按钮添加点击事件监听器
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 注册操作，获取用户输入的用户名和密码
                UName=TextUname.getText().toString();
                UPwd=TextPword.getText().toString();
                if(UName.equals("110")||UName.equals("119")||UName.equals("120")){
                    Toast.makeText(RegistActivity.this, "账号不能为紧急电话", Toast.LENGTH_SHORT).show();
                }else if(UName.equals("10086")){
                    Toast.makeText(RegistActivity.this, "账号已注册", Toast.LENGTH_SHORT).show();
                }else if(!(TextUtils.isEmpty(UName)||TextUtils.isEmpty(UPwd))){
                    // TODO: 将用户名和密码传输到数据库
                    insert(UName,UPwd,1);
                    Toast.makeText(RegistActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    // TODO: 跳转到主界面
                    Intent intent = new Intent();
                    intent.setClass(RegistActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(RegistActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
            private void insert(String uname,String upwd,Integer level){
                ContentValues values=new ContentValues();
                values.put(MySQLiteHelper.NAME,uname);
                values.put(MySQLiteHelper.PASS_WORD,upwd);
                values.put(MySQLiteHelper.LEVEL,level);
                db.insert(MySQLiteHelper.TABLE_NAME,null,values);
            }
        });

        // 为返回按钮添加点击事件监听器
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 返回操作，返回到登录页面
                Intent intent = new Intent();
                intent.setClass(RegistActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initSQL(){
        mySql=new MySQLiteHelper(this,"user.db",null,1);
        db=mySql.getWritableDatabase();
    }
}
