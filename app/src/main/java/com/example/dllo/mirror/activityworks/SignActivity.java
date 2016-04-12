package com.example.dllo.mirror.activityworks;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.intentworks.ToNextListener;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.FormEncodingBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dllo on 16/3/31.
 * 创建账号的界面
 */
public class SignActivity extends BaseActivity implements ToNextListener, StaticEntityInterface, View.OnClickListener {
    private Button sendMessage;
    private EditText phoneNum, getCode, password;
    private Button creatId;

    @Override
    protected int initLayout() {
        return R.layout.activity_sign;
    }

    @Override
    protected void initView() {
        sendMessage = bindView(R.id.sign_tv_sendmessage);
        phoneNum = bindView(R.id.sign_et_phoneNum);
        getCode = bindView(R.id.sign_et_getCode);
        password = bindView(R.id.sign_et_password);
        creatId = bindView(R.id.sign_tv_creatID);
    }

    @Override
    protected void initData() {

        sendMessage.setOnClickListener(this);
        creatId.setOnClickListener(this);
    }


    //editview 的到的文字
    private void getPhoneNum() {
        if (phoneNum != null && phoneNum.getText().length() == 11) {
            String getPhoneNum = phoneNum.getText().toString();
            sendMessageToNet(getPhoneNum);
        } else {
            Toast.makeText(SignActivity.this, "请输入正确手机号码", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 注册账号的网络请求
     *
     * @param num      电话号码
     * @param code     验证码
     * @param password 密码
     */
    private void createMyId(String num, String code, String password) {

        OkHttpNetHelper helper = new OkHttpNetHelper();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add(PHONE_NUMBER, num);
        builder.add(NUMBER, code);
        builder.add(PASSWORD, password);
        helper.getPostDataFromNet(builder, USER_REG, new NetListener() {
            @Override
            public void getSuccess(String s) {
                setJsonObject(s);
            }

            @Override
            public void getFail(String s) {
                Log.e("失败的", "signactivity注册页面");
            }
        });


    }

    /**
     * 注册成功后 的吐丝
     *
     * @param s 网络请求成功后得到的string
     */
    private void setJsonObject(String s) {
        try {
            JSONObject json = new JSONObject(s);
            String result = json.getString("result");
            if (result.equals("1")) {
                Toast.makeText(SignActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                finish();
            }
            String msg = json.getString("msg");
            if (msg.equals("验证码错误")) {
                Toast.makeText(SignActivity.this, "请出入正确验证码", Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送验证码的网络请求
     *
     * @param phoneNum et得到的你输入的文字
     */
    private void sendMessageToNet(String phoneNum) {
        OkHttpNetHelper helper = new OkHttpNetHelper();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add(PHONE_NUMBER, phoneNum);
        helper.getPostDataFromNet(builder, USER_SEND_CODE, new NetListener() {
            @Override
            public void getSuccess(String s) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SignActivity.this, "验证码以发送请注意查收", Toast.LENGTH_SHORT).show();
                }
            });
            }

            @Override
            public void getFail(String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SignActivity.this, "请检查您的网络连接", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_tv_sendmessage:
                getPhoneNum();
                break;
            case R.id.sign_tv_creatID:
                Toast.makeText(SignActivity.this, "请输入正确的用户名和密码", Toast.LENGTH_SHORT).show();
                if (phoneNum.getText() != null && getCode.getText() != null && password.getText() != null) {
                    createMyId(phoneNum.getText().toString(),
                            getCode.getText().toString(),
                            password.getText().toString());
                }

                break;
        }
    }
}
