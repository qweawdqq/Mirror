package com.example.dllo.mirror.activityworks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.intentworks.ToNextActivity;
import com.example.dllo.mirror.intentworks.ToNextListener;

/**
 * Created by jialiang on 16/3/30.
 * 登录的界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener,ToNextListener {
    private ImageButton back;
    private Button create, land;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        back = bindView(R.id.landing_btn_back);
        create = bindView(R.id.landing_btn_create);
        land = bindView(R.id.landing_btn_land);
    }

    @Override
    protected void initData() {
        back.setOnClickListener(this);
        create.setOnClickListener(this);
        land.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.landing_btn_back:

                this.finish();
                break;
            case R.id.landing_btn_create:
                ToNextActivity.toNextActivity(TO_SIGN_ACTIVITY,LoginActivity.this,false,null);
                break;
            case R.id.landing_btn_land:

                break;
        }
    }
}
