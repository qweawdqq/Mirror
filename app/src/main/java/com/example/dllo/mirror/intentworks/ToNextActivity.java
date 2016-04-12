package com.example.dllo.mirror.intentworks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.activityworks.LoginActivity;
import com.example.dllo.mirror.activityworks.MainActivity;
import com.example.dllo.mirror.activityworks.SignActivity;
import com.example.dllo.mirror.activityworks.VideoActivity;

/**
 * Created by dllo on 16/3/31.
 * 跳转页面的工具类
 */
public class ToNextActivity implements ToNextListener {

    /**
     * @param key     表示即将要进行的任务  如:TO_SIGN_ACTIVITY 就是 跳转到 SignActivity
     * @param context 上下文  在那一页 就传哪一页的
     * @param finish  写trun 跳转完则  finish页面   false则不 结束页面
     * @param bundle  你的跳转传值 写null 表示没有
     */
    public static void toNextActivity(String key, Context context, boolean finish, Bundle bundle) {
        Intent intent = new Intent();
        switch (key) {
            case TO_SIGN_ACTIVITY:
                intent.setClass(context, SignActivity.class);
                setMyIntent(intent, context, finish, bundle);
                break;
            case TO_MAIN_ACTYVITY:
                intent.setClass(context, MainActivity.class);
                setMyIntent(intent,context,finish,bundle);
                break;
            case TO_VIEDO_ACTIVITY:
                intent.setClass(context, VideoActivity.class);
                setMyIntent(intent,context,finish,bundle);
                break;
        }
    }

    /**
     *开始跳转的方法
     */
    private static void setMyIntent(Intent intent, Context context, boolean finish, Bundle bundle) {
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
        if (finish) {
            ((Activity) context).finish();
        }
    }
}
