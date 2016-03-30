package com.example.dllo.mirror.activityworks;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.baseworks.BaseActivity;

/**
 * Created by dllo on 16/3/30.
 */
public class LandingActivity extends BaseActivity implements View.OnClickListener {
    private ImageButton back;
    private Button create, land;

    @Override
    protected int initLayout() {
        return R.layout.activity_landing;
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

                break;
            case R.id.landing_btn_land:

                break;
        }
    }
}
