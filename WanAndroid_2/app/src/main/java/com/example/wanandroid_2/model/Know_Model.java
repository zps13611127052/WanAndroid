package com.example.wanandroid_2.model;

import android.util.Log;

import com.example.wanandroid_2.bean.KnowBean;
import com.example.wanandroid_2.http.ApiServer;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 张十八 on 2019/4/30.
 */

public class Know_Model  {

    private static final String TAG = "Know_Model";

    public interface knowCallBack{
        void onSucceedKnowData(KnowBean bean);
    }

    public void getKnowData(final knowCallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiServer.Url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServer server = retrofit.create(ApiServer.class);
        Observable<KnowBean> data = server.getKnowBean();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<KnowBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(KnowBean value) {
                        if (value!=null){
                            callBack.onSucceedKnowData(value);
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
