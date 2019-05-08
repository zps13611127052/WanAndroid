package com.example.wanandroid_2.model;

import com.example.wanandroid_2.bean.Official_Tab_Bean;
import com.example.wanandroid_2.http.ApiServer;
import com.example.wanandroid_2.presenter.Official_Tab_Presenter;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 张十八 on 2019/5/2.
 */

public class Official_Tab_Model {
    public interface ofTabCallBack{
        void onSucceedOfTab(Official_Tab_Bean bean);
        void onFailuedOfTab(String str);
    }

    public void initTabData(final ofTabCallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiServer.Url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServer server = retrofit.create(ApiServer.class);
        Observable<Official_Tab_Bean> data = server.getOfficialTabData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Official_Tab_Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Official_Tab_Bean value) {
                        if (value!=null){
                            callBack.onSucceedOfTab(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailuedOfTab(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
