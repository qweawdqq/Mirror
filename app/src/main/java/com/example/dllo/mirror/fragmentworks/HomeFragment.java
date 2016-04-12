package com.example.dllo.mirror.fragmentworks;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.adapterworks.HomeFragmentAdapter;
import com.example.dllo.mirror.allviewworks.VerticalViewPager;
import com.example.dllo.mirror.baseworks.BaseFragment;
import com.example.dllo.mirror.bean.MenuFragmentBean;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JIALIANG on 16/3/29.
 * 主页的fragment
 */
public class HomeFragment extends BaseFragment implements StaticEntityInterface {
    private VerticalViewPager viewPager;
    private HomeFragmentAdapter adapter;
    private float startY;
    private int viewpagerPosition;
    private Handler handler;
    MenuFragmentBean t;

    @Override
    protected int initLayout() {
        return R.layout.fragment_home_viewpager;
    }

    @Override
    protected void initView() {
        viewPager = bindView(R.id.fragment_home_viewpager);

    }

    @Override
    protected void initData() {
        getData();

        adapter = new HomeFragmentAdapter(getActivity().getSupportFragmentManager());

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                ArrayList<Fragment> list = (ArrayList<Fragment>) msg.obj;
                Log.e("传过来东西了吗", "" + list.size());
                adapter.addData(list);
//                Bundle bundle = getArguments();
//                String pos = bundle.getString("页数");
//                Log.d("测试结果", pos);
//                viewPager.setCurrentItem(pos);
                viewPager.setAdapter(adapter);
                return false;
            }
        });

        passDataItem();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("----", "ondestroy");
    }

    public void setBroadReveice(int x) {
        Intent intent = new Intent();
        intent.setAction("activityworks");
        getActivity().sendBroadcast(intent);
    }

    public void getData() {

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
                t = new Gson().fromJson(s.toString(), MenuFragmentBean.class);
                Log.d("size", t.getData().getList().size() + "");
                MenuFragmentBean.DataBean b = t.getData();
                List<MenuFragmentBean.DataBean.ListBean> l = b.getList();
                ArrayList<Fragment> fragment = new ArrayList<Fragment>();

                for (int i = 0; i < l.size(); i++) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("title", l.get(i));
                    String type = l.get(i).getType();
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

    /**
     * 传递当前页面是viewPager 的第几个页面
     */
    public void passDataItem() {

        Log.i("刚进来没滑动时第几页", viewPager.getCurrentItem() + 1 + "");
        // 监听ViewPager ,重载方法,可以的出当前在第几行
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // 被选中在第几行
            @Override
            public void onPageSelected(int position) {
                viewPager.getCurrentItem();
                Log.i("第几页", viewPager.getCurrentItem() + 1 + "");

            }

            // 改变状态是调用
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}