package com.example.dllo.mirror.fragmentworks;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.adapterworks.ListFragmentAdapter;
import com.example.dllo.mirror.baseworks.BaseFragment;
import com.example.dllo.mirror.bean.GoodsListBean;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/30.
 */
public class ListFragment extends BaseFragment implements View.OnClickListener {
private LinearLayout layout;
    private RecyclerView recyclerView;
    private ListFragmentAdapter adapter;
    GoodsListBean bean;


    @Override
    protected int initLayout() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) getView().findViewById(R.id.fragment_list_recyclerview);
        layout = bindView(R.id.layout_layout);
    }

    @Override
    protected void initData() {

//        GridLayoutManager gm = new GridLayoutManager(getActivity(), 1);
//        gm.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(gm);

        // recyclerView管理者   横向
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(lm);

        // TODO 假数据
        ArrayList<String> data = new ArrayList<>();
        data.add("aaa");
        data.add("sss");
        data.add("ddd");
        data.add("fff");
        data.add("ggg");

        
        adapter = new ListFragmentAdapter();

        // 添加数据的方法  输入的是一个集合
        adapter.addData(data);

        recyclerView.setAdapter(adapter);
        layout.setOnClickListener(this);
    }

    public void setBroadReveice() {
        Intent intent = new Intent();
        intent.setAction("activityworks");
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        setBroadReveice();
    }
}
