package com.example.dllo.mirror.fragmentworks;

import android.bluetooth.BluetoothGatt;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.activityworks.MainActivity;
import com.example.dllo.mirror.adapterworks.ListFragmentAdapter;
import com.example.dllo.mirror.baseworks.BaseFragment;
import com.example.dllo.mirror.baseworks.BitMapTools;
import com.example.dllo.mirror.bean.GoodsListBean;
import com.example.dllo.mirror.bean.MenuFragmentBean;
import com.example.dllo.mirror.db.DbHelper;
import com.example.dllo.mirror.db.HomeData;
import com.example.dllo.mirror.net.NetConnectionStatus;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/30.
 */
public class ListFragment extends BaseFragment implements StaticEntityInterface {
    private RecyclerView recyclerView;
    private ListFragmentAdapter adapter;
    private LinearLayout clickLayout;
    private GoodsListBean data;
    private MenuFragmentBean.DataBean.ListBean bean;
    private TextView title, title_nest;
    private Bundle bundle, bundle1;
    private LinearLayout layout_bg;
    private int atItem;// 是从MainActivity 中传过来的现在的ViewPager所在的位置
    private boolean netStatus;

    @Override
    protected int initLayout() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView() {
        layout_bg = bindView(R.id.list_img_bg);
        title = bindView(R.id.fragment_list_title);
        title_nest = bindView(R.id.fragment_list_titleNest);
        recyclerView = bindView(R.id.fragment_list_recyclerview);
        clickLayout = bindView(R.id.fragment_list_click);
        recyclerView = (RecyclerView) getView().findViewById(R.id.fragment_list_recyclerview);

    }


    @Override
    protected void initData() {
        setRecyclerView();
        bundle = getArguments();
        bean = bundle.getParcelable("title");
        title.setText(bean.getTitle());
        atItem = bundle.getInt("titleAtWhatItem");
        getNetStatus();

        setClickLayout();

    }

    private void getNetStatus() {
        netStatus = NetConnectionStatus.getNetContectStatus(getActivity());
        if (netStatus) {
            jsonFromNet();
        } else {
            noNetStauts();
        }
    }


    private void setRecyclerView() {
        layout_bg.setBackground(BitMapTools.readBitMap(getActivity(), R.mipmap.background));
        // recyclerView管理者   横向
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(lm);

    }

    private void noNetStauts() {
        HomeData hd =  DbHelper.getInstance(getActivity()).getNote(bean.getTitle());
        data = new Gson().fromJson(hd.getValue(), GoodsListBean.class);
        getRecycleData();
    }

    private void jsonFromNet() {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add(DEVICE_TYPE, "2");
        builder.add(PAGE, "");
        builder.add(LAST_TIME, "");
        builder.add(GOODS_LIST_CATEGORY_ID, bean.getInfo_data());
        builder.add(GOODS_LIST_VERSION, "1.0.1");
        OkHttpNetHelper helper = new OkHttpNetHelper();
        helper.getPostDataFromNet(builder, GOODS_LIST, new NetListener() {
            @Override
            public void getSuccess(String s) {

                dbGetData(s);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getRecycleData();
                    }
                });
            }

            @Override
            public void getFail(String s) {

            }
        });
    }

    private void dbGetData(String s) {
        data = new Gson().fromJson(s, GoodsListBean.class);
        Log.e("长度", data.getData().getList().get(0).getGoods_name() + "");
        HomeData hd = new HomeData();
        hd.setKey(bean.getTitle());
        hd.setValue(s);
        DbHelper.getInstance(getActivity()).addData(hd);
    }

    private void getRecycleData() {
        adapter = new ListFragmentAdapter();
        adapter.addData(data, getContext());
        recyclerView.setAdapter(adapter);
    }


    private void setClickLayout() {
        clickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuFragment menuFragment = new MenuFragment();
                bundle1 = new Bundle();
                bundle1.putInt("toMenuFragment", atItem);
                menuFragment.setArguments(bundle1);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.main_flt_layout, menuFragment).commit();
            }
        });
    }

}
