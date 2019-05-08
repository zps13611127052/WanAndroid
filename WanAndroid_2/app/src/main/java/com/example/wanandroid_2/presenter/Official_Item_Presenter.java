package com.example.wanandroid_2.presenter;

import com.example.wanandroid_2.base.BasePresenter;
import com.example.wanandroid_2.bean.Official_Item_Bean;
import com.example.wanandroid_2.model.Official_Item_Model;
import com.example.wanandroid_2.view.Official_Item_View;

/**
 * Created by 张十八 on 2019/5/2.
 */

public class Official_Item_Presenter extends BasePresenter<Official_Item_View> implements Official_Item_Model.ofItemCallBack{

    private Official_Item_Model model = new Official_Item_Model();

    @Override
    public void onSucceedOfItem(Official_Item_Bean bean) {
        mView.onSucceedOfItem(bean);
        mView.OnShowProgressbar();
    }

    @Override
    public void onFaliuedOfItem(String str) {
        mView.onFaliuedOfItem(str);
        mView.onHideProgressbar();
    }

    public void initItemData(int cid,int page){
        model.initData(this,cid,page);
    }

}
