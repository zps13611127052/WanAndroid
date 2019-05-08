package com.example.wanandroid_2.presenter;

import android.util.Log;

import com.example.wanandroid_2.base.BasePresenter;
import com.example.wanandroid_2.bean.KnowBean;
import com.example.wanandroid_2.model.Know_Model;
import com.example.wanandroid_2.view.Know_View;

/**
 * Created by 张十八 on 2019/4/30.
 */

public  class Know_Presenter extends BasePresenter<Know_View> implements Know_View, Know_Model.knowCallBack {

    private static final String TAG = "Know_Presenter";
    private  Know_Model model = new Know_Model();

    public void knowData(){
        model.getKnowData(this);
        mView.OnShowProgressbar();
    }

    @Override
    public void onSucceedKnow(KnowBean bean) {
        mView.onSucceedKnow(bean);
        mView.onHideProgressbar();
    }

    @Override
    public void OnShowProgressbar() {

    }

    @Override
    public void onHideProgressbar() {

    }

    @Override
    public void onSucceedKnowData(KnowBean bean) {
        mView.onSucceedKnow(bean);
    }
}
