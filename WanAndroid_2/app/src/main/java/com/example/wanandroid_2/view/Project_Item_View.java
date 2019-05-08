package com.example.wanandroid_2.view;

import com.example.wanandroid_2.base.BaseView;
import com.example.wanandroid_2.bean.Project_Item_Bean;

/**
 * Created by 张十八 on 2019/4/30.
 */

public interface Project_Item_View extends BaseView{
    void onSucceedProjectItem(Project_Item_Bean bean);
    void onFaliuedProjectItem(String str);
}
