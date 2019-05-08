package com.example.wanandroid_2.model;

import com.example.wanandroid_2.bean.Official_Item_Bean;
import com.example.wanandroid_2.http.ApiServer;
import com.example.wanandroid_2.presenter.Official_Item_Presenter;

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

public class Official_Item_Model {
    public void initData(final ofItemCallBack callBack, int cid, int page) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiServer.Url)
                .build();

        ApiServer server = retrofit.create(ApiServer.class);
        Observable<Official_Item_Bean> data = server.getOfficialItemData(cid, page);
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Official_Item_Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Official_Item_Bean value) {
                        if (value!=null){
                            callBack.onSucceedOfItem(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFaliuedOfItem(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface ofItemCallBack{
        void onSucceedOfItem(Official_Item_Bean bean);
        void onFaliuedOfItem(String str);
    }
}
