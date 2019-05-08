package com.example.wanandroid_2.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.example.wanandroid_2.R;
import com.example.wanandroid_2.adapter.official_Adapter.Official_Vp_Adapter;
import com.example.wanandroid_2.base.BaseFragment;
import com.example.wanandroid_2.base.BasePresenter;
import com.example.wanandroid_2.bean.Official_Tab_Bean;
import com.example.wanandroid_2.presenter.Official_Tab_Presenter;
import com.example.wanandroid_2.view.Official_Tab_View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 张十八 on 2019/4/28.
 */

public class Official_Fragment extends BaseFragment<Official_Tab_View,Official_Tab_Presenter> implements Official_Tab_View {

    private static final String TAG = "Official_Fragment";
    @BindView(R.id.official_tab)
    TabLayout mOfficialTab;
    @BindView(R.id.vp_id)
    ViewPager mVpId;

    @Override
    protected Official_Tab_Presenter initPresenter() {
        return new Official_Tab_Presenter();
    }

    @Override
    protected void initData() {
        mPresenter.initTabData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_official;
    }

    @Override
    public void onSucceedOfTab(Official_Tab_Bean bean) {
        if (bean!=null&&bean.getData().size()>0){
            List<Official_Tab_Bean.DataBean> data = bean.getData();
            ArrayList<BaseFragment> fragments = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                Official_Item_Fragment item = new Official_Item_Fragment();
                Bundle bundle = new Bundle();
                bundle.putInt("cid",data.get(i).getId());
                bundle.putString("name",data.get(i).getName());
                item.setArguments(bundle);
                fragments.add(item);
                //mSearchViewSousuo.setQueryHint(data.get(i).getName()+"带你发现更多干货");
            }
            Official_Vp_Adapter adapter = new Official_Vp_Adapter(getChildFragmentManager(),fragments,data);
            mVpId.setAdapter(adapter);
            mOfficialTab.setupWithViewPager(mVpId);
        }
    }

    @Override
    public void onFaliuedOfTab(String str) {
        Log.i(TAG, "onFaliuedOfTab: "+str);
    }
}
