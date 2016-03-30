package com.example.dllo.mirror.baseworks;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.dllo.mirror.R;

/**
 * Created by jialiang on 16/3/28.
 * Acticity的基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
    }

    //初始布局窗口
    protected abstract int initLayout();

    //初始化控件窗口

    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    //绑定控件方法
    protected <T extends View> T bindView(int id) {
        return (T) findViewById(id);
    }

}
