package com.example.dllo.mirror.activityworks;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.adapterworks.VerPagerAdapter;
import com.example.dllo.mirror.allviewworks.ItemVerticalViewpager;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.bean.ShareContentActivityBean;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/14.
 */
public class ShareContentActivity extends BaseActivity implements StaticEntityInterface {
    private ArrayList<View> list;
    private ItemVerticalViewpager viewpager;
    private VerPagerAdapter adapter;
    private Handler handler;

    @Override
    protected int initLayout() {
        return R.layout.activity_sharecontent;
    }

    @Override
    protected void initView() {
        viewpager = bindView(R.id.share_viewpager);
    }

    @Override
    protected void initData() {
        setHandler();
        getNetData();
    }

    /**
     * 设置viewpager
     * @param bean  需要的实体类
     */
    private void setViewPager(ShareContentActivityBean bean) {
        list = new ArrayList<>();
        for (int i = 0; i < bean.getData().getList().size(); i++) {

        }
    }

    /**
     * 初始化Handler
     */
    private void setHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ShareContentActivityBean bean = (ShareContentActivityBean) msg.obj;
                setViewPager(bean);
            }
        };
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
                ShareContentActivityBean beanx = new Gson().fromJson(s, ShareContentActivityBean.class);
                Message message = Message.obtain();
                message.obj = beanx;
                handler.sendMessage(message);
            }

            @Override
            public void getFail(String s) {

            }
        });
    }
}
