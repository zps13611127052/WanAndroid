package com.example.wanandroid_2.utils;

import android.os.Environment;

import com.example.wanandroid_2.app.MyApp;

import java.io.File;

/**
 * Created by asus on 2019/3/5.
 */

public interface Constants {
    boolean isDebug = true;


    String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + "codeest" + File.separator + "GeekNews";

    String FILE_PROVIDER_AUTHORITY="com.baidu.geek.fileprovider";

    //网络缓存的地址
    String PATH_DATA = MyApp.getMyApp().getCacheDir().getAbsolutePath() +
            File.separator + "data";


    String PATH_DATAs = MyApp.getMyApp().getCacheDir().getAbsolutePath() +
            File.separator + "datas";

    String PATH_CACHEs = PATH_DATAs + "/NetCache";

    String PATH_CACHE = PATH_DATA + "/NetCache";
    String DATA = "data";
    //夜间模式
    String MODE = "mode";
    String NIGHT_CURRENT_FRAG_POS = "fragment_pos";
    String DAY_NIGHT_FRAGMENT_POS = "day_night";
    //保存设置日夜间模式时碎片的position

}
