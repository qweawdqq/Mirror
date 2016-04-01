package com.example.dllo.mirror.activityworks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.bean.WelcomeBean;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.FormEncodingBuilder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by jialiang on 16/3/30.
 * 欢迎页面
 */
public class WelcomeActivity extends BaseActivity implements StaticEntityInterface{

    private ImageView iv;
    private final long SPLASH_LENGTH = 2000;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                WelcomeBean bean = new Gson().fromJson(msg.obj.toString(),WelcomeBean.class);
                Log.i("====",bean.getImg());
                //显示图片的配置
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.mipmap.ic_launcher)   //加载过程中的图片
                        .showImageOnFail(R.mipmap.ic_launcher) //加载失败的图片
                        .cacheInMemory(true)//是否放到内存缓存中
                        .cacheOnDisk(true)//是否放到硬盘缓存中
                        .bitmapConfig(Bitmap.Config.RGB_565)//图片的类型
                        .build();//创建

                ImageLoader.getInstance().displayImage(bean.getImg(), iv, options);//参数1  是图片网址 ,参数2 是图片窗口 imgview,参数3 是上面创建的配置

                return false;
            }
        });
    }

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

        // 使用handler的postDelayed实现延时跳转
        
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_LENGTH); // 2秒后跳转至MainActivity


        // OkhttpUtils 解析欢迎页背景图片
        //  http://blog.csdn.net/lmj623565791/article/details/49734867

        OkHttpUtils.post().url(STARTED_IMG).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                String body = response.body().string();
                Log.d("----",body);

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




}
