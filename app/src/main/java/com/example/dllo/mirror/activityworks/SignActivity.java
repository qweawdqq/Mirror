package com.example.dllo.mirror.activityworks;

import android.os.Bundle;
import android.util.Log;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.intentworks.ToNextListener;

/**
 * Created by dllo on 16/3/31.
 * 创建账号的界面
 */
public class SignActivity extends BaseActivity implements ToNextListener {
    @Override
    protected int initLayout() {
        return R.layout.activity_sign;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
    }
}
