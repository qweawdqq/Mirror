package com.example.dllo.mirror.fragmentworks;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.activityworks.EveryGlassesActivity;
import com.example.dllo.mirror.adapterworks.AllFragmentAdapter;
import com.example.dllo.mirror.baseworks.BaseFragment;
import com.example.dllo.mirror.bean.DailyCommandBean;
import com.example.dllo.mirror.bean.MenuFragmentBean;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;

/**
 * Created by dllo on 16/4/6.
 */
public class AllFragment extends BaseFragment implements StaticEntityInterface {

    private TextView titleTv;
    private MenuFragmentBean.DataBean.ListBean bean;
    private RecyclerView recyclerView;
    private DailyCommandBean data;
    private AllFragmentAdapter allFragmentAdapter;
    private LinearLayout allLine;
    private Bundle bundle, bundle1;
    private int atItem;// 是从MainActivity 中传过来的现在的ViewPager所在的位置

    @Override
    protected int initLayout() {
        return R.layout.fragment_all;
    }

    @Override
    protected void initView() {
        titleTv = bindView(R.id.fragment_all_title);
        recyclerView = bindView(R.id.fragment_all_recyclerview);
        allLine = bindView(R.id.fragment_all_click);
    }

    @Override
    protected void initData() {

        // recyclerView管理者   横向
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(lm);

        // 接收Bundle 从AllFragment 的上级 >> MianActivity 传递过来的数据
        bundle = getArguments();
        bean = bundle.getParcelable("title");
        titleTv.setText(bean.getTitle());
        atItem = bundle.getInt("titleAtWhatItem");

        // 通过标题的Type 参数判断,解析网络数据
        if (bean.getType().equals("6")) {
            FormEncodingBuilder builder = new FormEncodingBuilder();
            builder.add(TOKEN, "");
            builder.add(DEVICE_TYPE, "2");
            builder.add(PAGE, "");
            builder.add(LAST_TIME, "");
            OkHttpNetHelper httpNetHelper = new OkHttpNetHelper();
            httpNetHelper.getPostDataFromNet(builder, MRTJ, new NetListener() {
                @Override
                public void getSuccess(String s) {
                    data = new Gson().fromJson(s, DailyCommandBean.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            allFragmentAdapter = new AllFragmentAdapter();
                            allFragmentAdapter.addData(data,getContext());
                            recyclerView.setAdapter(allFragmentAdapter);
                        }
                    });
                }

                @Override
                public void getFail(String s) {

                }
            });
        }

        allLine.setOnClickListener(new View.OnClickListener() {
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
