package com.example.wanandroid_2.presenter;

import com.example.wanandroid_2.base.BasePresenter;
import com.example.wanandroid_2.bean.Project_Item_Bean;
import com.example.wanandroid_2.bean.Project_Tab_Bean;
import com.example.wanandroid_2.model.Project_Model;
import com.example.wanandroid_2.view.Project_View;

/**
 * Created by 张十八 on 2019/4/30.
 */

public class Project_Presenter extends BasePresenter<Project_View> implements Project_Model.ProjectCallBack{

    private Project_Model model = new Project_Model();
    @Override
    public void onSucceedTab(Project_Tab_Bean bean) {
        mView.onSucceedTabData(bean);
        //mView.OnShowProgressbar();
    }

    @Override
    public void onFaliuedTab(String str) {
        mView.onFaliuedTabData(str);
        //mView.onHideProgressbar();
    }

    public void getData(){
        model.getTabData(this);
    }

}
