package com.example.wanandroid_2.presenter;

import com.example.wanandroid_2.base.BasePresenter;
import com.example.wanandroid_2.bean.Navigation_Tab_Bean;
import com.example.wanandroid_2.model.Navigation_Tab_Model;
import com.example.wanandroid_2.view.Navigation_Tab_View;

/**
 * Created by 张十八 on 2019/5/5.
 */

public class Navigation_Presenter extends BasePresenter<Navigation_Tab_View> implements Navigation_Tab_Model.NaTabCallBack {

    private Navigation_Tab_Model model = new Navigation_Tab_Model();

    public void initData(){
        model.initData(this);
    }

    @Override
    public void onSucceedTab(Navigation_Tab_Bean bean) {
        if (bean!=null){
            if (mView!=null){
                mView.onSucceedTab(bean);
                mView.OnShowProgressbar();
            }
        }
    }

    @Override
    public void onFaliuedTab(String str) {
        if (mView!=null){
            mView.onFaliuedTab(str);
            mView.onHideProgressbar();
        }
    }
}
