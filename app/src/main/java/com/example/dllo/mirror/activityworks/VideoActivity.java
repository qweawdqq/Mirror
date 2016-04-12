package com.example.dllo.mirror.activityworks;

import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.adapterworks.HeadRecycleAdapter;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by dllo on 16/4/8.
 */
public class VideoActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private HeadRecycleAdapter adapter;
    private JCVideoPlayer jCVideoPlayer;

    @Override
    protected int initLayout() {
        return R.layout.activity_video;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.video_recycle);
    }

    @Override
    protected void initData() {
        adapter = new HeadRecycleAdapter(this);
        setMyVideoView();
        setRecyclerView();
        stopVideoview();

    }

    private void setMyVideoView() {
        View view = LayoutInflater.from(this).inflate(R.layout.video_recycle_video, null);
        jCVideoPlayer = (JCVideoPlayer) view.findViewById(R.id.videocontroller1);
//        这里的网址换成 我们拉取数据的网址就OK了
        jCVideoPlayer.setUp("http://flv2.bn.netease.com/videolib3/1604/11/GUubL9293/SD/GUubL9293-mobile.mp4", "");
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
        ImageLoader.getInstance().displayImage("http://c.hiphotos.baidu.com/image/pic/item/b7003af33a87e9500f22e6aa14385343fbf2b43a.jpg", jCVideoPlayer.ivThumb, options);

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

        ArrayList<String> data = new ArrayList<>();
        data.add("http://e.hiphotos.baidu.com/image/pic/item/eaf81a4c510fd9f99b356067272dd42a2834a499.jpg");
        data.add("http://e.hiphotos.baidu.com/image/pic/item/b90e7bec54e736d1db84320899504fc2d562691f.jpg");
        data.add("http://f.hiphotos.baidu.com/image/pic/item/2f738bd4b31c8701b40e7bc8257f9e2f0708ff60.jpg");
        data.add("http://c.hiphotos.baidu.com/image/pic/item/c83d70cf3bc79f3dfa086b84bfa1cd11738b299c.jpg");
        data.add("http://b.hiphotos.baidu.com/image/pic/item/a686c9177f3e670900d880193fc79f3df9dc5578.jpg");
        data.add("http://c.hiphotos.baidu.com/image/pic/item/b7003af33a87e9500f22e6aa14385343fbf2b43a.jpg");
        data.add("http://b.hiphotos.baidu.com/image/pic/item/738b4710b912c8fcda0a68faf9039245d688210f.jpg");
        data.add("http://a.hiphotos.baidu.com/image/pic/item/dbb44aed2e738bd49b456038a38b87d6277ff957.jpg");
        adapter.addInfo(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


}
