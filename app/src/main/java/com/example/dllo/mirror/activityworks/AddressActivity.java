package com.example.dllo.mirror.activityworks;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.adapterworks.AddressActivityAdapter;
import com.example.dllo.mirror.baseworks.BaseActivity;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/11.
 */
public class AddressActivity extends BaseActivity {

    private ImageButton btnBack;
    private TextView tvAdd;
    private ListView listView;
    private AddressActivityAdapter adapter;


    @Override
    protected int initLayout() {
        return R.layout.activity_address;
    }

    @Override
    protected void initView() {
        btnBack = bindView(R.id.address_btn_back);
        tvAdd = bindView(R.id.address_tv_address);
        listView = bindView(R.id.address_listview);


    }

    @Override
    protected void initData() {

        ArrayList<String> data = new ArrayList<>();
        data.add("dhbdcn");
        data.add("dhbdcn");
        data.add("dhbdcn");
        data.add("dhbdcn");
        data.add("dhbdcn");
        data.add("dhbdcn");

        adapter = new AddressActivityAdapter(data);
        listView.setAdapter(adapter);

    }
}
