package com.example.dllo.mirror.fragmentworks;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.adapterworks.ShareFragmentAdapter;
import com.example.dllo.mirror.baseworks.BaseFragment;
import com.example.dllo.mirror.bean.MenuFragmentBean;
import com.example.dllo.mirror.bean.ShareFragmentBean;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;

/**
 * Created by dllo on 16/4/14.
 */
public class ShareFragment extends BaseFragment implements StaticEntityInterface {

    private LinearLayout lineShare;
    private TextView tvMenu;
    private Bundle bundle, bundle1;
    private MenuFragmentBean.DataBean.ListBean bean;
    private ShareFragmentBean data;
    private RecyclerView recyclerView;
    private int atItem;// 是从MainActivity 中传过来的现在的ViewPager所在的位置
    private ShareFragmentAdapter adapter;

    @Override
    protected int initLayout() {
        return R.layout.fragment_share;
    }

    @Override
    protected void initView() {
        tvMenu = bindView(R.id.fragment_share_title);
        lineShare = bindView(R.id.fragment_share_click);
        recyclerView = bindView(R.id.fragment_share_recyclerview);
    }

    @Override
    protected void initData() {

        // 接收Bundle 从AllFragment 的上级 >> MianActivity 传递过来的数据
        bundle = getArguments();
        bean = bundle.getParcelable("title");
        tvMenu.setText(bean.getTitle());
        atItem = bundle.getInt("titleAtWhatItem");

        // 网络请求数据的自定义方法
        jsonFromNet();

        // 设置上面标题的监听,点击后跳转到菜单页
        setOnClick();
    }

    /**
     * 获取网络请求的数据
     */
    private void jsonFromNet() {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add(TOKEN, "");
        builder.add(DEVICE_TYPE, "2");
        builder.add(STORY_ID, "");
        builder.add(VERSION, "1.0.1");

        OkHttpNetHelper helper = new OkHttpNetHelper();
        helper.getPostDataFromNet(builder, STORY_LIST, new NetListener() {
            @Override
            public void getSuccess(String s) {
                data = new Gson().fromJson(s, ShareFragmentBean.class);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setRecyclerFromNet();
                    }
                });
            }

            @Override
            public void getFail(String s) {

            }
        });
    }

    /**
     * 适配器与recyclerView绑定,以及传递解析后的数据到适配器中
     */
    private void setRecyclerFromNet() {
        adapter = new ShareFragmentAdapter();
        adapter.addData(data, getContext());
        recyclerView.setAdapter(adapter);
    }

    /**
     * 设置上面标题的监听,点击后跳转到菜单页
     */
    private void setOnClick() {
        lineShare.setOnClickListener(new View.OnClickListener() {
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
