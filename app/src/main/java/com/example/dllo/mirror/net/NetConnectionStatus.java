package com.example.dllo.mirror.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * 判断网络连接状态的工具类
 * Created by jialiang on 16/4/1.
 */
public class NetConnectionStatus {



    public static boolean getNetContectStatus(Context context) {
        ConnectivityManager manager;
        manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        获取代表联网状态的netWorkInfo对象
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null) {
            Toast.makeText(context, "网络未连接", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}
