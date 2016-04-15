package com.example.dllo.mirror.activityworks;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dllo.mirror.bean.Bean;
import com.example.dllo.mirror.allviewworks.FullyGridLayoutManager;

import com.example.dllo.mirror.allviewworks.ObservableScrollView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.interfaceworks.ScrollViewListener;

import com.example.dllo.mirror.adapterworks.EveryGlassesBackRecyclerViewAdapter;
import com.example.dllo.mirror.adapterworks.EveryGlassesFrontRecyclerViewAdapter;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.intentworks.ToNextActivity;
import com.example.dllo.mirror.intentworks.ToNextListener;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.squareup.okhttp.FormEncodingBuilder;

import java.io.Serializable;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


public class EveryGlassesActivity extends BaseActivity implements ScrollViewListener,
        View.OnClickListener, StaticEntityInterface, ToNextListener {

    private ObservableScrollView scrollViewFront = null;
    private ObservableScrollView scrollViewBack = null;
    private RecyclerView recyclerViewFront, recyclerViewBack;
    private EveryGlassesBackRecyclerViewAdapter backAdapter;
    private EveryGlassesFrontRecyclerViewAdapter frontAdapter;
    private RelativeLayout colorChanceRelativeLayout;
    private LinearLayout colorChanceLinearLayout;
    private ImageView ivBack, ivBuy, ivShare;
    private TextView tvToPic;
    private RelativeLayout buttonLayout;
    private boolean btnBL = false;
    private TextView tvEnglishTitle, tvName, tvContent, tvPrice, tvNameBeforerecyclerview;
    private ImageView ivBackground;

    // 用于接收recyclerView子布局的位置 的id   跳转到哪个二级页面
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
        ivShare = bindView(R.id.everyglasses_share);
        tvToPic = bindView(R.id.everyglasses_button_topic);
        tvEnglishTitle = bindView(R.id.everyglasses_englishTitle);
        tvName = bindView(R.id.everyglasses_name);
        tvContent = bindView(R.id.everyglasses_glassesContent);
        tvPrice = bindView(R.id.everyglasses_price);
        tvNameBeforerecyclerview = bindView(R.id.everyglasses_name_beforerecyclerview);
        ivBackground = bindView(R.id.everyglasses_background_iv);

        // 属性动画  刚进入页面时就隐藏到屏幕左边
        ObjectAnimator.ofFloat(buttonLayout, "translationX", 0, -2000).setDuration(100).start();

        // 两层滑动的 scrollView
        scrollViewFront = bindView(R.id.everyglasses_scrollView_front);
        scrollViewFront.setScrollViewListener(this);
        scrollViewBack = bindView(R.id.everyglasses_scrollView_back);
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
        ivShare.setOnClickListener(this);
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
        OkHttpNetHelper httpNetHelper = new OkHttpNetHelper();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        // 解析数据
        builder.add(TOKEN, "");
        builder.add(DEVICE_TYPE, "3");
        builder.add(GOODS_INFO_GOODS_ID, id);
        builder.add(GOODS_LIST_VERSION, "1.0.1");

        httpNetHelper.getPostDataFromNet(builder, GOODS_INFO, new NetListener() {
            @Override
            public void getSuccess(String s) {

                Gson gson = new Gson();
                Bean myBean = gson.fromJson(s.toString(), Bean.class);
                Message msg = Message.obtain();
                msg.obj = myBean;
                handler.sendMessage(msg);

            }

            @Override
            public void getFail(String s) {

            }
        });


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                bean = (Bean) msg.obj;
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


    /**
     * 滚动监听 的方法
     *
     * @param scrollView
     * @param y          移动后y的位置
     * @param oldy       移动前y的位置
     */
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

    /**
     * 跳转到Video页面方法
     */
    private void toVideoActivity() {
        if (bean != null) {
            Log.e("size", bean.getData().getWear_video().size() + "");
            List<Bean.DataBean.WearVideoBean> videoBeans = bean.getData().getWear_video();
            Bundle bundle = new Bundle();
            bundle.putSerializable(TO_VIEDO_ACTIVITY, (Serializable) videoBeans);
            ToNextActivity.toNextActivity(TO_VIEDO_ACTIVITY, this, false, bundle);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.everyglasses_button_back:
                finish();
                break;
            case R.id.everyglasses_button_topic:
                toVideoActivity();
                break;
            case R.id.everyglasses_button_buy:
                Bundle bundle = new Bundle();
                bundle.putString("img", bean.getData().getGoods_pic());
                bundle.putString("price", bean.getData().getGoods_price());
                bundle.putString("name", bean.getData().getGoods_name());
                bundle.putString("goods_id", bean.getData().getGoods_id());
//                OrderActivity orderActivity = new OrderActivity();

                ToNextActivity.toNextActivity(TO_ORDER_ACTIVITY, this, false, bundle);
                break;
            case R.id.everyglasses_share:

                ShareSDK.initSDK(this);
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();

                // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                // oks.setTitle(getString(R.string.share));
                oks.setTitle("美若推荐");
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                oks.setTitleUrl(bean.getData().getGoods_share() + bean.getData().getGoods_id());
                // text是分享文本，所有平台都需要这个字段
                oks.setText(bean.getData().getModel());
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl(bean.getData().getGoods_img());
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//                 oks.setComment("我是测试评论文本");
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite(getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl(bean.getData().getGoods_share() + bean.getData().getGoods_id());

                // 启动分享GUI
                oks.show(this);

                break;
        }
    }


}
