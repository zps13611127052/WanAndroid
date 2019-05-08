package com.example.wanandroid_2.http;

import com.example.codelibrary.mylibrary.HttpManager;
import com.example.wanandroid_2.app.MyApp;

/**
 * Created by 张十八 on 2019/4/29.
 */

public class ApiManager {
    private static ApiManager sApiManager;

    public ApiManager() {
    }

    public static ApiManager getApiManager() {
        if (sApiManager == null){
            synchronized (ApiManager.class){
                if (sApiManager == null){
                    sApiManager = new ApiManager();
                }
            }
        }
        return sApiManager;
    }

    public ApiServer getServer(){
        return HttpManager.getHttpManager().getRetrofitClient(ApiServer.Url, MyApp.getMyApp()).create(ApiServer.class);
    }
}
