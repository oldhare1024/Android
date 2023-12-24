package com.example.orderqueue;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class QueryActivity extends AppCompatActivity {
    private MySQLiteHelper mySql;
    private SQLiteDatabase db;
    private EditText userPhone;
    private Button checkButton;
    private Button arrangeButton;
    private Button callButton;
    private Button backButton;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query); // 设置布局文件
        initSQL();
        // 初始化控件变量
        userPhone = findViewById(R.id.phone);
        checkButton = findViewById(R.id.btn_check);
        arrangeButton = findViewById(R.id.btn_arrange);
        callButton = findViewById(R.id.btn_call);
        backButton = findViewById(R.id.btn_back);
        // 设置按钮点击事件监听器
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 在这里添加审核用户的逻辑代码
                Intent intent = new Intent();
                String phone=userPhone.getText().toString();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(QueryActivity.this, "手机号不能为空!", Toast.LENGTH_SHORT).show();
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("phone", phone);
                    intent.putExtras(bundle);
                    intent.setClass(QueryActivity.this,InfoActivity.class);
                    startActivity(intent);
                }
            }
        });
        arrangeButton.setOnClickListener(new View.OnClickListener() {
            // TODO: 添加弹出框设置号码
            @Override
            public void onClick(View v) {
                phone=userPhone.getText().toString();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(QueryActivity.this, "手机号不能为空!", Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(QueryActivity.this);
                    builder.setTitle("分配号码");
                    final EditText input = new EditText(QueryActivity.this);
                    builder.setView(input);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Integer number = Integer.valueOf(input.getText().toString());
                            update(phone, number);
                            Toast.makeText(QueryActivity.this, "分配成功!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    builder.show();
                }
            }
            private void update(String uname, Integer num) {
                ContentValues values = new ContentValues();
                values.put(MySQLiteHelper.NUMBER, num);
                db.update(MySQLiteHelper.TABLE_NAME, values, MySQLiteHelper.NAME + "=?", new String[]{uname});
            }
        });
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 在这里添加打电话的逻辑代码
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(QueryActivity.this, "手机号不能为空!", Toast.LENGTH_SHORT).show();
                }else {
                    phone=userPhone.getText().toString();
                    Intent intent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                    startActivity(intent);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 在这里添加返回的逻辑代码，可以调用finish()方法关闭当前活动。
                Intent intent = new Intent();
                intent.setClass(QueryActivity.this,AdminActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initSQL(){
        mySql=new MySQLiteHelper(this,"user.db",null,1);
        db=mySql.getWritableDatabase();
    }
}
