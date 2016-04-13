package com.example.dllo.mirror.activityworks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.bean.LoginEntity;
import com.example.dllo.mirror.intentworks.ToNextActivity;
import com.example.dllo.mirror.intentworks.ToNextListener;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jialiang on 16/3/30.
 * 登录的界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, ToNextListener, StaticEntityInterface {
    private ImageButton back;
    private Button create, land;
    private EditText getNum, getPassWord;
    private ImageButton weixin, weibo;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        back = bindView(R.id.landing_btn_back);
        create = bindView(R.id.landing_btn_create);
        land = bindView(R.id.landing_btn_land);
        getNum = bindView(R.id.login_et_phoneNum);
        getPassWord = bindView(R.id.login_et_prossed);
        weixin = bindView(R.id.login_img_weixin);
        weibo = bindView(R.id.login_img_weibo);
    }

    @Override
    protected void initData() {
        back.setOnClickListener(this);
        create.setOnClickListener(this);
        land.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.landing_btn_back:
                this.finish();
                break;
            case R.id.landing_btn_create:
                ToNextActivity.toNextActivity(TO_SIGN_ACTIVITY, LoginActivity.this, false, null);
                break;
            case R.id.landing_btn_land:
                getEtString();
                break;
            case R.id.login_img_weibo:
                break;
            case R.id.login_img_weixin:
                break;
        }
    }

    /**
     * 得到edittext的 账号和密码
     * 加入到网络请求中
     */
    private void getEtString() {
        if (getPassWord.getText().length() > 0 && getNum.getText().length() > 0) {
            String num = getNum.getText().toString();
            String passWord = getPassWord.getText().toString();
            jsonFromNet(USER_LOGIN, num, passWord);
        } else {
            Toast.makeText(LoginActivity.this, "请出入正确号码", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * 登录的网络请求
     *
     * @param url      网址
     * @param num      账号
     * @param password 密码
     */
    private void jsonFromNet(String url, String num, String password) {
        OkHttpNetHelper helper = new OkHttpNetHelper();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add(PHONE_NUMBER, num);
        builder.add(PASSWORD, password);
        helper.getPostDataFromNet(builder, url, new NetListener() {
            @Override
            public void getSuccess(String s) {
                try {
                    netSuccess(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void getFail(String s) {

            }
        });
    }

    /**
     * 成功进行网络请求时候 得到的具体情况  如:用户信息  错误原因
     *
     * @param s 网络请求成功时候的String
     * @throws JSONException
     */
    private void netSuccess(String s) throws JSONException {
        final JSONObject json = new JSONObject(s);
        String result = json.getString("result");
        if (result.equals("1")) {
            Gson gson = new Gson();
            // TODO 数据类不对吧   数据类注了能打出来String s
            LoginEntity entity = gson.fromJson(s, LoginEntity.class);
        } else {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Toast.makeText(LoginActivity.this, json.getString("msg").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

}
