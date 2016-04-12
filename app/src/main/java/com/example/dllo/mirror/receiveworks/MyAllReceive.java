package com.example.dllo.mirror.receiveworks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;


/**
 * Created by jialiang on 16/3/30.
 * 存放所有的广播
 */
public class MyAllReceive {

    /**
     * 菜单的弹出广播
     */
    MenuBroadReceive receive;

    /**
     * @return 一个菜单的弹出的广播
     */
    public MenuBroadReceive getMenuReceive() {
        if (this.receive == null) {
            receive = new MenuBroadReceive();
            return receive;
        } else {
            return receive;
        }
    }

    /**
     * 注册广播的方法
     *
     * @param context  上下文
     * @param key      广播的ation
     * @param receiver 广播的名字
     */
    public void startReceive(Context context, String key, BroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(key);
        context.registerReceiver(receiver, filter);
    }


    /**
     * 取消广播注册
     *
     * @param context
     * @param receiver 广播的名字
     */
    public void stopReceive(Context context, BroadcastReceiver receiver) {
        context.unregisterReceiver(receiver);
    }
}
