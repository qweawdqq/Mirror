package com.example.dllo.mirror.activityworks;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.mirror.Bean;
import com.example.dllo.mirror.FullyGridLayoutManager;

import com.example.dllo.mirror.ObservableScrollView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.ScrollViewListener;

import com.example.dllo.mirror.adapterworks.EveryGlassesBackRecyclerViewAdapter;
import com.example.dllo.mirror.adapterworks.EveryGlassesFrontRecyclerViewAdapter;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.squareup.okhttp.FormEncodingBuilder;

import java.util.ArrayList;


public class EveryGlassesActivity extends BaseActivity implements ScrollViewListener,
        View.OnClickListener, StaticEntityInterface {

    private ObservableScrollView scrollViewFront = null;
    private ObservableScrollView scrollViewBack = null;

    private RecyclerView recyclerViewFront, recyclerViewBack;
    private EveryGlassesBackRecyclerViewAdapter backAdapter;
    private EveryGlassesFrontRecyclerViewAdapter frontAdapter;

    private RelativeLayout colorChanceRelativeLayout;
    private LinearLayout colorChanceLinearLayout;

    // 按钮
    private ImageView ivBack, ivBuy;
    private TextView tvToPic;
    private RelativeLayout buttonLayout;
    private boolean btnBL = false;

    private TextView tvEnglishTitle, tvName, tvContent, tvPrice, tvNameBeforerecyclerview;
    private ImageView ivBackground;

    // 用于接收recyclerview子布局的位置 的id   跳转到哪个二级页面
    private int id;
    private Bean bean;// 单个页面的数据类

    private Handler handler;
    private DisplayImageOptions options;  //显示图片的配置

    @Override
    protected int initLayout() {
        return R.layout.activity_everyglasses;
    }

    @Override
    protected void initView() {
        buttonLayout = bindView(R.id.everyglasses_button_layout);
        ivBack = bindView(R.id.everyglasses_button_back);
        ivBuy = bindView(R.id.everyglasses_button_buy);
        tvToPic = bindView(R.id.everyglasses_button_topic);
        tvEnglishTitle = bindView(R.id.everyglasses_englishTitle);
        tvName = bindView(R.id.everyglasses_name);
        tvContent = bindView(R.id.everyglasses_glassesContent);
        tvPrice = bindView(R.id.everyglasses_price);
        tvNameBeforerecyclerview = bindView(R.id.everyglasses_name_beforerecyclerview);
        ivBackground = bindView(R.id.everyglasses_background_iv);

//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_create);
//        buttonLayout.setAnimation(animation);

        // 属性动画  刚进入页面时就隐藏到屏幕左边
        ObjectAnimator.ofFloat(buttonLayout, "translationX", 0, -2000).setDuration(100).start();

        // 两层滑动的 scrollView
        scrollViewFront = bindView(R.id.everyglasses_scrollView_front);
        scrollViewFront.setScrollViewListener(this);
        scrollViewBack = bindView(R.id.everyglasses_scrollView_back);
//        scrollViewBack.setScrollViewListener(this);

        recyclerViewBack = bindView(R.id.everyglasses_recyclerviewBack);
        recyclerViewFront = bindView(R.id.everyglasses_recyclerviewFront);

        // 透明度渐变的地方
        colorChanceRelativeLayout = bindView(R.id.everyglasses_color_chance_relativeLayout);
        colorChanceLinearLayout = bindView(R.id.everyglasses_color_chance_linearLayout);
        // 启动时就隐藏到屏幕外
        colorChanceRelativeLayout.setAlpha((float) 0.8);
        colorChanceLinearLayout.setAlpha((float) 0.8);

        ivBack.setOnClickListener(this);
        tvToPic.setOnClickListener(this);
        ivBuy.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        backAdapter = new EveryGlassesBackRecyclerViewAdapter();
        frontAdapter = new EveryGlassesFrontRecyclerViewAdapter();

        FullyGridLayoutManager backManager = new FullyGridLayoutManager(this, 1);
        recyclerViewBack.setLayoutManager(backManager);
        FullyGridLayoutManager frontManager = new FullyGridLayoutManager(this, 1);
        recyclerViewFront.setLayoutManager(frontManager);

        //显示图片的配置
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)   //加载过程中的图片
                .showImageOnFail(R.mipmap.ic_launcher) //加载失败的图片
                .cacheInMemory(true)//是否放到内存缓存中
                .cacheOnDisk(true)//是否放到硬盘缓存中
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)//图片的类型
                .build();//创建


        String id = getIntent().getStringExtra("id");
