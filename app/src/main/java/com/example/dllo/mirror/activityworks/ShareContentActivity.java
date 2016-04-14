package com.example.dllo.mirror.activityworks;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.adapterworks.VerPagerAdapter;
import com.example.dllo.mirror.allviewworks.ItemVerticalViewpager;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.bean.ShareContentActivityBean;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
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
    private ImageView share_bg;

    @Override
    protected int initLayout() {
        return R.layout.activity_sharecontent;
    }

    @Override
    protected void initView() {
        viewpager = bindView(R.id.share_viewpager);
        share_bg = bindView(R.id.img_share_bg);
    }

    @Override
    protected void initData() {
        setHandler();
        getNetData();
    }

    /**
     * 设置viewpager
     *
     * @param bean 需要的实体类
     */
    private void setViewPager(ShareContentActivityBean bean) {
        list = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        for (int i = 0; i < bean.getData().getStory_data().getImg_array().size(); i++) {
            title.add(bean.getData().getStory_data().getImg_array().get(i));
            View view = LayoutInflater.from(this).inflate(R.layout.viewpage_item, null);
            TextView title_up = (TextView) view.findViewById(R.id.tv_share_one);
            TextView title_min = (TextView) view.findViewById(R.id.tv_share_two);
            TextView title_down = (TextView) view.findViewById(R.id.tv_share_thr);
            title_up.setText(bean.getData().getStory_data().getText_array().get(i).getSmallTitle());
            title_min.setText(bean.getData().getStory_data().getText_array().get(i).getTitle());
            title_down.setText(bean.getData().getStory_data().getText_array().get(i).getSubTitle());
            list.add(view);
        }
        adapter = new VerPagerAdapter(list);
        viewpager.setMyBackGound(share_bg, title);
        viewpager.setAdapter(adapter);
        ImageLoader.getInstance().displayImage(title.get(0), share_bg);
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
        builder.add(STORY_ID, "2");
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
