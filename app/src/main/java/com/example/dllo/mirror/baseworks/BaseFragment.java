package com.example.dllo.mirror.baseworks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * Created by jialiang on 16/3/28.
 * Fragment的基类
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(initLayout(),null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    //绑定控件的方法
    protected <T extends View> T bindView(int id) {
        return (T) getView().findViewById(id);
    }

    //初始化布局
    protected abstract int initLayout();

    // 初始化控件
    protected abstract void initView();

    //  初始化数据
    protected abstract void initData();
}
