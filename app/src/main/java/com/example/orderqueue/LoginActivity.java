package com.example.orderqueue;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    private MySQLiteHelper mySql;
    private SQLiteDatabase db;
    // 声明控件变量
    private EditText TextUname;
    private EditText TextPword;
    private Button buttonLogin;
    private Button buttonRegist;
    private String UName;
    private String UPwd;
    private Integer lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initSQL();
        // 初始化控件变量
        TextUname = findViewById(R.id.username);
        TextPword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);
        buttonRegist = findViewById(R.id.btn_regist);

        // 添加点击事件监听器到按钮上
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 登录操作，获取输入的用户名和密码
                UName=TextUname.getText().toString();
                UPwd=TextPword.getText().toString();
                String query = "SELECT lv FROM user WHERE phone = ?";
                Cursor cursor = db.rawQuery(query, new String[]{UName});
                if (cursor != null && cursor.moveToFirst()) {
                    lv = cursor.getInt(cursor.getColumnIndexOrThrow("lv"));
                    cursor.close();
                }
                // TODO: 将用户名和密码与数据库中的账号密码进行对比
                if(!(TextUtils.isEmpty(UName)||TextUtils.isEmpty(UPwd))){
                    if(query(UName,UPwd)){
                        Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        // TODO: 跳转到主界面
                        Intent intent = new Intent();
                        if(lv==0){
                            intent.setClass(LoginActivity.this,AdminActivity.class);
                        }else {
                            intent.setClass(LoginActivity.this,QueueActivity.class);
                        }
                        // TODO: 传输用户信息
                        Bundle bundle = new Bundle();
                        bundle.putString("username", UName);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
            private boolean query(String uname,String upwd) {
                String[] columns = {MySQLiteHelper.PASS_WORD};
                String selection = MySQLiteHelper.NAME + "=?";
                String[] selectionArgs = {uname};
                Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    String password = cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.PASS_WORD));
                    if (password.equals(upwd)) {
                        cursor.close();
                        return true;
                    } else {
                        cursor.close();
                        return false;
                    }
                } else {
                    cursor.close();
                    return false;
                }
            }
        });
        buttonRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 注册操作，跳转到注册页面
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initSQL(){
        mySql=new MySQLiteHelper(this,"user.db",null,1);
        db=mySql.getWritableDatabase();
    }
}
