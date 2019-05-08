package com.example.wanandroid_2.model;

import com.example.wanandroid_2.bean.Project_Item_Bean;
import com.example.wanandroid_2.http.ApiServer;
import com.example.wanandroid_2.presenter.Project_Item_Presenter;

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

public class Project_Item_Model {

    public interface projectItemCallBack{
        void onSucceedItem(Project_Item_Bean bean);
        void onFaliuedItem(String str);
    }


    public void getItemData(final projectItemCallBack callBack, int page, int cid) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiServer.Url)
                .build();

        ApiServer server = retrofit.create(ApiServer.class);
        Observable<Project_Item_Bean> data = server.getProjectItemData(page, cid);
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Project_Item_Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Project_Item_Bean value) {
                        if (value!=null){
                            callBack.onSucceedItem(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFaliuedItem(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }



}
