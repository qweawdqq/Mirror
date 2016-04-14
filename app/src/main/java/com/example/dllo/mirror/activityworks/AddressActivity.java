package com.example.dllo.mirror.activityworks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.adapterworks.AddressActivityAdapter;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.bean.AllAddressBean;
import com.example.dllo.mirror.interfaceworks.ContentViewLinister;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;

/**
 * Created by dllo on 16/4/11.
 */
public class AddressActivity extends BaseActivity implements View.OnClickListener, StaticEntityInterface {

    private ImageButton ivBack;
    private TextView tvAdd;
    private ListView listView;
    private AddressActivityAdapter adapter;
    private Handler handler;
    private AllAddressBean bean;
    private DelReceiver delReceiver;
    private ChangeReceiver changeReceiver;
    private FinishReceiver finishReceiver;

    @Override
    protected int initLayout() {
        return R.layout.activity_address;
    }

    @Override
    protected void initView() {
        ivBack = bindView(R.id.address_btn_back);
        tvAdd = bindView(R.id.address_tv_toAdd);
        listView = bindView(R.id.address_listview);
        ivBack.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        addAddress();

        delReceiver = new DelReceiver();
        IntentFilter delIntentFilter = new IntentFilter();
        delIntentFilter.addAction("com.example.dllo.mirror.DEL_BROADCAST");
        registerReceiver(delReceiver, delIntentFilter);


        changeReceiver = new ChangeReceiver();
        IntentFilter changeIntentFilter = new IntentFilter();
        changeIntentFilter.addAction("com.example.dllo.mirror.CHANGE_BROADCAST");
        registerReceiver(changeReceiver, changeIntentFilter);

        finishReceiver = new FinishReceiver();
        IntentFilter finishIntentFilter = new IntentFilter();
        finishIntentFilter.addAction("com.example.dllo.mirror.FINISH_BROADCAST");
        registerReceiver(finishReceiver, finishIntentFilter);

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

                Message msg = Message.obtain();
                msg.obj = bean;
                handler.sendMessage(msg);
            }

            @Override
            public void getFail(String s) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                AllAddressBean bean = (AllAddressBean) msg.obj;

                adapter = new AddressActivityAdapter(bean, AddressActivity.this);
                listView.setAdapter(adapter);


                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_btn_back:

                finish();
                break;
            case R.id.address_tv_toAdd:

                Intent intentToAdd = new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivityForResult(intentToAdd, 101);

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        addAddress();
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(delReceiver);
        unregisterReceiver(changeReceiver);
        unregisterReceiver(finishReceiver);
        super.onDestroy();

    }


    // 接收删除的广播
    class DelReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            addAddress();
        }
    }

    // 接收修改的广播
    class ChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            addAddress();
        }
    }

    // 接收finish本页面的广播
    class FinishReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
          finish();
        }
    }

}
