package com.example.wanandroid_2.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid_2.R;
import com.example.wanandroid_2.adapter.Navigation_Adapter.Navigation_Rly_Adapter;
import com.example.wanandroid_2.adapter.Navigation_Adapter.Navigation_Tab_Adapter;
import com.example.wanandroid_2.base.BaseFragment;
import com.example.wanandroid_2.bean.Navigation_Tab_Bean;
import com.example.wanandroid_2.presenter.Navigation_Presenter;
import com.example.wanandroid_2.view.Navigation_Tab_View;
import com.example.wanandroid_2.webactivity.HomeWebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Created by 张十八 on 2019/4/28.
 */

public class Navigation_Fragment extends BaseFragment<Navigation_Tab_View, Navigation_Presenter> implements Navigation_Tab_View {


    @BindView(R.id.fab)
    FloatingActionButton mFab;
    Unbinder unbinder;
    private ArrayList<String> tabTitles = new ArrayList<>();
    private static final String TAG = "Navigation_Fragment";
    @BindView(R.id.navi_rly_id)
    RecyclerView mNaviRlyId;
    @BindView(R.id.tablayout)
    VerticalTabLayout mTablayout;
    private Navigation_Rly_Adapter mAdapter;
    private boolean isClick = false;

    @Override
    protected Navigation_Presenter initPresenter() {
        return new Navigation_Presenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initData() {
        mPresenter.initData();
    }

    @Override
    protected void initView() {
       HideTablayout_Float();



    }

    @Override
    public void onSucceedTab(Navigation_Tab_Bean bean) {
        if (bean != null && bean.getData().size() > 0) {
            List<Navigation_Tab_Bean.DataBean> data = bean.getData();
            for (int i = 0; i < data.size(); i++) {
                Log.i(TAG, "onSucceedTab: " + data.get(i).getName());
                tabTitles.add(data.get(i).getName());
            }
            mNaviRlyId.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new Navigation_Rly_Adapter(getActivity(), data);

            mAdapter.notifyDataSetChanged();
            mNaviRlyId.setAdapter(mAdapter);
        }
        Navigation_Tab_Adapter adapter = new Navigation_Tab_Adapter(tabTitles);
        mTablayout.setTabAdapter(adapter);


        mTablayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) mNaviRlyId.getLayoutManager();
                layoutManager.scrollToPositionWithOffset(position, 0);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });

        mNaviRlyId.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //重写该方法主要是判断recyclerview是否在滑动
                //0停止 ，1,2都是滑动
                if (newState == 0) {
                    isClick = false;
                } else {
                    isClick = true;
                }
                LinearLayoutManager layoutManager = (LinearLayoutManager) mNaviRlyId.getLayoutManager();
                //可见的第一条条目
                int top = layoutManager.findFirstVisibleItemPosition();
                mTablayout.setTabSelected(top);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //这个主要是recyclerview滑动时让tab定位的方法

               /*recyclerView : 当前滚动的view
                dx : 水平滚动距离
                dy : 垂直滚动距离
                dx > 0 时为手指向左滚动,列表滚动显示右面的内容
                dx < 0 时为手指向右滚动,列表滚动显示左面的内容
                dy > 0 时为手指向上滚动,列表滚动显示下面的内容
                dy < 0 时为手指向下滚动,列表滚动显示上面的内容*/
                LinearLayoutManager layoutManager = (LinearLayoutManager) mNaviRlyId.getLayoutManager();
                //可见的第一条条目
                int top = layoutManager.findFirstVisibleItemPosition();
                //可见的最后一条条目
                int bottom = layoutManager.findLastVisibleItemPosition();
                if (isClick) {
                    if (dy > 0) {
                        mTablayout.setTabSelected(top);
                    }
                }
            }
        });


    }

    @Override
    public void onFaliuedTab(String str) {
        Log.i(TAG, "onFaliuedTab: " + str);
    }

    private void HideTablayout_Float() {

        //滑动recyclerView隐藏tablayout
        mNaviRlyId.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (firstVisibleItem != 0) {
                    if (dy > 15) {
                        mFab.setVisibility(View.GONE);
                        getActivity().findViewById(R.id.group_id).setVisibility(View.GONE);
                    } else if (dy < -15) {
                        mFab.setVisibility(View.VISIBLE);
                        getActivity().findViewById(R.id.group_id).setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }


    @OnClick(R.id.fab)
    public void onViewClicked() {
        mNaviRlyId.smoothScrollToPosition(0);
    }
}
