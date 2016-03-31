package com.example.dllo.mirror.activityworks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.dllo.mirror.R;

/**
 * Created by jialiang on 16/3/30.
 * 欢迎页面
 */
public class WelcomeActivity extends AppCompatActivity {


    private final long SPLASH_LENGTH = 1000;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // 使用handler的postDelayed实现延时跳转
        
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_LENGTH); // 1秒后跳转至MainActivity

    }
}
