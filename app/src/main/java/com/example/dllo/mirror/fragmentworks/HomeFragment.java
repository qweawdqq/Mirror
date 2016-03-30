package com.example.dllo.mirror.fragmentworks;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.dllo.mirror.R;
import com.example.dllo.mirror.baseworks.BaseFragment;

/**
 * Created by JIALIANG on 16/3/29.
 * 主页的fragment
 */
public class HomeFragment extends BaseFragment  {
    //    private Button btn_home;
    private VerticalViewPager viewPager;
    private HomeFragmentAdapter adapter;
    private List<Fragment> fragments;
    private float startY;
    private int viewpagerPosition;

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

        viewPager = bindView(R.id.fragment_home_viewpager);
        fragments = new ArrayList<>();
        fragments.add(new ListFragment());
        fragments.add(new ListFragment());
        fragments.add(new ListFragment());

    }

    @Override
    protected void initData() {

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



}