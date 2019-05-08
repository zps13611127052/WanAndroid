package com.example.wanandroid_2.view;

import com.example.wanandroid_2.base.BaseView;
import com.example.wanandroid_2.bean.Navigation_Tab_Bean;

/**
 * Created by 张十八 on 2019/5/5.
 */

public interface Navigation_Tab_View extends BaseView{
    void onSucceedTab(Navigation_Tab_Bean bean);
    void onFaliuedTab(String str);
}
