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
import com.example.wanandroid_2.adapter.project_adapter.Project_Rly_Adapter;
import com.example.wanandroid_2.base.BaseFragment;
import com.example.wanandroid_2.bean.Project_Item_Bean;
import com.example.wanandroid_2.presenter.Project_Item_Presenter;
import com.example.wanandroid_2.view.Project_Item_View;
import com.example.wanandroid_2.webactivity.ProjectWebActivity;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 张十八 on 2019/4/30.
 */

public class Project_Item_Fragment extends BaseFragment<Project_Item_View, Project_Item_Presenter> implements Project_Item_View {


    private static final String TAG = "Project_Item_Fragment";
    @BindView(R.id.project_recy_id)
    RecyclerView mProjectRecyId;
    @BindView(R.id.project_smart_id)
    SmartRefreshLayout mProjectSmartId;
    Unbinder unbinder;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    Unbinder unbinder1;
    private int mCid;
    private int page = 1;
    private View view;
    private Project_Rly_Adapter mAdapter;
    private ArrayList<Project_Item_Bean.DataBean.DatasBean> mlist = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.project_item_fragment;
    }

    @Override
    protected void initView() {
        getCid();
        mProjectRecyId.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new Project_Rly_Adapter(getActivity(), mlist);
        mProjectRecyId.setAdapter(mAdapter);

        mProjectSmartId.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                mAdapter.notifyDataSetChanged();
                mPresenter.getProjectItemData(page, mCid);
                mProjectSmartId.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                mAdapter.mMlist.clear();
                mAdapter.notifyDataSetChanged();
                mPresenter.getProjectItemData(page, mCid);

                mProjectSmartId.finishRefresh();
            }
        });

        mProjectSmartId.setRefreshHeader(new BezierCircleHeader(getContext()));
        mProjectSmartId.setRefreshFooter(new ClassicsFooter(getContext()));

        mAdapter.setOnClickListener(new Project_Rly_Adapter.onClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), ProjectWebActivity.class);
                intent.putExtra("url", mlist.get(position).getLink());
                intent.putExtra("name", mlist.get(position).getTitle());
                startActivity(intent);
            }
        });

        HideTablayout_Float();
    }

    @Override
    protected void initData() {
        mPresenter.getProjectItemData(page, mCid);
    }

    @Override
    protected Project_Item_Presenter initPresenter() {
        return new Project_Item_Presenter();
    }

    private void getCid() {
        Bundle bundle = getArguments();
        mCid = bundle.getInt("cid");
    }


    @Override
    protected void initListener() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProjectRecyId.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    public void onSucceedProjectItem(Project_Item_Bean bean) {
        if (bean != null && bean.getData() != null && bean.getData().getDatas().size() > 0) {
            List<Project_Item_Bean.DataBean.DatasBean> datas = bean.getData().getDatas();
            for (int i = 0; i < datas.size(); i++) {
                Log.i(TAG, "onSucceedProjectItem: " + datas.get(i).getAuthor());
            }
            mlist.addAll(datas);
            mAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onFaliuedProjectItem(String str) {
        Log.i(TAG, "onFaliuedProjectItem: " + str);
    }


    private void HideTablayout_Float() {

        //滑动recyclerView隐藏tablayout
        mProjectRecyId.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
