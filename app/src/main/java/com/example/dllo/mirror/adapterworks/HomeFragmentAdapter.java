package com.example.dllo.mirror.adapterworks;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by dllo on 16/3/30.
 *   viewpager的适配器
 */
public class  HomeFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    public void addData(List<Fragment> mFragments){
        this.mFragments = mFragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return  mFragments.size()>0?mFragments.size():0;
    }
}
