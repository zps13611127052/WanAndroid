package com.example.codelibrary.mylibrary;

import android.content.Context;
import android.util.Log;

import com.example.codelibrary.utils.HttpUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 张十八 on 2019/4/29.
 */

public class HttpManager {
    private static HttpManager sHttpManager;

    public HttpManager() {
    }

    public static HttpManager getHttpManager() {
        if (sHttpManager == null){
            synchronized (HttpManager.class){
                if (sHttpManager == null){
                    sHttpManager = new HttpManager();
                }
            }
        }
        return sHttpManager;
    }

    public Retrofit getRetrofitClient(String baseurl, Context context){
        return new Retrofit.Builder()
                .baseUrl(baseurl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient(context))
                .build();
    }

    private OkHttpClient getOkHttpClient(Context context) {
        //日志过滤器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    Log.e("OKHttp-----", text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e("OKHttp-----", message);
                }
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Cache cache = new Cache(new File(context.getCacheDir(), "Cache"), 1024 * 1024 * 10);
        MyCacheinterceptor myCacheinterceptor = new MyCacheinterceptor(context);
        return new OkHttpClient.Builder()
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .cache(cache)

                //添加日志
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(myCacheinterceptor)
                .addNetworkInterceptor(myCacheinterceptor)
                .retryOnConnectionFailure(true)
                .build();
    }

    private class MyCacheinterceptor implements Interceptor{
        private final Context context;

        public MyCacheinterceptor(Context context) {
            this.context = context;
        }


        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //判断我们的网络情况，如果有网就网络加载，没网就从缓存里拿
            if (!HttpUtils.isNetwrokAvailable(context)){
                request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalRespone = chain.proceed(request);
            if (HttpUtils.isNetwrokAvailable(context)){
                int maxAge = 0;
                return originalRespone.newBuilder()
                        //清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Pragma")
                        .header("Cache-Control","public,max-age"+maxAge)
                        .build();
            }else {
                int maxStale = 15;
                return originalRespone.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control","public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    }
}
