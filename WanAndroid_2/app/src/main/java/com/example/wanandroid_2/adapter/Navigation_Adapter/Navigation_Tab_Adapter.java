package com.example.wanandroid_2.adapter.Navigation_Adapter;

import com.example.wanandroid_2.R;

import java.util.ArrayList;

import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;

/**
 * Created by 张十八 on 2019/5/5.
 */

public class Navigation_Tab_Adapter implements TabAdapter {
    private ArrayList<String> mTabTitles;

    public Navigation_Tab_Adapter(ArrayList<String> tabTitles) {
        mTabTitles = tabTitles;
    }

    @Override
    public int getCount() {
        return mTabTitles.size();
    }

    @Override
    public ITabView.TabBadge getBadge(int position) {
        return null;
    }

    @Override
    public ITabView.TabIcon getIcon(int position) {
        return null;
    }

    @Override
    public ITabView.TabTitle getTitle(int position) {
        return new ITabView.TabTitle.Builder()
                .setContent(mTabTitles.get(position))
                .setTextColor(R.color.blue,R.color.black)
                .build();
    }

    @Override
    public int getBackground(int position) {
        return 0;
    }
}
