package com.example.wanandroid_2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.wanandroid_2.R;
import com.example.wanandroid_2.adapter.Knowledge_Adapter.Know_Vp_Adapter;
import com.example.wanandroid_2.bean.KnowBean;
import com.example.wanandroid_2.fragment.KnowItem_Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Know_Activity extends AppCompatActivity {

    private static final String TAG = "Know_Activity";
    private String mToolbarname;
    /**
     * name
     */
    private TextView mKnowToolbarName;
    private Toolbar mKnowToolbarId;
    private TabLayout mKnowTab;
    private ViewPager mKnowVp;
    private int mPosition;
    private ArrayList<KnowBean.DataBean> mBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_);

        getFragmentData();
        initView();

    }

    private void getFragmentData() {
        Intent intent = getIntent();
        mToolbarname = intent.getStringExtra("toolbarname");
        mPosition = intent.getIntExtra("position", 0);
        mBean = (ArrayList<KnowBean.DataBean>) intent.getSerializableExtra("bean");
    }

    private void initView() {
        mKnowToolbarName = (TextView) findViewById(R.id.know_toolbar_name);
        mKnowToolbarId = (Toolbar) findViewById(R.id.know_toolbar_id);
        mKnowTab = (TabLayout) findViewById(R.id.know_tab);
        mKnowVp = (ViewPager) findViewById(R.id.know_vp);

        mKnowToolbarName.setText(mToolbarname);
        mKnowToolbarId.setTitle("");
        setSupportActionBar(mKnowToolbarId);

        mKnowToolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        List<KnowBean.DataBean.ChildrenBean> children = mBean.get(mPosition).getChildren();
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < children.size(); i++) {
            KnowItem_Fragment item = new KnowItem_Fragment();
            Bundle bundle = new Bundle();
            bundle.putInt("cid",children.get(i).getId());
            item.setArguments(bundle);
            fragments.add(item);
        }

        Know_Vp_Adapter adapter = new Know_Vp_Adapter(getSupportFragmentManager(),fragments,children);
        mKnowVp.setAdapter(adapter);
        mKnowTab.setupWithViewPager(mKnowVp);
    }
}
