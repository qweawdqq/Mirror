package com.example.dllo.mirror.activityworks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.squareup.okhttp.FormEncodingBuilder;

/**
 * Created by dllo on 16/4/11.
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener,StaticEntityInterface {

    private EditText etName, etPhone, etAddress;
    private Button btnAdd;
    private ImageView ivBack;

    @Override
    protected int initLayout() {
        return R.layout.activity_addaddress;
    }

    @Override
    protected void initView() {
        etName = bindView(R.id.addaddress_name);
        etPhone = bindView(R.id.addaddress_phone);
        etAddress = bindView(R.id.addaddress_address);

        ivBack = bindView(R.id.addaddress_btn_back);
        ivBack.setOnClickListener(this);
        btnAdd = bindView(R.id.addaddress_btn_add);
        btnAdd.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addaddress_btn_back:
                finish();
                break;
            case R.id.addaddress_btn_add:
                if ((etName.getText().length() == 0) || (etPhone.getText().length() == 0) ||
                        (etAddress.getText().length() == 0)) {
                    Toast.makeText(AddAddressActivity.this, "请填写信息", Toast.LENGTH_SHORT).show();
                } else {

                    OkHttpNetHelper httpNetHelper = new OkHttpNetHelper();

                    FormEncodingBuilder builder = new FormEncodingBuilder();
                    // 解析数据
                    builder.add("token", "fa9d21aa6de9639d76ac1f31d7519326");
                    builder.add("device_type", "3");
                    builder.add("username", etName.getText().toString());
                    builder.add("cellphone", etPhone.getText().toString());
                    builder.add("addr_info", etAddress.getText().toString());

                    httpNetHelper.getPostDataFromNet(builder, ADD_ADDRESS, new NetListener() {
                        @Override
                        public void getSuccess(String s) {

                        }

                        @Override
                        public void getFail(String s) {
                        }
                    });


                    Intent in = new Intent();
                    setResult(-1, in);

                    finish();
                }
                break;

        }
    }


}
