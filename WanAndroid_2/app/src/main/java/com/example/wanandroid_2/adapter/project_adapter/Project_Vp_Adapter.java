package com.example.wanandroid_2.adapter.project_adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wanandroid_2.base.BaseFragment;
import com.example.wanandroid_2.bean.Project_Tab_Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张十八 on 2019/4/30.
 */

public class Project_Vp_Adapter extends FragmentStatePagerAdapter {
    private final ArrayList<BaseFragment> mFragments;
    private final List<Project_Tab_Bean.DataBean> mData;

    public Project_Vp_Adapter(FragmentManager fm, ArrayList<BaseFragment> fragments, List<Project_Tab_Bean.DataBean> data) {
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
