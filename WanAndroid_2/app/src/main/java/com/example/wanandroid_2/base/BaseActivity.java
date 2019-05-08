package com.example.wanandroid_2.base;

/**
 * Created by 张十八 on 2019/4/29.
 */

public abstract class BaseActivity<V,P extends BasePresenter<V>> extends SimplActivity{

    protected P mPresenter;
    @Override
    protected void initView() {
        mPresenter = initPresenter();
        mPresenter.bind((V)this);
    }

    protected abstract P initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null){
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }
}
