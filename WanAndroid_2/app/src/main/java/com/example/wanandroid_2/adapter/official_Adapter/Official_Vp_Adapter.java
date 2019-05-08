package com.example.wanandroid_2.adapter.official_Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wanandroid_2.base.BaseFragment;
import com.example.wanandroid_2.bean.Official_Tab_Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张十八 on 2019/5/2.
 */

public class Official_Vp_Adapter extends FragmentStatePagerAdapter {
    private final ArrayList<BaseFragment> mFragments;
    private final List<Official_Tab_Bean.DataBean> mData;

    public Official_Vp_Adapter(FragmentManager fm, ArrayList<BaseFragment> fragments, List<Official_Tab_Bean.DataBean> data) {
        super(fm);
        mFragments = fragments;
        mData = data;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position).getName();
    }
}
