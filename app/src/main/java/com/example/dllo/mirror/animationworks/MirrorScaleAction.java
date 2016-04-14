package com.example.dllo.mirror.animationworks;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.example.dllo.mirror.interfaceworks.MirrorScaleLinister;

/**
 * Created by jialiang on 16/3/29.
 * 设置Mirror的缩放动画
 */
public class MirrorScaleAction extends ScaleAnimation implements MirrorScaleLinister {
    private View view;
    private MirrorScaleAction ation;
    private boolean isAnimRun = true;


    public MirrorScaleAction(Context context, AttributeSet attrs) {
        super(context, attrs);
        ation = new MirrorScaleAction(1f, 0.6f, 1f, 0.6f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        setAtion();
    }

    public MirrorScaleAction(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {
        super(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue);
    }

    public void setView(View view) {
        this.view = view;
    }


    /**
     * 设置动画的方法
     */
    private void setAtion() {
        ation.setFillAfter(false);
        ation.setDuration(300);
        ation.setRepeatCount(1);
        ation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isAnimRun = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isAnimRun = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 设置mirror的动画
     */
    @Override
    public void setMirrorScaleLinister() {
        if (isAnimRun){
            view.startAnimation(ation);
        }
    }
}


