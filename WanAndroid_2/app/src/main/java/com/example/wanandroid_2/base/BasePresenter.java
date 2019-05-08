package com.example.wanandroid_2.base;

import java.lang.ref.WeakReference;

/**
 * Created by 张十八 on 2019/4/29.
 */

public class BasePresenter<V> {
    private WeakReference<V> mVWeakReference;
    public V mView;
    public void bind(V v){
        WeakReference<V> vWeakReference = new WeakReference<>(v);
        mView = vWeakReference.get();
    }

    public void onDestroy(){
        if (mVWeakReference!=null){
            mVWeakReference.clear();
        }
    }
}