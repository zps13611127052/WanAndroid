package com.example.wanandroid_2.adapter.Knowledge_Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wanandroid_2.bean.KnowBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张十八 on 2019/5/4.
 */

public class Know_Vp_Adapter extends FragmentStatePagerAdapter {
    private final ArrayList<Fragment> mFragments;
    private final List<KnowBean.DataBean.ChildrenBean> mChildren;

    public Know_Vp_Adapter(FragmentManager fm, ArrayList<Fragment> fragments, List<KnowBean.DataBean.ChildrenBean> children) {
        super(fm);
        mFragments = fragments;
        mChildren = children;
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
        return mChildren.get(position).getName();
    }
}
