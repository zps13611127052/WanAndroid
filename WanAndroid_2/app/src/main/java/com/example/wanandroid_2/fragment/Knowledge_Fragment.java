package com.example.wanandroid_2.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wanandroid_2.R;
import com.example.wanandroid_2.activity.Know_Activity;
import com.example.wanandroid_2.adapter.Knowledge_Adapter.Know_First_Rly_Adapter;
import com.example.wanandroid_2.base.BaseFragment;
import com.example.wanandroid_2.bean.KnowBean;
import com.example.wanandroid_2.presenter.Know_Presenter;
import com.example.wanandroid_2.view.Know_View;
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
 * Created by 张十八 on 2019/4/28.
 */

public class Knowledge_Fragment extends BaseFragment<Know_View, Know_Presenter> implements Know_View {

    private static final String TAG = "Knowledge_Fragment";
    @BindView(R.id.know_recy_id)
    RecyclerView mKnowRecyId;
    @BindView(R.id.know_smart_id)
    SmartRefreshLayout mKnowSmartId;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    Unbinder unbinder;
    private Know_First_Rly_Adapter mAdapter;
    private ArrayList<KnowBean.DataBean> mlist = new ArrayList<>();

    @Override
    protected void initData() {
        mPresenter.knowData();
    }

    @Override
    protected void initView() {
        mKnowRecyId.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new Know_First_Rly_Adapter(getActivity(), mlist);
        mKnowRecyId.setAdapter(mAdapter);

        mKnowSmartId.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Toast.makeText(getContext(), "没有更多干货了", Toast.LENGTH_SHORT).show();
                mKnowSmartId.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mAdapter.mMlist.clear();
                mAdapter.notifyDataSetChanged();
                initData();
            }
        });

        mAdapter.setOnClickListener(new Know_First_Rly_Adapter.onClickListener() {
            @Override
            public void onClick(int position) {
                KnowBean.DataBean bean = mlist.get(position);
                List<KnowBean.DataBean.ChildrenBean> beanChildren = bean.getChildren();
                String toolbar_name = mlist.get(position).getName();

                Intent intent = new Intent(getContext(), Know_Activity.class);
                intent.putExtra("toolbarname", toolbar_name);
                intent.putExtra("bean", mAdapter.mMlist);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        HideTablayout_Float();
    }

    @Override
    protected Know_Presenter initPresenter() {
        return new Know_Presenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge;
    }

    @Override
    public void onSucceedKnow(KnowBean bean) {
        if (bean != null && bean.getData().size() > 0) {
            List<KnowBean.DataBean> data = bean.getData();
            mlist.addAll(data);
            mAdapter.notifyDataSetChanged();
            mKnowSmartId.finishRefresh();
        }
    }


    @OnClick(R.id.fab)
    public void onViewClicked() {
        mKnowRecyId.smoothScrollToPosition(0);

    }

    private void HideTablayout_Float() {

        //滑动recyclerView隐藏tablayout
        mKnowRecyId.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
