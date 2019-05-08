package com.example.wanandroid_2.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 张十八 on 2019/4/29.
 */

public abstract class SimplActivity extends AppCompatActivity {

    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(getLayoutId(), null);
        setContentView(view);
        mBind = ButterKnife.bind(this);
        initView();
        initData();
        initLIstener();
    }

    protected void initLIstener() {

    }

    protected void initData() {

    }

    protected void initView() {

    }

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind == null){
            mBind.unbind();
        }
    }
}
