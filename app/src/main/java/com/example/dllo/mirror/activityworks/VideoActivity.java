package com.example.dllo.mirror.activityworks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.dllo.mirror.Bean;
import com.example.dllo.mirror.R;
import com.example.dllo.mirror.adapterworks.HeadRecycleAdapter;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.intentworks.ToNextListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by dllo on 16/4/8.
 */
public class VideoActivity extends BaseActivity implements View.OnClickListener, ToNextListener {
    private RecyclerView recyclerView;
    private HeadRecycleAdapter adapter;
    private JCVideoPlayer jCVideoPlayer;
    private ImageButton btn_back, btn_buy;
    private List<Bean.DataBean.WearVideoBean> videoBeans;

    @Override
    protected int initLayout() {
        return R.layout.activity_video;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.video_recycle);
        btn_back = bindView(R.id.video_btn_back);
        btn_buy = bindView(R.id.video_btn_buy);
    }

    @Override
    protected void initData() {
        getIntentBundle();
        adapter = new HeadRecycleAdapter(this);
        setMyVideoView(getVideoUrl(), getVideoImg());
        setRecyclerView();
        stopVideoview();
        setBtnOnclick();

    }

    private ArrayList<Bean.DataBean.WearVideoBean> getImgArrayList() {
        ArrayList<Bean.DataBean.WearVideoBean> vbs = new ArrayList<Bean.DataBean.WearVideoBean>();
        for (int i = 0; i < videoBeans.size(); i++) {
            if (!videoBeans.get(i).getType().equals("8") && i != 1) {
                vbs.add(videoBeans.get(i));
            }
        }
        return vbs;
    }

    private String getVideoUrl() {
        Bean.DataBean.WearVideoBean videoBean = new Bean.DataBean.WearVideoBean();
        for (int i = 0; i < videoBeans.size(); i++) {
            if (videoBeans.get(i).getType().equals("8")) {
                videoBean = videoBeans.get(i);
            }
        }
        return videoBean.getData();
    }

    private String getVideoImg() {
        String img = videoBeans.get(1).getData();
        Log.e("-------", img);
        return img;
    }

    private void getIntentBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        videoBeans = (List<Bean.DataBean.WearVideoBean>) bundle.get(TO_VIEDO_ACTIVITY);
    }

    private void setBtnOnclick() {
        btn_buy.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    private void setMyVideoView(String videoUrl, String videoImg) {
        View view = LayoutInflater.from(this).inflate(R.layout.video_recycle_video, null);
        jCVideoPlayer = (JCVideoPlayer) view.findViewById(R.id.videocontroller1);
//        这里的网址换成 我们拉取数据的网址就OK了
        jCVideoPlayer.setUp(videoUrl, "");
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
//        图片的网址换成网络拉取数据的网址就OK了
        ImageLoader.getInstance().displayImage(videoImg, jCVideoPlayer.ivThumb, options);

        adapter.setHeaderView(view);
    }

    //停止播放的方法
    private void stopVideoview() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Math.abs(dy) > 0) {
                    jCVideoPlayer.releaseAllVideos();
                }
            }
        });
    }

    private void setRecyclerView() {
        ArrayList<Bean.DataBean.WearVideoBean> list = getImgArrayList();

        adapter.addInfo(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_btn_back:
                finish();
                break;
            case R.id.video_btn_buy:
                break;
        }
    }
}
