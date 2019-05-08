package com.example.wanandroid_2.presenter;

import com.example.wanandroid_2.base.BasePresenter;
import com.example.wanandroid_2.bean.Official_Tab_Bean;
import com.example.wanandroid_2.model.Official_Tab_Model;
import com.example.wanandroid_2.view.Official_Tab_View;

/**
 * Created by 张十八 on 2019/5/2.
 */

public class Official_Tab_Presenter extends BasePresenter<Official_Tab_View> implements Official_Tab_Model.ofTabCallBack{

    private Official_Tab_Model model = new Official_Tab_Model();

    @Override
    public void onSucceedOfTab(Official_Tab_Bean bean) {
        mView.onSucceedOfTab(bean);
//        mView.OnShowProgressbar();
    }

    @Override
    public void onFailuedOfTab(String str) {
        mView.onFaliuedOfTab(str);
//        mView.onHideProgressbar();
    }

    public void initTabData(){
        model.initTabData(this);
    }
}
