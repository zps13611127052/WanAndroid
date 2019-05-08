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
import com.example.wanandroid_2.adapter.Home_Rly_Adapter;
import com.example.wanandroid_2.base.BaseFragment;
import com.example.wanandroid_2.bean.Home_Banner_Bean;
import com.example.wanandroid_2.bean.Home_Item_Bean;
import com.example.wanandroid_2.presenter.Home_Presenter;
import com.example.wanandroid_2.view.Home_View;
import com.example.wanandroid_2.webactivity.HomeWebActivity;
import com.scwang.smartrefresh.header.DropboxHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by 张十八 on 2019/4/28.
 */

public class HomePager_Fragment extends BaseFragment<Home_View, Home_Presenter> implements Home_View {

    @BindView(R.id.Home_recy_id)
    RecyclerView mHomeRecyId;
    @BindView(R.id.Home_smar_id)
    SmartRefreshLayout mHomeSmarId;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    Unbinder unbinder;
    private int page = 0;
    private ArrayList<Home_Banner_Bean.DataBean> mBannerList = new ArrayList<>();
    private ArrayList<Home_Item_Bean.DatasBean> mItemList = new ArrayList<>();
    private Home_Rly_Adapter mAdapter;

    @Override
    protected void initData() {
        mPresenter.getItemData(page);
        mPresenter.getBannerData();
    }

    @Override
    protected void initView() {
        mHomeSmarId.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                mAdapter.notifyDataSetChanged();
                mPresenter.getItemData(page);
                mHomeSmarId.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                mAdapter.mItemList.clear();
                mAdapter.mBannerList.clear();
                initData();
                mAdapter.notifyDataSetChanged();
                mHomeSmarId.finishRefresh();
            }
        });
       // initRecy();
        mHomeSmarId.setRefreshHeader(new DropboxHeader(getContext()));
        mHomeSmarId.setRefreshFooter(new BallPulseFooter(getContext()));

        mHomeRecyId.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new Home_Rly_Adapter(getActivity(), mBannerList, mItemList);
        mHomeRecyId.setAdapter(mAdapter);

        mAdapter.setOnClickListener(new Home_Rly_Adapter.onClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), HomeWebActivity.class);
                intent.putExtra("url", mItemList.get(position).getLink());
                intent.putExtra("name", mItemList.get(position).getTitle());
                intent.putExtra("charname", mItemList.get(position).getChapterName());
                intent.putExtra("supercharname", mItemList.get(position).getSuperChapterName());
                intent.putExtra("title", mItemList.get(position).getTitle());
                intent.putExtra("time", mItemList.get(position).getNiceDate());
                startActivity(intent);
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeRecyId.smoothScrollToPosition(0);
            }
        });
        HideTablayout_Float();
    }

    @Override
    public void onSucceedBanner(Home_Banner_Bean bean) {
        List<Home_Banner_Bean.DataBean> data = bean.getData();
        for (int i = 0; i < data.size(); i++) {
            Log.i("banner", "onSucceedBanner: " + data.get(i).getTitle());
            //titles.add(data.get(i).getTitle());
        }
        mBannerList.addAll(data);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFaliuedBanner(String str) {
        Log.i("onFaliuedBanner", "onFaliuedBanner: " + str);
    }

    @Override
    public void onSucceedHomeItem(Home_Item_Bean bean) {
        List<Home_Item_Bean.DatasBean> datas = bean.getDatas();
        for (int i = 0; i < datas.size(); i++) {
            Log.i("onSucceedHomeItem", "onSucceedHomeItem: " + datas.get(i).getAuthor());
        }
        mItemList.addAll(datas);
        mAdapter.notifyDataSetChanged();
        mHomeSmarId.finishLoadmore();
        mHomeSmarId.finishRefresh();
    }

    @Override
    public void onFaliuedHomeItem(String str) {
        Log.i("onFaliuedHomeItem", "onFaliuedHomeItem: " + str);
    }

    @Override
    protected Home_Presenter initPresenter() {
        return new Home_Presenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homepager;
    }


    private void HideTablayout_Float() {

        //滑动recyclerView隐藏tablayout
        mHomeRecyId.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
}
