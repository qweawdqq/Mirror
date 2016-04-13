package com.example.dllo.mirror.allviewworks;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ScrollView;

import com.example.dllo.mirror.interfaceworks.ScrollViewListener;

/**
 * Created by dllo on 16/4/1.
 * <p/>
 * // 自定义ScrollView
 */
public class ObservableScrollView extends ScrollView {

    private ScrollViewListener scrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (scrollViewListener != null) {
            int newY= (int) (y*0.8);
            scrollViewListener.onScrollChanged(this, x, newY, oldx, oldy);

        }
        int chanceY = y - oldy;
    }

}