package com.example.wanandroid_2.http;

import com.example.codelibrary.callback.BaseResponce;
import com.example.wanandroid_2.bean.Home_Banner_Bean;
import com.example.wanandroid_2.bean.Home_Item_Bean;
import com.example.wanandroid_2.bean.HotWord_Bean;
import com.example.wanandroid_2.bean.KnowBean;
import com.example.wanandroid_2.bean.Know_Item_Data;
import com.example.wanandroid_2.bean.Navigation_Tab_Bean;
import com.example.wanandroid_2.bean.Official_Item_Bean;
import com.example.wanandroid_2.bean.Official_Tab_Bean;
import com.example.wanandroid_2.bean.Project_Item_Bean;
import com.example.wanandroid_2.bean.Project_Tab_Bean;
import com.example.wanandroid_2.bean.UseWeb_Bean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by 张十八 on 2019/4/29.
 */

public interface ApiServer {
    String Url = "http://www.wanandroid.com/";
    @GET("banner/json")
    Observable<Home_Banner_Bean> getBannerData();

    @GET("article/list/{page}/json")
    Observable<BaseResponce<Home_Item_Bean>>getHomeItemData(@Path("page")int page);

    @GET("tree/json")
    Observable<KnowBean>getKnowBean();

    @GET("project/tree/json")
    Observable<Project_Tab_Bean>getProjectTabData();

    @GET("article/list/{page}/json?")
    Observable<Know_Item_Data>getKnowItemData(@Path("page")int page,@Query("cid")int cid);

    @GET("project/list/{page}/json?")
    Observable<Project_Item_Bean>getProjectItemData(@Path("page")int page, @Query("cid") int cid);

    @GET("wxarticle/chapters/json")
    Observable<Official_Tab_Bean>getOfficialTabData();

    @GET("wxarticle/list/{cid}/{page}/json")
    Observable<Official_Item_Bean>getOfficialItemData(@Path("cid")int cid,@Path("page")int page);

//    https://www.wanandroid.com/navi/json
    @GET("navi/json")
    Observable<Navigation_Tab_Bean>getNavigationTabData();

//    https://www.wanandroid.com/
    @GET("friend/json")
    Observable<UseWeb_Bean>getUseWebData();

//    https://www.wanandroid.com//hotkey/json
    @GET("/hotkey/json")
    Observable<HotWord_Bean>getHotData();
}
