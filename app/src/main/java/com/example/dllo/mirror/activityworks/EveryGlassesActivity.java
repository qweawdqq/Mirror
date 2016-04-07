package com.example.dllo.mirror.activityworks;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.mirror.FullyGridLayoutManager;
import com.example.dllo.mirror.ObservableScrollView;
import com.example.dllo.mirror.ScrollViewListener;
import com.example.dllo.mirror.adapterworks.EveryGlassesBackRecyclerViewAdapter;
import com.example.dllo.mirror.adapterworks.EveryGlassesFrontRecyclerViewAdapter;
import com.example.dllo.mirror.baseworks.BaseActivity;

import java.util.ArrayList;

public class EveryGlassesActivity extends BaseActivity implements ScrollViewListener, View.OnClickListener {

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


    }

    @Override
    protected void initData() {
        backAdapter = new EveryGlassesBackRecyclerViewAdapter();
        frontAdapter = new EveryGlassesFrontRecyclerViewAdapter();

        FullyGridLayoutManager backManager = new FullyGridLayoutManager(this, 1);
        recyclerViewBack.setLayoutManager(backManager);
        FullyGridLayoutManager frontManager = new FullyGridLayoutManager(this, 1);
        recyclerViewFront.setLayoutManager(frontManager);

        // TODO 假数据
        ArrayList<String> data = new ArrayList<>();
        data.add("aaa");
        data.add("sss");
        data.add("ddd");
        data.add("fff");
        data.add("ggg");
        // 添加数据的方法  输入的是一个集合
        backAdapter.addData(data);
        frontAdapter.addData(data);

        recyclerViewBack.setAdapter(backAdapter);
        recyclerViewFront.setAdapter(frontAdapter);

        colorChanceRelativeLayout.setAlpha((float) 0.8);
        colorChanceLinearLayout.setAlpha((float) 0.8);

        ivBack.setOnClickListener(this);
        tvToPic.setOnClickListener(this);
        ivBuy.setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.everyglasses_button_back:
                // TODO 跳转
                Toast.makeText(EveryGlassesActivity.this, "点击了返回按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.everyglasses_button_topic:
                Toast.makeText(EveryGlassesActivity.this, "点击了佩戴图集按钮", Toast.LENGTH_SHORT).show();
                break;
            case R.id.everyglasses_button_buy:
                Toast.makeText(EveryGlassesActivity.this, "点击了购买按钮", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
