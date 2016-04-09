package com.example.dllo.mirror.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.dllo.mirror.intentworks.ToNextListener;

/**
 * 判断网络连接状态的工具类
 * Created by jialiang on 16/4/1.
 */
public class NetConnectionStatus implements ToNextListener {


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

    /**
     * 将文件写入到轻量级数据库中
     *
     * @param context
     * @param key     写入文件时候的key
     * @param value   写入文件的string
     */
    public static void saveSharedPrefer(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 得到轻量级缓存的数据库 中的值  用于网络断开时显示
     *
     * @param context
     * @param key
     * @return
     */
    public static String getSharedPrefer(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String value = preferences.getString(key, "");
        return value;
    }
}