//        Log.i("8888888888888888", "id    " + id);

        OkHttpNetHelper httpNetHelper = new OkHttpNetHelper();

        FormEncodingBuilder builder = new FormEncodingBuilder();
        // 解析数据
        builder.add("token", "");
        builder.add("device_type", "3");
        builder.add("goods_id", id);


        httpNetHelper.getPostDataFromNet(builder, GOODS_INFO, new NetListener() {
            @Override
            public void getSuccess(String s) {

//                Log.i("8888888888888888", "解析成功");


                Gson gson = new Gson();
                bean = gson.fromJson(s.toString(), Bean.class);

//                Log.i("8888888888888888", s);
//                Log.i("8888888888888888", bean.toString());

                Message msg = Message.obtain();
                msg.obj = bean;
                handler.sendMessage(msg);


            }

            @Override
            public void getFail(String s) {
//                Log.i("8888888888888888", "解析失败");
            }
        });


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                Bean newBean = (Bean) msg.obj;
                tvEnglishTitle.setText(bean.getData().getGoods_name());
                tvName.setText(bean.getData().getBrand());
                tvContent.setText(bean.getData().getInfo_des());
                tvPrice.setText(bean.getData().getGoods_price());
                tvNameBeforerecyclerview.setText(bean.getData().getBrand());

                ImageLoader.getInstance().displayImage(bean.getData().getGoods_img(), ivBackground, options);

                // 添加数据的方法  输入的是一个集合
                backAdapter.addData(bean);
                frontAdapter.addData(bean);

                recyclerViewBack.setAdapter(backAdapter);
                recyclerViewFront.setAdapter(frontAdapter);

                return false;
            }
        });

    }


    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
                                int oldx, int oldy) {
        if (scrollView == scrollViewFront) {
            scrollViewBack.scrollTo(x, y);


            // Y值改变的量
            int chanceY = (int) ((y / 0.8) - oldy);

            // 透明度渐变
            if (y > 0 && y <= 800) {
                if (chanceY > 0) {
                    //  除0.8  乘0.8 不能消
                    colorChanceRelativeLayout.setAlpha((float) (0.8 - y / 0.8 / 800 * 0.8 / 2));
                    colorChanceLinearLayout.setAlpha((float) (0.8 - y / 0.8 / 800 * 0.8 / 2));
                }

                if (chanceY < 0) {
                    int nowAlpha = (int) colorChanceLinearLayout.getAlpha();
                    colorChanceRelativeLayout.setAlpha((float) (0.8 - y / 0.8 / 800 * 0.8 / 2));
                    colorChanceLinearLayout.setAlpha((float) (0.8 - y / 0.8 / 800 * 0.8 / 2));
                }
            }


            // 滑动按钮飞入.飞出
            if (y > 2900) {
                if (chanceY > 0) {
                    if (btnBL == false) {
                        ObjectAnimator.ofFloat(buttonLayout, "translationX", -2000, 0).setDuration(1000).start();
                        btnBL = true;
                    }
                }

            }
            if (y < 2850) {
                if (chanceY < 0) {
                    if (btnBL == true) {
                        ObjectAnimator.ofFloat(buttonLayout, "translationX", 0, -2000).setDuration(1000).start();
                        btnBL = false;
                    }
                }
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.everyglasses_button_back:
                // TODO 跳转
                Toast.makeText(EveryGlassesActivity.this, "点击了返回按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.everyglasses_button_topic:
                Toast.makeText(EveryGlassesActivity.this, "点击了佩戴图集按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.everyglasses_button_buy:
//                Toast.makeText(EveryGlassesActivity.this, "点击了购买按钮", Toast.LENGTH_SHORT).show();
                Intent intentToOrder=new Intent(EveryGlassesActivity.this,OrderActivity.class);
                startActivity(intentToOrder);

                break;
        }
    }


}
