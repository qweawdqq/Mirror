package com.example.dllo.mirror.interfaceworks;

import com.example.dllo.mirror.allviewworks.ObservableScrollView;

/**
 * Created by dllo on 16/4/1.
 */
public interface ScrollViewListener {
    void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
}
