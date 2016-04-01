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

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/30.
 */
public class ListFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private ListFragmentAdapter adapter;
    private LinearLayout clickLayout;

    @Override
    protected int initLayout() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.fragment_list_recyclerview);
        clickLayout=bindView(R.id.fragment_list_click);
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

        clickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("activityworks");
                getActivity().sendBroadcast(intent);
            }
        });
    }
}
