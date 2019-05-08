package com.example.wanandroid_2.view;

import com.example.wanandroid_2.base.BaseView;
import com.example.wanandroid_2.bean.Official_Item_Bean;

/**
 * Created by 张十八 on 2019/5/2.
 */

public interface Official_Item_View extends BaseView{
    void onSucceedOfItem(Official_Item_Bean bean);
    void onFaliuedOfItem(String str);
}
