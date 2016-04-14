package com.example.dllo.mirror.fragmentworks;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.activityworks.MainActivity;
import com.example.dllo.mirror.adapterworks.MenuFragmentAdapter;
import com.example.dllo.mirror.baseworks.BaseFragment;
import com.example.dllo.mirror.baseworks.BitMapTools;
import com.example.dllo.mirror.bean.MenuFragmentBean;
import com.example.dllo.mirror.db.DbHelper;
import com.example.dllo.mirror.db.HomeData;
import com.example.dllo.mirror.eventbusclass.PassTitleToMenu;
import com.example.dllo.mirror.net.NetConnectionStatus;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.MenuPassDataEvent;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import de.greenrobot.event.EventBus;

/**
 * Created by dllo on 16/3/29.
 */
public class MenuFragment extends BaseFragment implements StaticEntityInterface, View.OnClickListener {

    private ListView listView;
    private MenuFragmentAdapter adapter;
    MenuFragmentBean bean;
    private AutoLinearLayout layout;
    private TextView tvTitle;
    private ImageView ivLine;
    private boolean netStauts;
    AutoLinearLayout backTitle, exit;
    LinearLayout fragment_menu_line;
    private Intent intent;
    private int i, k = 333;


    @Override
    protected int initLayout() {
        return R.layout.fragment_menu;
    }

    @Override
    protected void initView() {
        layout = bindView(R.id.menu_layout);
        fragment_menu_line = bindView(R.id.fragment_menu_line);
        listView = bindView(R.id.fragment_menu_listview);
        backTitle = bindView(R.id.fragment_menu_back_title);
        exit = bindView(R.id.fragment_menu_exit);
        tvTitle = bindView(R.id.fragment_menu_tvfirst_tab);
        ivLine = bindView(R.id.fragment_menu_first_iv);
    }


    @Override
    protected void initData() {
        initialFragment();

        jsonFromNetStauts();
        backTitle.setOnClickListener(this);
        exit.setOnClickListener(this);

    }

    private void jsonFromNetStauts() {
        if (netStauts) {
            jsonFromNet();
        } else {
            HomeData hd = DbHelper.getInstance(getActivity()).getNote("toMenuFragment");
            setAdapter(hd.getValue());
        }

    }

    private void initialFragment() {
        netStauts = NetConnectionStatus.getNetContectStatus(getActivity());
        layout.setBackground(BitMapTools.readBitMap(getActivity(), R.mipmap.background));
        // 注册eventbus
//        EventBus.getDefault().register(this);

        Bundle bundle = getArguments();
        i = bundle.getInt("toMenuFragment");
        Log.i("值", i + "");
        if (i == 4) {
            ivLine.setVisibility(View.VISIBLE);
            tvTitle.setAlpha(1);
        } else if (i == 5) {
            ivLine.setVisibility(View.VISIBLE);
            tvTitle.setAlpha(1);
        }
    }

    private void jsonFromNet() {
        // ******* 请求网络数据并解析 ******* //
        OkHttpNetHelper httpNetHelper = new OkHttpNetHelper();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add(TOKEN, "");
        builder.add(DEVICE_TYPE, "2");

        httpNetHelper.getPostDataFromNet(builder, MENU_LIST, new NetListener() {
            @Override
            public void getSuccess(final String s) {
                Log.d("数据", s);
                dbAddData(s);
                // 指出Fragment 的寄主activity, 更新UI需要主线程来更新,所以复写Activity 的这个方法更新,
                // 另一个方法是用Handler的对象,用handleMessage回调函数调用更新界面显示的函数
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter(s);
                    }
                });
            }

            @Override
            public void getFail(String s) {

            }
        });
    }

    private void setAdapter(String s) {
        bean = new Gson().fromJson(s, MenuFragmentBean.class);
        adapter = new MenuFragmentAdapter(bean, i); // 通过构造方法将值viewpager的值传到适配器中,设置显示
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void dbAddData(String s) {
        HomeData homeData = new HomeData();
        homeData.setKey("toMenuFragment");
        homeData.setValue(s);
        DbHelper.getInstance(getActivity()).addData(homeData);
    }

    // ********************************//

    @Override
    public void onResume() {
        setAnimation(fragment_menu_line);
        super.onResume();
    }


    //    设置动画
    private void setAnimation(View tv) {
        ObjectAnimator.ofFloat(tv, "translationX", 0F, 60F).setDuration(400).start();
        ObjectAnimator.ofFloat(tv, "translationY", 0F, 40F).setDuration(400).start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_menu_back_title:
                intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("positon", 0);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.fragment_menu_exit:
                Toast.makeText(getActivity(), "退出了应用", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 反注册EventBus
//        EventBus.getDefault().unregister(this);
    }
}
