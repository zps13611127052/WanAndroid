package com.example.wanandroid_2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanandroid_2.R;
import com.example.wanandroid_2.adapter.collectAdapter.Collect_Adapter;
import com.example.wanandroid_2.bean.DaoBean;
import com.example.wanandroid_2.utils.DaoUtil;
import com.example.wanandroid_2.webactivity.ProjectWebActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.List;

/**
 * Created by 张十八 on 2019/5/5.
 */

public class Collect_fragment extends Fragment {
    private static final String TAG = "Collect_fragment";
    private View view;
    /**
     * 快去收藏一些干货吧！
     */
    private RecyclerView mCollectRecyId;
    private SmartRefreshLayout mCollectSmart;
    private Collect_Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.collect_fragment, null);
        initData();
        initView(inflate);
        return inflate;
    }

    private void initData() {
        List<DaoBean> list = DaoUtil.getDaoUtils().query();

        if (list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                Log.i(TAG, "initData: "+list.get(i).getAuthor());
            }
            mAdapter = new Collect_Adapter(getActivity(),list);
            mAdapter.notifyDataSetChanged();
        }
    }
    private void initView(final View inflate) {

        mCollectRecyId = (RecyclerView) inflate.findViewById(R.id.collect_recy_id);
        mCollectSmart = (SmartRefreshLayout) inflate.findViewById(R.id.collect_smart);

        mCollectRecyId.setLayoutManager(new LinearLayoutManager(getContext()));
        mCollectRecyId.setAdapter(mAdapter);


        mCollectSmart.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Toast.makeText(getContext(), "快去收藏更多干货吧", Toast.LENGTH_SHORT).show();
                mCollectSmart.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
                mAdapter.notifyDataSetChanged();
                mCollectSmart.finishRefresh();
            }
        });

//            mAdapter.setOnClickListener(new Collect_Adapter.onClickListener() {
//                @Override
//                public void onListener(int position) {
//                    DaoBean bean = mAdapter.mList.get(position);
//
//                    Intent intent = new Intent(getContext(), ProjectWebActivity.class);
//                    intent.putExtra("name",bean.getTitle());
//                    intent.putExtra("url",bean.getLink());
//                    startActivity(intent);
//                }
//            });
//
//            mAdapter.setOnClickLongListener(new Collect_Adapter.onClickLongListener() {
//                @Override
//                public void onClickLong(int position) {
//                    DaoBean bean = mAdapter.mList.get(position);
//                    DaoUtil.getDaoUtils().delete(bean);
//                    mAdapter.mList.remove(bean);
//                    mAdapter.notifyDataSetChanged();
//                }
//            });
    }
}
