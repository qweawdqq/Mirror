package com.example.dllo.mirror.receiveworks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;


/**
 * Created by jialiang on 16/3/30.
 * 存放所有的广播
 */
public class MyAllReceive {
    MenuBroadReceive receive;

    //得到指定Menu类型的成员
    public MenuBroadReceive getMenuReceive() {
        if (this.receive == null) {
            receive = new MenuBroadReceive();
            return receive;
        } else {

            return receive;
        }
    }

    //    注册方法
    public void startReceive(Context context, String key, BroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(key);
        context.registerReceiver(receiver, filter);
    }

    //取消注册方法
    public void stopReceive(Context context, BroadcastReceiver receiver) {
        context.unregisterReceiver(receiver);
    }
}
