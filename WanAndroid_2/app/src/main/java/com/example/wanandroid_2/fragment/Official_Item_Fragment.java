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
import android.widget.Button;
import android.widget.SearchView;

import com.example.wanandroid_2.R;
import com.example.wanandroid_2.adapter.official_Adapter.Official_Rly_Adapter;
import com.example.wanandroid_2.base.BaseFragment;
import com.example.wanandroid_2.bean.Official_Item_Bean;
import com.example.wanandroid_2.presenter.Official_Item_Presenter;
import com.example.wanandroid_2.view.Official_Item_View;
import com.example.wanandroid_2.webactivity.OfficialWebActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 张十八 on 2019/5/2.
 */

public class Official_Item_Fragment extends BaseFragment<Official_Item_View, Official_Item_Presenter> implements Official_Item_View {


    private static final String TAG = "Official_Item_Fragment";
    @BindView(R.id.searchView_sousuo)
    SearchView mSearchViewSousuo;
    @BindView(R.id.official_btn)
    Button mOfficialBtn;
    @BindView(R.id.official_recy_id)
    RecyclerView mOfficialRecyId;
    @BindView(R.id.official_smart)
    SmartRefreshLayout mOfficialSmart;
    Unbinder unbinder;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    Unbinder unbinder1;
    private int mCid;
    private int page = 1;
    private String mName;
    private ArrayList<Official_Item_Bean.DataBean.DatasBean> mlist = new ArrayList<>();
    private Official_Rly_Adapter mAdapter;

    @Override
    protected Official_Item_Presenter initPresenter() {
        return new Official_Item_Presenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.official_item_fragment;
    }

    @Override
    protected void initView() {
        getCid();
        mSearchViewSousuo.setQueryHint(mName + "带你看更多干货");

        mOfficialRecyId.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new Official_Rly_Adapter(getActivity(), mlist);
        mOfficialRecyId.setAdapter(mAdapter);

        mOfficialSmart.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                mAdapter.notifyDataSetChanged();
                initData();
                mOfficialSmart.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                mAdapter.mMlist.clear();
                mAdapter.notifyDataSetChanged();
                initData();
                mOfficialSmart.finishRefresh();
            }
        });

        mAdapter.setOnClickListener(new Official_Rly_Adapter.onClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), OfficialWebActivity.class);
                intent.putExtra("url", mlist.get(position).getLink());
                intent.putExtra("name", mlist.get(position).getTitle());
                startActivity(intent);
            }
        });

        HideTablayout_Float();
    }

    private void getCid() {
        Bundle bundle = getArguments();
        mCid = bundle.getInt("cid");
        mName = bundle.getString("name");
    }

    @Override
    protected void initData() {
        mPresenter.initItemData(mCid, page);
    }

    @Override
    public void onSucceedOfItem(Official_Item_Bean bean) {
        if (bean != null && bean.getData() != null && bean.getData().getDatas().size() > 0) {
            List<Official_Item_Bean.DataBean.DatasBean> datas = bean.getData().getDatas();
            for (int i = 0; i < datas.size(); i++) {
                Log.i(TAG, "onSucceedOfItem: " + datas.get(i).getAuthor());
            }
            mlist.addAll(datas);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFaliuedOfItem(String str) {
        Log.i(TAG, "onFaliuedOfItem: " + str);
    }


    private void HideTablayout_Float() {

        //滑动recyclerView隐藏tablayout
        mOfficialRecyId.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        mOfficialRecyId.smoothScrollToPosition(0);
    }
}
