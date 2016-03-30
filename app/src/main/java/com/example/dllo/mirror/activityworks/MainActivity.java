package com.example.dllo.mirror.activityworks;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.animationworks.MirrorScaleAtion;
import com.example.dllo.mirror.fragmentworks.HomeFragment;
import com.example.dllo.mirror.fragmentworks.MenuFragment;
import com.example.dllo.mirror.interfaceworks.AllReceiveListener;
import com.example.dllo.mirror.receiveworks.MyAllReceive;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean whatFragment = true;
    private ImageView mirror;
    private TextView land;
    private MirrorScaleAtion ation;
    private MyAllReceive receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mirror = (ImageView) findViewById(R.id.main_iv_mirror);
        land = (TextView) findViewById(R.id.main_iv_land);
        initData();
    }

    //初始化数据方法
    private void initData() {
        setMyReceiver();
        setLayout();
        getMyBroadReceive();
        ation = new MirrorScaleAtion(this, null);
        mirror.setOnClickListener(this);
        land.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
//        取消广播
        receive.stopReceive(this,receive.getMenuReceive());
        super.onDestroy();
    }

    //    fragmentmanger  管理与替换视图
    private void setLayout() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        if (whatFragment) {
            tx.replace(R.id.main_flt_layout, new HomeFragment());
            tx.commit();
            whatFragment = false;
        } else {
            tx.replace(R.id.main_flt_layout, new MenuFragment());
            tx.commit();
            whatFragment = true;
        }
    }

    //    注册广播的方法
    public void setMyReceiver() {
        receive = new MyAllReceive();
        receive.startReceive(this, "activityworks",receive.getMenuReceive());
    }

    //mirror 缩放动画
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_iv_mirror:
                ation.setView(mirror);
                ation.setMirrorScaleLinister();
                break;
            case R.id.main_iv_land:
                Intent intent = new Intent(this,LandingActivity.class);
                startActivity(intent);
                break;
        }

    }

    //    得到广播的方法
    private void getMyBroadReceive() {
        receive.getMenuReceive().setMyListener(new AllReceiveListener() {
            @Override
            public void setAllReceiveListener(Context context, Intent intent) {
                setLayout();
            }
        });
    }

}
