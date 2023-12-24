package com.example.orderqueue;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Random;

public class QueueActivity extends AppCompatActivity implements View.OnClickListener {

    private MySQLiteHelper mySql;
    private SQLiteDatabase db;
    private Button applyButton;
    private Button numButton;
    private Button signButton;
    private Button backButton;
    private Integer number = new Random(System.currentTimeMillis()).nextInt(99)+1;
    private Boolean flag=false;//判定是否预约
    private Boolean flag2=false;//判定是否已签到
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue); // 设置布局文件
        initSQL();
        // 初始化控件变量
        applyButton = findViewById(R.id.btn_apply);
        numButton = findViewById(R.id.btn_num);
        signButton = findViewById(R.id.btn_sign);
        backButton = findViewById(R.id.btn_back);

        // 设置按钮点击事件监听器
        applyButton.setOnClickListener(this::onClick1);

        numButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 在这里添加查看号码的逻辑代码
                if(flag){
                    Intent intent = getIntent();
                    String uname = intent.getStringExtra("username");
                    update(uname,number);
                    Toast.makeText(QueueActivity.this, "号码为"+number, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(QueueActivity.this, "请先预约排队！ ", Toast.LENGTH_SHORT).show();
                }
            }
            private void update(String uname, Integer num) {
                ContentValues values = new ContentValues();
                values.put(MySQLiteHelper.NUMBER, num);
                db.update(MySQLiteHelper.TABLE_NAME, values, MySQLiteHelper.NAME + "=?", new String[]{uname});
            }
        });

        signButton.setOnClickListener(this::onClick);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 在这里添加退出登录的逻辑代码
                Intent intent = new Intent();
                intent.setClass(QueueActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onClick1(View v) {
        if(flag){
            Toast.makeText(this, "已预约成功，请勿重复预约！ ", Toast.LENGTH_SHORT).show();
        }else {
            scan();
        }
    }
    @Override
    public void onClick(View v) {
        if(flag&&!flag2){
            scan();
            Intent intent = getIntent();
            String uname = intent.getStringExtra("username");
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.NUMBER, 0);
            db.update(MySQLiteHelper.TABLE_NAME, values, MySQLiteHelper.NAME + "=?", new String[]{uname});
            flag2=true;
        } else {
            Toast.makeText(QueueActivity.this, "请先预约排队！ ", Toast.LENGTH_SHORT).show();
        }
    }
    private void scan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setPrompt("扫描条码");
        integrator.setOrientationLocked(false);
        integrator.setCameraId(0);  // 使用默认的相机
        integrator.setBeepEnabled(false); // 扫到码后播放提示音
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "扫码取消！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "扫码成功 ", Toast.LENGTH_SHORT).show();
                flag=true;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void initSQL(){
        mySql=new MySQLiteHelper(this,"user.db",null,1);
        db=mySql.getWritableDatabase();
    }
}
