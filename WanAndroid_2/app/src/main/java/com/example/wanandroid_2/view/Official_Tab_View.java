package com.example.wanandroid_2.view;

import com.example.wanandroid_2.base.BaseView;
import com.example.wanandroid_2.bean.Official_Tab_Bean;

/**
 * Created by 张十八 on 2019/5/2.
 */

public interface Official_Tab_View extends BaseView{
    void onSucceedOfTab(Official_Tab_Bean bean);
    void onFaliuedOfTab(String str);
}
