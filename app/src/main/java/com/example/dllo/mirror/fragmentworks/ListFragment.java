package com.example.dllo.mirror.fragmentworks;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

    @Override
    protected int initLayout() {
        return R.layout.fragment_list;
    }
 
    @Override
    protected void initView() {
        recyclerView = (RecyclerView) getView().findViewById(R.id.fragment_list_recyclerview);
    }

    @Override
    protected void initData() {

//        GridLayoutManager gm = new GridLayoutManager(getActivity(), 1);
//        gm.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(gm);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(lm);

        ArrayList<String> data = new ArrayList<>();
        data.add("aaa");
        data.add("sss");
        data.add("ddd");
        data.add("fff");
        data.add("ggg");

        adapter = new ListFragmentAdapter();

        adapter.addData(data);

        recyclerView.setAdapter(adapter);
    }
}
