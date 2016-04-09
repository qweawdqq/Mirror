package com.example.dllo.mirror.fragmentworks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.baseworks.BaseFragment;
import com.example.dllo.mirror.bean.MenuFragmentBean;

/**
 * Created by dllo on 16/4/6.
 */
public class BuyFragment extends BaseFragment {

    private TextView titleTv;
    private MenuFragmentBean.DataBean.ListBean bean;
    private LinearLayout buyLine;
    private Bundle bundle,bundle1;
    private int atItem;// 是从MainActivity 中传过来的现在的ViewPager所在的位置

    @Override
    protected int initLayout() {
        return R.layout.fragment_buy;
    }

    @Override
    protected void initView() {
        titleTv = bindView(R.id.fragment_buy_title);
        buyLine = bindView(R.id.fragment_buy_click);
    }

    @Override
    protected void initData() {
        bundle = getArguments();
        bean = bundle.getParcelable("title");
        titleTv.setText(bean.getTitle());
        atItem = bundle.getInt("titleAtWhatItem");

        buyLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuFragment menuFragment = new MenuFragment();
                bundle1 = new Bundle();
                bundle1.putInt("toMenuFragment", atItem);
                menuFragment.setArguments(bundle1);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.main_flt_layout,menuFragment).commit();
            }
        });
    }

}
