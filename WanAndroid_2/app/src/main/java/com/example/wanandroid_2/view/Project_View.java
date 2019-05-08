package com.example.wanandroid_2.view;

import com.example.wanandroid_2.base.BaseView;
import com.example.wanandroid_2.bean.Project_Item_Bean;
import com.example.wanandroid_2.bean.Project_Tab_Bean;

/**
 * Created by 张十八 on 2019/4/30.
 */

public interface Project_View extends BaseView{
    void onSucceedTabData(Project_Tab_Bean tab_bean);
    void onFaliuedTabData(String str);
//    void onSucceedItemData(Project_Item_Bean item_bean);
//    void onFaliuedItemData(String str);
}
