package com.example.dllo.mirror.activityworks;

import android.content.Intent;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;

/**
 * Created by dllo on 16/4/14.
 */
public class ShareContentActivity extends BaseActivity implements StaticEntityInterface {

    private ShareContentActivity bean;

    @Override
    protected int initLayout() {
        return R.layout.activity_sharecontent;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getNetData();
    }

    /**
     * 获得网络数据
     */
    private void getNetData() {

        // 得到从shareFragment 的适配器里传过来的id
        Intent intent = getIntent();
        String id = intent.getStringExtra("story_id");

        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add(TOKEN, "");
        builder.add(DEVICE_TYPE, "2");
        builder.add(STORY_ID, id);
        OkHttpNetHelper helper = new OkHttpNetHelper();
        helper.getPostDataFromNet(builder, STORY_INFO, new NetListener() {
            @Override
            public void getSuccess(String s) {
                bean = new Gson().fromJson(s, ShareContentActivity.class);
            }

            @Override
            public void getFail(String s) {

            }
        });
    }
}
