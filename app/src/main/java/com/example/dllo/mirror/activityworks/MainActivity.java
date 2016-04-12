package com.example.dllo.mirror.activityworks;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.adapterworks.HomeFragmentAdapter;
import com.example.dllo.mirror.allviewworks.VerticalViewPager;
import com.example.dllo.mirror.animationworks.MirrorScaleAtion;
import com.example.dllo.mirror.baseworks.BaseActivity;
import com.example.dllo.mirror.bean.MenuFragmentBean;
import com.example.dllo.mirror.db.DaoSingleton;
import com.example.dllo.mirror.db.HomeData;
import com.example.dllo.mirror.db.HomeDataDao;
import com.example.dllo.mirror.eventbusclass.PassTitleToMenu;
import com.example.dllo.mirror.fragmentworks.AllFragment;
import com.example.dllo.mirror.fragmentworks.BuyFragment;
import com.example.dllo.mirror.fragmentworks.ListFragment;
import com.example.dllo.mirror.interfaceworks.AllReceiveListener;
import com.example.dllo.mirror.net.NetConnectionStatus;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.example.dllo.mirror.receiveworks.MyAllReceive;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity implements View.OnClickListener, StaticEntityInterface {
    private ImageView mirror;
    private TextView land;
    private MirrorScaleAtion action;

    //
    private VerticalViewPager viewPager;
    private HomeFragmentAdapter adapter;
    private Handler handler;
    MenuFragmentBean bean;

    private HomeData homeData;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mirror = bindView(R.id.main_iv_mirror);
        land = bindView(R.id.main_iv_land);
        viewPager = bindView(R.id.fragment_home_viewpager);
    }

    @Override
    protected void initData() {

        action = new MirrorScaleAtion(this, null);
        mirror.setOnClickListener(this);
        land.setOnClickListener(this);

        // 接收从MenuFragment中传过来的 点击 菜单行布局 的 position, 让viewPager滑动到相对应的位置
        Intent intent = getIntent();
        final int pos = intent.getIntExtra("position", 0);

        getNetData();
        adapter = new HomeFragmentAdapter(getSupportFragmentManager());

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                ArrayList<Fragment> list = (ArrayList<Fragment>) msg.obj;
                Log.e("传过来东西了吗", "" + list.size());
                adapter.addData(list);
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(pos);
                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_iv_mirror:
                //mirror 缩放动画
                action.setView(mirror);
                action.setMirrorScaleLinister();
                break;
            case R.id.main_iv_land:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }

    }

    public void getNetData() {

        // 获取网络数据
        OkHttpNetHelper helper = new OkHttpNetHelper();

        // 获取菜单页的标签的集合的大小来确定ViewPager 能滑动的页数
        // 并且设置标签内容到MenuFragment 的Title 上
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        formEncodingBuilder.add(TOKEN, "");
        formEncodingBuilder.add(DEVICE_TYPE, "2");
        helper.getPostDataFromNet(formEncodingBuilder, MENU_LIST, new NetListener() {
            @Override
            public void getSuccess(String s) {
                bean = new Gson().fromJson(s.toString(), MenuFragmentBean.class);
//                Log.d("size", bean.getData().getList().size() + "");
                List<MenuFragmentBean.DataBean.ListBean> list = bean.getData().getList();

//                // 有网运行时加入到数据库中,到网络断开在运行程序时依然有数据显示
//                List<String> titles = new ArrayList<String>();
//                for (int i = 0; i < list.size(); i++) {
//                    titles.add(bean.getData().getList().get(i).getTitle());
//                }
//                HomeDataDao dataDao = DaoSingleton.getInstance().getHomeDataDao();
//                homeData = new HomeData(null,titles,null,null,null,null);
                ArrayList<Fragment> fragment = new ArrayList<Fragment>();
                for (int i = 0; i < list.size(); i++) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("title", list.get(i));
                    bundle.putInt("titleAtWhatItem", i);
//                    bundle.putString("titleNetData", bean.toString());
                    String type = list.get(i).getType();
                    // 根据type判断加载不同的fragment, 6 是 全部分类, 3 是两种眼镜的种类的列表, 4 是购物车
                    switch (type) {
                        case "6":
                            AllFragment af = new AllFragment();
                            af.setArguments(bundle);
                            fragment.add(af);
                            break;
                        case "3":
                            ListFragment lf = new ListFragment();
                            lf.setArguments(bundle);
                            fragment.add(lf);
                            break;
                        case "4":
                            BuyFragment bf = new BuyFragment();
                            bf.setArguments(bundle);
                            fragment.add(bf);
                            break;

                    }

                }
                Message message = Message.obtain();
                message.obj = fragment;
                handler.sendMessage(message);
            }

            @Override
            public void getFail(String s) {

            }
        });
    }


}
