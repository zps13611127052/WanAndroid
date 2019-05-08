package com.example.wanandroid_2.presenter;

import com.example.wanandroid_2.base.BasePresenter;
import com.example.wanandroid_2.bean.Project_Item_Bean;
import com.example.wanandroid_2.bean.Project_Tab_Bean;
import com.example.wanandroid_2.model.Project_Item_Model;
import com.example.wanandroid_2.model.Project_Model;
import com.example.wanandroid_2.view.Project_Item_View;
import com.example.wanandroid_2.view.Project_View;

/**
 * Created by 张十八 on 2019/4/30.
 */

public class Project_Item_Presenter extends BasePresenter<Project_Item_View> implements Project_Item_Model.projectItemCallBack{
    private Project_Item_Model model = new Project_Item_Model();

    public void getProjectItemData(int page,int cid){
        model.getItemData(this,page,cid);
    }

    @Override
    public void onSucceedItem(Project_Item_Bean bean) {
        mView.onSucceedProjectItem(bean);
        //mView.onHideProgressbar();
    }

    @Override
    public void onFaliuedItem(String str) {
        mView.onFaliuedProjectItem(str);
//        mView.onHideProgressbar();
    }
}
