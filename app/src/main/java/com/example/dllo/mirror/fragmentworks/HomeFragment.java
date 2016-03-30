package com.example.dllo.mirror.fragmentworks;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.baseworks.BaseFragment;

/**
 * Created by JIALIANG on 16/3/29.
 * 主页的fragment
 */
public class HomeFragment extends BaseFragment {
    private Button btn_home;

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        btn_home = bindView(R.id.btn_home);
    }

    @Override
    protected void initData() {
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBroadReveice();
            }
        });
    }
    public void setBroadReveice(){
        Intent intent = new Intent();
        intent.setAction("activityworks");
        getActivity().sendBroadcast(intent);
    }
}
