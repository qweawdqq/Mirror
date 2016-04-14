package com.example.dllo.mirror.activityworks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.system.ErrnoException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.dllo.mirror.R;
import com.example.dllo.mirror.adapterworks.AddressActivityAdapter;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.bean.AllAddressBean;
import com.example.dllo.mirror.net.NetConnectionStatus;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;

/**
 * Created by dllo on 16/4/11.
 */
public class OrderActivity extends BaseActivity implements View.OnClickListener, StaticEntityInterface {

    private TextView etAddressee;
    private TextView tvAddress;
    private ImageView ivGlasses;
    private TextView tvGlassesName, tvGlassesContent, tvPrice;
    private Button btnOrder;
    private AllAddressBean bean;
    private Handler handler;
    private int pos;
    private ImageButton btnback;

    @Override
    protected int initLayout() {
        return R.layout.activity_order;
    }

    @Override
    protected void initView() {
        etAddressee = bindView(R.id.order_tv_addressee);
        tvAddress = bindView(R.id.order_tv_address);
        ivGlasses = bindView(R.id.order_glasses_image);
        tvGlassesName = bindView(R.id.order_glasses_name);
        tvGlassesContent = bindView(R.id.order_glasses_content);
        tvPrice = bindView(R.id.order_glasses_price);
        btnOrder = bindView(R.id.order_btn_order);
        btnback = bindView(R.id.order_btn_back);
    }

    @Override
    protected void initData() {
        setBtnListener();
        addAddress();
        setMyHandler();

    }

    private void setBtnListener() {
        tvAddress.setOnClickListener(this);
        btnback.setOnClickListener(this);
    }


    private void setMyHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                pos = msg.arg1;
                AllAddressBean bean = (AllAddressBean) msg.obj;
                etAddressee.setText("收件人: " + bean.getData().getList().get(pos).getUsername() + "\n"
                        + "地址: " + bean.getData().getList().get(pos).getAddr_info() + "\n"
                        + bean.getData().getList().get(pos).getCellphone());

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_tv_address:
                Intent intentToAddress = new Intent(OrderActivity.this, AddressActivity.class);
                startActivityForResult(intentToAddress, 100);

                break;
            case R.id.order_btn_order:

                break;
            case R.id.order_btn_back:
                this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        addAddress();

    }

    private void addAddress() {
        OkHttpNetHelper httpNetHelper = new OkHttpNetHelper();

        FormEncodingBuilder builder = new FormEncodingBuilder();
        // 解析数据
        builder.add("token", "fa9d21aa6de9639d76ac1f31d7519326");
        builder.add("device_type", "3");


        httpNetHelper.getPostDataFromNet(builder, ADDRESS_LIST, new NetListener() {
            @Override
            public void getSuccess(String s) {

                Gson gson = new Gson();
                bean = gson.fromJson(s.toString(), AllAddressBean.class);

                String address = NetConnectionStatus.getSharedPrefer(OrderActivity.this, "myAddress");
                for (int i = 0; i < bean.getData().getList().size(); i++) {
                    if (address.equals(bean.getData().getList().get(i).getAddr_id())) {
                        Message msg = Message.obtain();
                        msg.obj = bean;
                        msg.arg1 = i;
                        handler.sendMessage(msg);
                        return;
                    }
                }

            }

            @Override
            public void getFail(String s) {
            }
        });
    }

}
