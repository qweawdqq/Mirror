package com.example.dllo.mirror.fragmentworks;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.adapterworks.ListFragmentAdapter;
import com.example.dllo.mirror.baseworks.BaseFragment;
import com.example.dllo.mirror.bean.GoodsListBean;
import com.example.dllo.mirror.bean.MenuFragmentBean;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;

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
    private int atItem;// 是从MainActivity 中传过来的现在的ViewPager所在的位置


    @Override
    protected int initLayout() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView() {

        title = bindView(R.id.fragment_list_title);
        title_nest = bindView(R.id.fragment_list_titleNest);
        recyclerView = bindView(R.id.fragment_list_recyclerview);
        clickLayout = bindView(R.id.fragment_list_click);
        recyclerView = (RecyclerView) getView().findViewById(R.id.fragment_list_recyclerview);

    }


    @Override
    protected void initData() {

        // recyclerView管理者   横向
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(lm);

        bundle = getArguments();
        bean = bundle.getParcelable("title");
        title.setText(bean.getTitle());
        atItem = bundle.getInt("titleAtWhatItem");

        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add(DEVICE_TYPE, "2");
        builder.add(PAGE, "");
        builder.add(LAST_TIME, "");
        builder.add(GOODS_LIST_CATEGORY_ID, bean.getInfo_data());
        builder.add(GOODS_LIST_VERSION, "1.0.0");
        OkHttpNetHelper helper = new OkHttpNetHelper();
        helper.getPostDataFromNet(builder, GOODS_LIST, new NetListener() {
            @Override
            public void getSuccess(String s) {
                data = new Gson().fromJson(s, GoodsListBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ListFragmentAdapter();
                        adapter.addData(data,getContext());
                        recyclerView.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void getFail(String s) {

            }
        });


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
