package com.example.wanandroid_2.model;

import android.util.Log;

import com.example.codelibrary.callback.BaseResponce;
import com.example.codelibrary.callback.ObServerCallBack;
import com.example.codelibrary.mylibrary.HttpManager;
import com.example.codelibrary.utils.HttpUtils;
import com.example.wanandroid_2.bean.Home_Banner_Bean;
import com.example.wanandroid_2.bean.Home_Item_Bean;
import com.example.wanandroid_2.http.ApiManager;
import com.example.wanandroid_2.http.ApiServer;
import com.example.wanandroid_2.presenter.Home_Presenter;
import com.example.wanandroid_2.view.Home_View;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 张十八 on 2019/4/29.
 */

public class Home_Model{

    private static final String TAG = "Home_Model";

    public interface homeCallBack{
        void onHomeBannerData(Home_Banner_Bean bean);
        void onHomeItemData(Home_Item_Bean bean);
    }

    public  void getItemData(final homeCallBack back, int page) {
        ApiManager.getApiManager().getServer()
                .getHomeItemData(page)
                .compose(HttpUtils.<BaseResponce<Home_Item_Bean>>rxShcdule())
                .compose(HttpUtils.<Home_Item_Bean>handResult())
                .subscribe(new ObServerCallBack<Home_Item_Bean>() {
                    @Override
                    public void onError(String message) {
                        Log.i(TAG, "onError: "+message);
                    }

                    @Override
                    public void onNext(Home_Item_Bean value) {
                        back.onHomeItemData(value);
                    }
                });
    }

    public  void getBannerData(final homeCallBack back) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiServer.Url)
                .build();

        ApiServer server = retrofit.create(ApiServer.class);
        Observable<Home_Banner_Bean> data = server.getBannerData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Home_Banner_Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Home_Banner_Bean value) {
                        if (value!=null){
                            back.onHomeBannerData(value);
                        }
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
