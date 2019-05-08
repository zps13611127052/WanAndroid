package com.example.wanandroid_2.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid_2.R;
import com.example.wanandroid_2.adapter.project_adapter.Project_Vp_Adapter;
import com.example.wanandroid_2.base.BaseFragment;
import com.example.wanandroid_2.bean.Project_Tab_Bean;
import com.example.wanandroid_2.presenter.Project_Presenter;
import com.example.wanandroid_2.view.Project_View;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by 张十八 on 2019/4/28.
 */

public class Project_Fragment extends BaseFragment<Project_View, Project_Presenter> implements Project_View {

    private static final String TAG = "Project_Fragment";

    @BindView(R.id.project_vp_id)
    ViewPager mProjectVpId;
    @BindView(R.id.project_tab_id)
    SlidingTabLayout mProjectTabId;

    @Override
    protected Project_Presenter initPresenter() {
        return new Project_Presenter();
    }

    //@SuppressLint("NewApi")
    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mPresenter.getData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    public void onSucceedTabData(Project_Tab_Bean tab_bean) {
        if (tab_bean != null && tab_bean.getData().size() > 0) {
            List<Project_Tab_Bean.DataBean> data = tab_bean.getData();
            ArrayList<BaseFragment> fragments = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                Project_Item_Fragment item = new Project_Item_Fragment();
                Bundle bundle = new Bundle();
                bundle.putInt("cid", data.get(i).getId());
                item.setArguments(bundle);
                fragments.add(item);
            }
            Project_Vp_Adapter adapter = new Project_Vp_Adapter(getChildFragmentManager(), fragments, data);
            mProjectVpId.setAdapter(adapter);
            mProjectTabId.setViewPager(mProjectVpId);
        }
    }

    @Override
    public void onFaliuedTabData(String str) {
        Log.i(TAG, "onFaliuedTabData: " + str);
    }

}
