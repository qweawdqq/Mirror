package com.example.dllo.mirror.fragmentworks;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.adapterworks.HomeFragmentAdapter;
import com.example.dllo.mirror.allviewworks.VerticalViewPager;
import com.example.dllo.mirror.baseworks.BaseFragment;
import com.example.dllo.mirror.bean.GoodsListBean;
import com.example.dllo.mirror.net.NetListener;
import com.example.dllo.mirror.net.OkHttpNetHelper;
import com.example.dllo.mirror.normalstatic.StaticEntityInterface;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JIALIANG on 16/3/29.
 * 主页的fragment
 */
public class HomeFragment extends BaseFragment implements StaticEntityInterface {
    //    private Button btn_home;
    private VerticalViewPager viewPager;
    private HomeFragmentAdapter adapter;
    private List<Fragment> fragments;
    private float startY;
    private int viewpagerPosition;

    GoodsListBean data;

    @Override
    protected int initLayout() {
        return R.layout.fragment_home_viewpager;
    }

    @Override
    protected void initView() {

        viewPager = bindView(R.id.fragment_home_viewpager);
//        fragments = new ArrayList<>();
//        fragments.add(new ListFragment());
//        fragments.add(new ListFragment());
//        fragments.add(new ListFragment());

    }

    @Override
    protected void initData() {

        getData();

        FragmentManager fm = getActivity().getSupportFragmentManager();
        adapter = new HomeFragmentAdapter(fm, fragments);

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //onPageSelected选中了.......
            @Override
            public void onPageSelected(int position) {

            }

            //此方法是在状态改变的时候调用，其中arg0这个参数
            //有三种状态（0，1，2）。arg0 ==1的时辰默示正在滑动，arg0==2的时辰默示滑动完毕了，arg0==0的时辰默示什么都没做。
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    public void setBroadReveice() {
        Intent intent = new Intent();
        intent.setAction("activityworks");
        getActivity().sendBroadcast(intent);
    }

    public void getData(){
        // 获取网络数据
        OkHttpNetHelper helper = new OkHttpNetHelper();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add(DEVICE_TYPE, "2");
        builder.add(PAGE,"");
        builder.add(LAST_TIME,"");
        builder.add(GOODS_LIST_CATEGORY_ID,"");
        builder.add(GOODS_LIST_VERSION,"");
        helper.getPostDataFromNet(builder, GOODS_LIST, new NetListener() {
            @Override
            public void getSuccess(final String s) {

                Log.i("+++",s);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        data = new Gson().fromJson(s.toString(),GoodsListBean.class);
                        fragments = new ArrayList<Fragment>();
                        for (int i = 0; i < data.getData().getList().size();i++){
                            fragments.add(new ListFragment());
                        }

                    }
                });
            }

            @Override
            public void getFail(String s) {

            }
        });
    }


}