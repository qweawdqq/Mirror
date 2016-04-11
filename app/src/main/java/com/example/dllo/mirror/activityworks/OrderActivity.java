package com.example.dllo.mirror.activityworks;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.baseworks.BaseActivity;

/**
 * Created by dllo on 16/4/11.
 */
public class OrderActivity extends BaseActivity implements View.OnClickListener {

    private EditText etAddressee;
    private TextView tvAddress;
    private ImageView ivGlasses;
    private TextView tvGlassesName, tvGlassesContent, tvPrice;
    private Button btnOrder;


    @Override
    protected int initLayout() {
        return R.layout.activity_order;
    }

    @Override
    protected void initView() {
        etAddressee = bindView(R.id.order_et_addressee);
        tvAddress = bindView(R.id.order_tv_address);
        tvAddress.setOnClickListener(this);
        ivGlasses = bindView(R.id.order_glasses_image);
        tvGlassesName = bindView(R.id.order_glasses_name);
        tvGlassesContent = bindView(R.id.order_glasses_content);
        tvPrice = bindView(R.id.order_glasses_price);
        btnOrder = bindView(R.id.order_btn_order);


    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_tv_address:
                Intent intent = new Intent(OrderActivity.this, AddressActivity.class);
                // TODO 孙健 跳转返回值
//                startActivityForResult(intent);
                startActivity(intent);

                break;
            case R.id.order_btn_order:

                break;
        }
    }
}
