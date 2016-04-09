package com.example.dllo.mirror.allviewworks;


import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * 子页面----粘性头部的竖向viewpager
 * Created by jialiang on 16/4/5.
 * 使用时记得 给第0位置的viewpager 设置一张替换的背景图片
 * 在滑动的时候他就会自动更新了
 */
public class ItemVerticalViewpager extends ViewPager {
    private int[] title;//背景图片的集合
    private RelativeLayout.LayoutParams params;
    private float down_y, move_y, set_y;//按下时Y的坐标,滑动时Y坐标,固定值Y得坐标
    private ImageView back_iv;//需要修改的图片
    int heigh_x;//要移动的距离


    public ItemVerticalViewpager(Context context) {
        this(context, null);
    }

    public ItemVerticalViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        setPageTransformer(false, new DefaultTransformerX());
//        页面滑动的监听  这里确定了 第1页与最后1页 具有粘性窗口
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                down_y = event.getY();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                if (getCurrentItem() == 0) {
                                    move_y = event.getY() - down_y;
                                    float xy = set_y > move_y ? set_y : move_y;
                                    heigh_x = (int) ((int) xy * 0.6);
                                    if (xy > 0) {
                                        params.setMargins(0, heigh_x, 0, 0);
                                        setLayoutParams(params);
                                        set_y = xy;
                                    }
                                }
                                if (getCurrentItem() == title.length - 1 && (down_y - event.getY()) > 0) {
                                    move_y = down_y - event.getY();
                                    float xy = set_y > move_y ? set_y : move_y;
                                    heigh_x = (int) ((int) xy * 0.5);
                                    params.setMargins(0, 0, 0, heigh_x);
                                    setLayoutParams(params);
                                    set_y = xy;
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                                if (getCurrentItem() == 0) {
                                    ValueAnimator animator = ValueAnimator.ofInt(heigh_x, 0);
                                    animator.setTarget(this);
                                    animator.setDuration(400).start();
                                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animation) {
                                            Integer value = (Integer) animation.getAnimatedValue();
                                            params.setMargins(0, value, 0, 0);
                                            setLayoutParams(params);
                                        }
                                    });
                                }
                                if (getCurrentItem() == title.length - 1 && (down_y - event.getY()) > 0) {
                                    ValueAnimator animator = ValueAnimator.ofInt(heigh_x, 0);
                                    animator.setTarget(this);
                                    animator.setDuration(400).start();
                                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animation) {
                                            Integer value = (Integer) animation.getAnimatedValue();
                                            params.setMargins(0, 0, 0, value);
                                            setLayoutParams(params);
                                        }
                                    });
                                }
                                set_y = 0;
                                break;
                        }
                        return false;
                    }
                });
            }

            @Override
            public void onPageSelected(int position) {

            }

            //滑动结束后   设置图片 为图片集合的第几张
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 2) {
                    back_iv.setBackgroundResource(title[getCurrentItem()]);
                }
            }
        });

    }


    /**
     * 对外获得需要修改的图片窗口   以及图片资源集合
     *
     * @param iv
     * @param title
     */
    public void setMyBackGound(ImageView iv, int[] title) {
        this.back_iv = iv;
        this.title = title;
    }

    /**
     * 转化成纵向移动
     *
     * @param event
     * @return
     */
    private MotionEvent swapTouchEvent(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();

        float swappedX = (event.getY() / height) * width;
        float swappedY = (event.getX() / width) * height;

        event.setLocation(swappedX, swappedY);

        return event;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        swapTouchEvent(event);
        return super.onInterceptTouchEvent(swapTouchEvent(event));
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapTouchEvent(ev));
    }


    /**
     * 纵向移动要用到的翻页变化
     */
    public class DefaultTransformerX implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View page, float position) {

            page.setTranslationX(page.getWidth() * -position);
            float yPosition = position * page.getHeight();
            page.setTranslationY(yPosition);
        }
    }

}
