package com.example.dllo.mirror.activityworks;


import android.content.Intent;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.GetChars;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.adapterworks.HomeFragmentAdapter;
import com.example.dllo.mirror.allviewworks.VerticalViewPager;
import com.example.dllo.mirror.animationworks.MirrorScaleAction;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.bean.MenuFragmentBean;


import com.example.dllo.mirror.db.DbHelper;
import com.example.dllo.mirror.db.HomeData;

import com.example.dllo.mirror.fragmentworks.AllFragment;
import com.example.dllo.mirror.fragmentworks.BuyFragment;
import com.example.dllo.mirror.fragmentworks.ListFragment;

import com.example.dllo.mirror.fragmentworks.ShareFragment;
import com.example.dllo.mirror.net.NetConnectionStatus;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, StaticEntityInterface {
    private ImageView mirror;
    private TextView land;
    private MirrorScaleAction action;
    private VerticalViewPager viewPager;
    private HomeFragmentAdapter adapter;
    private Handler handler;
    private MenuFragmentBean bean;
    private int pos;
    private boolean netStauts;


    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mirror = bindView(R.id.main_iv_mirror);
        land = bindView(R.id.main_iv_land);
        viewPager = bindView(R.id.fragment_home_viewpager);
    }

    @Override
    protected void initData() {
        setAllonClick();

        getMyIntent();
        getNetStatus();
        adapter = new HomeFragmentAdapter(getSupportFragmentManager());
    }

    /**
     * 接收从MenuFragment中传过来的 点击 菜单行布局 的 position, 让viewPager滑动到相对应的位置
     */
    private void getMyIntent() {
        Intent intent = getIntent();
        pos = intent.getIntExtra("position", 0);
        setMyHandler(pos);
    }


    /**
     * 得到网络状态  并进行断网时与联网时 对应的操作
     */
    private void getNetStatus() {
        netStauts = NetConnectionStatus.getNetContectStatus(this);
        if (netStauts) {
            getNetData();
        } else {
            HomeData homeData = DbHelper.getInstance(this).getNote("mainActivity");
            String value = homeData.getValue();
            jsonData(value);
        }


    }


    /**
     * 初始化Handler
     * @param pos  指定viewpager 滑动到对应的位置
     */
    private void setMyHandler(final int pos) {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                ArrayList<Fragment> list = (ArrayList<Fragment>) msg.obj;
                adapter.addData(list);
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(pos);
                return false;
            }
        });
    }

    /**
     * 设置所有的点击事件
     */
    private void setAllonClick() {
        action = new MirrorScaleAction(this, null);
        mirror.setOnClickListener(this);
        land.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_iv_mirror:
                //mirror 缩放动画
                action.setView(mirror);
                action.setMirrorScaleLinister();
                break;
            case R.id.main_iv_land:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }

    }

    /**
     * 请求网络数据
     */
    public void getNetData() {

        // 获取网络数据
        OkHttpNetHelper helper = new OkHttpNetHelper();

        // 获取菜单页的标签的集合的大小来确定ViewPager 能滑动的页数
        // 并且设置标签内容到MenuFragment 的Title 上
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        formEncodingBuilder.add(TOKEN, "");
        formEncodingBuilder.add(DEVICE_TYPE, "2");
        helper.getPostDataFromNet(formEncodingBuilder, MENU_LIST, new NetListener() {
            @Override
            public void getSuccess(String s) {
                jsonData(s);
                HomeData hd = new HomeData();
                hd.setKey("mainActivity");
                hd.setValue(s);
                DbHelper.getInstance(MainActivity.this).addData(hd);
            }

            @Override
            public void getFail(String s) {

            }
        });
    }

    /**
     * 解析数据
     * @param s  从网络或者数据库的得到的string
     */
    private void jsonData(String s) {
        bean = new Gson().fromJson(s.toString(), MenuFragmentBean.class);
        List<MenuFragmentBean.DataBean.ListBean> list = bean.getData().getList();
        sendMessage(list);
    }


    /**
     * 发送消息
     * @param list   菜单集合
     */
    private void sendMessage(List<MenuFragmentBean.DataBean.ListBean> list) {
        ArrayList<Fragment> fragment = new ArrayList<Fragment>();
        for (int i = 0; i < list.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("title", list.get(i));
            bundle.putInt("titleAtWhatItem", i);
//                    bundle.putString("titleNetData", bean.toString());
            String type = list.get(i).getType();
            // 根据type判断加载不同的fragment, 6 是 全部分类, 3 是两种眼镜的种类的列表, 4 是购物车
            switch (type) {
                case "6":
                    AllFragment af = new AllFragment();
                    af.setArguments(bundle);
                    fragment.add(af);
                    break;
                case "3":
                    ListFragment lf = new ListFragment();
                    lf.setArguments(bundle);
                    fragment.add(lf);
                    break;
                case "2":
                    ShareFragment sf = new ShareFragment();
                    sf.setArguments(bundle);
                    fragment.add(sf);
                    break;
                case "4":
                    BuyFragment bf = new BuyFragment();
                    bf.setArguments(bundle);
                    fragment.add(bf);
                    break;

            }

        }
        Message message = Message.obtain();
        message.obj = fragment;
        handler.sendMessage(message);
    }
}
