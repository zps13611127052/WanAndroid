package com.example.wanandroid_2.app;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.wanandroid_2.utils.Constants;
import com.example.wanandroid_2.utils.SpUtil;
import com.example.wanandroid_2.utils.UIModeUtil;

/**
 * Created by 张十八 on 2019/4/29.
 */

public class MyApp extends Application{
    private static MyApp sMyApp;
    //默认不是夜间模式
    public static int mMode = AppCompatDelegate.MODE_NIGHT_NO;
    public static int mWidthPixels;
    public static int mHeightPixels;
    @Override
    public void onCreate() {
        super.onCreate();
        sMyApp = this;
        getScreenWH();
        setDayNightMode();
    }
    private void setDayNightMode() {
        //根据sp里面的模式设置对应的日夜间模式
        mMode = (int) SpUtil.getParam(Constants.MODE, AppCompatDelegate.MODE_NIGHT_NO);
        UIModeUtil.setAppMode(mMode);
    }

    //屏幕宽高
    private void getScreenWH() {
        WindowManager manger = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display defaultDisplay = manger.getDefaultDisplay();
        DisplayMetrics metics = new DisplayMetrics();
        defaultDisplay.getMetrics(metics);
        mWidthPixels = metics.widthPixels;
        mHeightPixels = metics.heightPixels;
    }

    public static MyApp getMyApp() {
        return sMyApp;
    }
}
