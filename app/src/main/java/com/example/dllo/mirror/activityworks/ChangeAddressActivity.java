package com.example.dllo.mirror.activityworks;

import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.bean.AllAddressBean;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;

/**
 * Created by dllo on 16/4/12.
 */
public class ChangeAddressActivity extends BaseActivity implements View.OnClickListener, StaticEntityInterface {

    private EditText etName, etPhone, etAddress;
    private Button btnChange;
    private ImageView ivBack;
    private String id;

    @Override
    protected int initLayout() {
        return R.layout.activity_changeaddress;
    }

    @Override
    protected void initView() {
        etName = bindView(R.id.changeaddress_name);
        etPhone = bindView(R.id.changeaddress_phone);
        etAddress = bindView(R.id.changeaddress_address);

        ivBack = bindView(R.id.changeaddress_btn_back);
        ivBack.setOnClickListener(this);
        btnChange = bindView(R.id.changeaddress_btn_change);
        btnChange.setOnClickListener(this);

        Intent intent = getIntent();
        id = intent.getExtras().get("id").toString();
        etName.setText(intent.getExtras().get("name").toString());
        etPhone.setText(intent.getExtras().get("phone").toString());
        etAddress.setText(intent.getExtras().get("address").toString());

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeaddress_btn_back:
                finish();
                break;
            case R.id.changeaddress_btn_change:
                OkHttpNetHelper httpNetHelper = new OkHttpNetHelper();

                FormEncodingBuilder builder = new FormEncodingBuilder();
                // 解析数据
                builder.add("token", "fa9d21aa6de9639d76ac1f31d7519326");
                builder.add("device_type", "3");
                builder.add("addr_id", id);
                builder.add("username", etName.getText().toString());
                builder.add("cellphone", etPhone.getText().toString());
                builder.add("addr_info", etAddress.getText().toString());

                httpNetHelper.getPostDataFromNet(builder, EDIT_ADDRESS, new NetListener() {
                    @Override
                    public void getSuccess(String s) {

                    }

                    @Override
                    public void getFail(String s) {
                    }
                });

                Intent changeBroadcast = new Intent("com.example.dllo.mirror.CHANGE_BROADCAST");
                //  发送一条广播
                sendBroadcast(changeBroadcast);
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();

                finish();
                break;
        }
    }
}
