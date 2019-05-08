package com.example.wanandroid_2.utils;

import com.example.wanandroid_2.app.MyApp;
import com.example.wanandroid_2.bean.DaoBean;
import com.example.wanandroid_2.db.DaoBeanDao;
import com.example.wanandroid_2.db.DaoMaster;
import com.example.wanandroid_2.db.DaoSession;

import java.util.List;

/**
 * Created by 张十八 on 2019/5/6.
 */

public class DaoUtil {
    private static DaoUtil sDaoUtils;
    private final DaoBeanDao mDao;

    public DaoUtil() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MyApp.getMyApp(), "into.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        mDao = daoSession.getDaoBeanDao();
    }


    public static DaoUtil getDaoUtils() {
        if (sDaoUtils == null){
            synchronized (DaoUtil.class){
                if (sDaoUtils == null){
                    sDaoUtils = new DaoUtil();
                }
            }
        }
        return sDaoUtils;
    }

    public boolean has(DaoBean bean){
//        List<DaoBean> list = mDao.queryBuilder().where(DaoBeanDao.Properties.Title.eq(bean.getTitle())).list();
        List<DaoBean> list = mDao.queryBuilder().where(DaoBeanDao.Properties.Link.eq(bean.getLink())).list();
        if (list!=null&&list.size()>0){
            return true;
        }
        return false;
    }

    public long insert(DaoBean bean){
        //如果数据库中没有  就插入
        if (!has(bean)){
            return mDao.insertOrReplace(bean);
        }
        //如果数据库有  就返回-1
        return -1;
    }

    public boolean delete(DaoBean bean) {
        if (has(bean)) {
            mDao.delete(bean);
            return true;
        }
        return false;
    }

    public List<DaoBean> query(){
        return mDao.queryBuilder().list();
    }

}
