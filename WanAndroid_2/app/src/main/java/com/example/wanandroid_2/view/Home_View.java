package com.example.wanandroid_2.view;

import com.example.wanandroid_2.base.BaseView;
import com.example.wanandroid_2.bean.Home_Banner_Bean;
import com.example.wanandroid_2.bean.Home_Item_Bean;

/**
 * Created by 张十八 on 2019/4/29.
 */

public interface Home_View extends BaseView{
    void onSucceedBanner(Home_Banner_Bean bean);
    void onFaliuedBanner(String str);

    void onSucceedHomeItem(Home_Item_Bean bean);
    void onFaliuedHomeItem(String str);
}
