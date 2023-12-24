package com.example.orderqueue;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    private Button searchButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin); // 设置布局文件

        // 初始化控件变量
        searchButton = findViewById(R.id.btn_search);
        backButton = findViewById(R.id.btn_back);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 在这里跳转用户操作界面的逻辑代码
                Intent intent=new Intent();
                intent.setClass(AdminActivity.this,QueryActivity.class);
                startActivity(intent);
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 在这里添加退出登录的逻辑代码，可以调用finish()方法关闭当前活动。
                Intent intent = new Intent();
                intent.setClass(AdminActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
