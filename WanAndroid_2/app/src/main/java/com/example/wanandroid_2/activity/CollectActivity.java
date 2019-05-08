package com.example.wanandroid_2.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanandroid_2.MainActivity;
import com.example.wanandroid_2.R;
import com.example.wanandroid_2.adapter.collectAdapter.Collect_Adapter;
import com.example.wanandroid_2.bean.DaoBean;
import com.example.wanandroid_2.utils.DaoUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.List;

public class CollectActivity extends AppCompatActivity {

    private static final String TAG = "CollectActivity";
    private Collect_Adapter mAdapter;
    /**
     * 收藏
     */
    private TextView mToolbarName;
    private Toolbar mToolbarId;
    private RecyclerView mCollectRecyId;
    private SmartRefreshLayout mCollectSmartId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        initData();
        initView();

        ToolBarFace();
    }

    private void ToolBarFace() {
        mToolbarId.setTitle("");
        setSupportActionBar(mToolbarId);

        mToolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        List<DaoBean> list = DaoUtil.getDaoUtils().query();

        if (list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                Log.i(TAG, "initData: "+list.get(i).getAuthor());
            }
            mAdapter = new Collect_Adapter(this,list);
            mAdapter.notifyDataSetChanged();
        }

//        mAdapter.setOnClickListener(new Collect_Adapter.onClickListener() {
//            @Override
//            public void onListener(int position) {
//                mAdapter.mList.remove(position);
//                DaoUtil.getDaoUtils().delete(mAdapter.mList.get(position));
//                mAdapter.notifyDataSetChanged();
//            }
//        });
    }

    private void initView() {
        mToolbarName = (TextView) findViewById(R.id.toolbar_name);
        mToolbarId = (Toolbar) findViewById(R.id.toolbar_id);
        mCollectRecyId = (RecyclerView) findViewById(R.id.collect_recy_id);
        mCollectSmartId = (SmartRefreshLayout) findViewById(R.id.collect_smart_id);

        mCollectRecyId.setLayoutManager(new LinearLayoutManager(this));
        mCollectRecyId.setAdapter(mAdapter);



        mCollectSmartId.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Toast.makeText(CollectActivity.this, "快去收藏更多干货吧", Toast.LENGTH_SHORT).show();
                mCollectSmartId.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData();
                mAdapter.notifyDataSetChanged();
                mCollectSmartId.finishRefresh();
            }
        });



    }
}
