package com.example.dllo.mirror.activityworks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.bean.WelcomeBean;
import com.example.dllo.mirror.intentworks.ToNextActivity;
import com.example.dllo.mirror.intentworks.ToNextListener;
import com.example.dllo.mirror.net.NetConnectionStatus;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.okhttp.FormEncodingBuilder;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by jialiang on 16/3/30.
 * 欢迎页面
 */
public class WelcomeActivity extends BaseActivity implements StaticEntityInterface, ToNextListener {

    private ImageView iv;
    private final long SPLASH_LENGTH = 2000;
    Handler handler = new Handler();
    private boolean netConnection;
    private DisplayImageOptions options;


    @Override
    protected int initLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        iv = bindView(R.id.activity_welcome_iv);
    }

    @Override
    protected void initData() {
//       初始化imageloader
        setMyImgLonder();
//        判断网络连接状态
        netConnection = NetConnectionStatus.getNetContectStatus(this);
        getNetStatus();
//        初始化handler  获取网络传值
        setHandler();
//       跳转到下一个页面
        toNextActivity();


    }

    //获取网络状态
    private void getNetStatus() {
        if (netConnection) {
            jsonFromNet();
        } else {
            String urlImg = NetConnectionStatus.getSharedPrefer(WelcomeActivity.this, WELCOME_ACTIVITY);
            ImageLoader.getInstance().displayImage(urlImg, iv, options);
        }
    }

    //设置handler
    private void setHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                WelcomeBean bean = new Gson().fromJson(msg.obj.toString(), WelcomeBean.class);
                Log.i("====", bean.getImg());
                //参数1  是图片网址 ,参数2 是图片窗口 imgview,参数3 是上面创建的配置
                ImageLoader.getInstance().displayImage(bean.getImg(), iv, options);
                NetConnectionStatus.saveSharedPrefer(WelcomeActivity.this, WELCOME_ACTIVITY, bean.getImg());
                return false;
            }
        });
    }

    //跳转到下一页
    private void toNextActivity() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ToNextActivity.toNextActivity(TO_MAIN_ACTYVITY,WelcomeActivity.this,true,null);
            }
        }, SPLASH_LENGTH); // 2秒后跳转至MainActivity
    }

    private void setMyImgLonder() {
        //显示图片的配置
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//是否放到内存缓存中
                .cacheOnDisk(true)//是否放到硬盘缓存中
                .bitmapConfig(Bitmap.Config.RGB_565)//图片的类型
                .build();//创建

    }

    public void jsonFromNet() {
        // OkhttpUtils 解析欢迎页背景图片
        //  http://blog.csdn.net/lmj623565791/article/details/49734867
        OkHttpUtils.post().url(STARTED_IMG).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                String body = response.body().string();
                // 将body 从子线程传到主线程中 再设置 到 xml 上 并刷新UI
                Message message = new Message();
                message.obj = body;
                handler.sendMessage(message);
                return null;
            }

            @Override
            public void onError(Call call, Exception e) {
            }

            @Override
            public void onResponse(Object response) {
            }
        });

    }

    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        super.onResume();
    }
}
