package com.example.wanandroid_2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wanandroid_2.R;
import com.example.wanandroid_2.adapter.Knowledge_Adapter.Know_Item_Adapter;
import com.example.wanandroid_2.bean.KnowBean;
import com.example.wanandroid_2.bean.Know_Item_Data;
import com.example.wanandroid_2.bean.Project_Item_Bean;
import com.example.wanandroid_2.http.ApiServer;
import com.example.wanandroid_2.webactivity.HomeWebActivity;
import com.scwang.smartrefresh.header.FunGameBattleCityHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 张十八 on 2019/5/4.
 */

public class KnowItem_Fragment extends Fragment {
    private static final String TAG = "KnowItem_Fragment";
    private View view;
    private RecyclerView mKnowitemRecyId;
    private SmartRefreshLayout mKnowItemSmart;
    private int mCid;
    private int page = 0;
    private ArrayList<Know_Item_Data.DataBean.DatasBean> mlist = new ArrayList<>();
    private Know_Item_Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.know_item_fragment, null);
        getCid();
        initView(inflate);
        initData();
        return inflate;
    }

    private void getCid() {
        Bundle bundle = getArguments();
        mCid = bundle.getInt("cid");
        Log.i(TAG, "getCid: "+mCid);
    }


    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiServer.Url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServer server = retrofit.create(ApiServer.class);
        final Observable<Know_Item_Data> data = server.getKnowItemData(page, mCid);
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Know_Item_Data>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Know_Item_Data value) {
                        if (value!=null){
                            List<Know_Item_Data.DataBean.DatasBean> datas = value.getData().getDatas();
                            mlist.addAll(datas);
                            mAdapter.notifyDataSetChanged();
                            if (value.getData().getDatas().size() == 0||value.getData().getDatas() == null){
                                Toast.makeText(getContext(), "没有更多干货了", Toast.LENGTH_SHORT).show();
                                mKnowItemSmart.finishLoadmore();
                                mlist.addAll(datas);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView(final View inflate) {
        mKnowitemRecyId = (RecyclerView) inflate.findViewById(R.id.knowitem_recy_id);
        mKnowItemSmart = (SmartRefreshLayout) inflate.findViewById(R.id.know_item_smart);

        mKnowitemRecyId.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new Know_Item_Adapter(getActivity(),mlist);
        mKnowitemRecyId.setAdapter(mAdapter);


        mKnowItemSmart.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                mAdapter.notifyDataSetChanged();
                initData();
                mKnowItemSmart.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 0;
                mAdapter.mMlist.clear();
                mAdapter.notifyDataSetChanged();
                initData();
                mKnowItemSmart.finishRefresh();
            }
        });
        mKnowItemSmart.setRefreshFooter(new BallPulseFooter(getContext()));
        mKnowItemSmart.setRefreshHeader(new FunGameBattleCityHeader(getContext()));

        mAdapter.setOnClickListener(new Know_Item_Adapter.onClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), HomeWebActivity.class);
                intent.putExtra("name",mlist.get(position).getTitle());
                intent.putExtra("url",mlist.get(position).getLink());
                startActivity(intent);
            }
        });

    }
}