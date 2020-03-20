package com.demo.ctw.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.demo.ctw.base.BaseFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<String> data;
    private ArrayList<BaseFragment> fragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void initData(ArrayList<String> data, ArrayList<BaseFragment> fragments) {
        this.data = data;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments == null ? null : fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        return data == null ? "" : data.get(position);
        return data == null ? "" : data.get(position % data.size());
    }
}
