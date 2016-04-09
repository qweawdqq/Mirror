package com.example.dllo.mirror.allviewworks;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by dllo on 16/4/6.
 */
public class HeadRecycleView extends LinearLayout{
    public HeadRecycleView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public HeadRecycleView(Context context) {
       this(context,null);
    }

    public HeadRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
