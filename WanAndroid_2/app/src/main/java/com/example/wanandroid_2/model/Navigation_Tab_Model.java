package com.example.wanandroid_2.model;

import com.example.wanandroid_2.bean.Navigation_Tab_Bean;
import com.example.wanandroid_2.http.ApiServer;
import com.example.wanandroid_2.presenter.Navigation_Presenter;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 张十八 on 2019/5/5.
 */

public class Navigation_Tab_Model {

    public interface NaTabCallBack{
        void onSucceedTab(Navigation_Tab_Bean bean);
        void onFaliuedTab(String str);
    }

    public void initData(final NaTabCallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiServer.Url)
                .build();

        ApiServer server = retrofit.create(ApiServer.class);
        Observable<Navigation_Tab_Bean> data = server.getNavigationTabData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Navigation_Tab_Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Navigation_Tab_Bean value) {
                        if (value!=null){
                            callBack.onSucceedTab(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFaliuedTab(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
