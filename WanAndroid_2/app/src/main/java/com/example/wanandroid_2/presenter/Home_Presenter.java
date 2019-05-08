package com.example.wanandroid_2.presenter;

import com.example.wanandroid_2.base.BasePresenter;
import com.example.wanandroid_2.bean.Home_Banner_Bean;
import com.example.wanandroid_2.bean.Home_Item_Bean;
import com.example.wanandroid_2.model.Home_Model;
import com.example.wanandroid_2.view.Home_View;

/**
 * Created by 张十八 on 2019/4/29.
 */

public class Home_Presenter<V extends Home_View> extends BasePresenter<Home_View> implements Home_Model.homeCallBack{
    private  Home_Model model = new Home_Model();

    @Override
    public void onHomeBannerData(Home_Banner_Bean bean) {
        mView.onSucceedBanner(bean);
        mView.onHideProgressbar();
    }

    @Override
    public void onHomeItemData(Home_Item_Bean bean) {
        mView.onSucceedHomeItem(bean);
        mView.onHideProgressbar();
    }

    public void getBannerData(){
        model.getBannerData(this);
        mView.OnShowProgressbar();
    }

    public void getItemData(int page){
        model.getItemData(this,page);
        mView.OnShowProgressbar();
    }

}
