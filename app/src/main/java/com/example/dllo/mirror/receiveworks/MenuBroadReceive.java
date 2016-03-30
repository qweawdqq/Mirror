package com.example.dllo.mirror.receiveworks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dllo.mirror.interfaceworks.AllReceiveListener;

/**
 * Created by jialiang on 16/3/30.
 * 菜单广播
 */
public class MenuBroadReceive extends BroadcastReceiver {
    private AllReceiveListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (listener != null) {
            listener.setAllReceiveListener(context, intent);
        }

    }

    public void setMyListener(AllReceiveListener listener) {
        this.listener = listener;
    }
}
