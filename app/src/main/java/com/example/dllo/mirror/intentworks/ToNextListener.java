package com.example.dllo.mirror.intentworks;

/**
 * Created by dllo on 16/3/31.
 * 跳转任务的key值
 * bundle也可以使用此key值  接收传值
 */
public interface ToNextListener {
    public final String TO_SIGN_ACTIVITY = "toSignActivity";//跳转到SignActivity
    //得到sharedPreferences  ----welcomeActivity 的
    public final String SHARED_PREFERENCES = "MySharedPreferences";
    public final String WELCOME_ACTIVITY = "welcomeactivity";
    public final String TO_MAIN_ACTYVITY = "toMainActivity";
}
