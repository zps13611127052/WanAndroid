package com.example.wanandroid_2.webactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanandroid_2.R;
import com.example.wanandroid_2.bean.DaoBean;
import com.example.wanandroid_2.utils.DaoUtil;

import java.lang.reflect.Method;
import java.util.List;

public class HomeWebActivity extends AppCompatActivity {

    private WebView mHomeWeb;
    private String mUrl;
    /**
     * xxx
     */
    private TextView mHomeWebToolbarName;
    private Toolbar mHomwWebToolbarId;
    private String mName;
    private ImageView mHomeImgXin;
    private String mCharname;
    private String mSupercharname;
    private String mTime;
    private String mTitle;
    private DaoBean mDaoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_web);
        getUrl();
        initView();
    }
    private void getUrl() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        mName = intent.getStringExtra("name");
        mCharname = intent.getStringExtra("charname");
        mSupercharname = intent.getStringExtra("supercharname");
        mTime = intent.getStringExtra("time");
        mTitle = intent.getStringExtra("title");
    }

    public boolean database(){
        final List<DaoBean> list = DaoUtil.getDaoUtils().query();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLink().equals(mUrl)) {
                mHomeImgXin.setImageResource(R.mipmap.xin);
                mDaoBean = new DaoBean();
                mDaoBean.setAuthor(mName);
                mDaoBean.setCharSuperName(mSupercharname);
                mDaoBean.setLink(mUrl);
                mDaoBean.setNiceDate(mTime);
                mDaoBean.setTitle(mTitle);
                mDaoBean.setCharname(mCharname);
                mDaoBean.setId(null);
                return true;
            }
        }
        return false;
    }

    private void initView() {
        mHomeWeb = (WebView) findViewById(R.id.home_web);
        mHomeWebToolbarName = (TextView) findViewById(R.id.home_web_toolbar_name);
        mHomwWebToolbarId = (Toolbar) findViewById(R.id.homw_web_toolbar_id);
        mHomeImgXin = (ImageView) findViewById(R.id.home_img_xin);
        database();
        IntentWeb();
        WebToolBar_OnClick();

        mHomeImgXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DaoBean> list = DaoUtil.getDaoUtils().query();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getLink().equals(mUrl)) {
                        boolean delete = DaoUtil.getDaoUtils().delete(mDaoBean);
                        if (delete){
                            Toast.makeText(HomeWebActivity.this, "取消", Toast.LENGTH_SHORT).show();
                            mHomeImgXin.setImageResource(R.drawable.follow_unselected);
                        }else {
                            mHomeImgXin.setImageResource(R.mipmap.xin);
                        }
                        break;
                    }
                        Toast.makeText(HomeWebActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                        mHomeImgXin.setImageResource(R.mipmap.xin);
                        DaoBean daoBean = new DaoBean();
                        daoBean.setId(null);
                        daoBean.setCharname(mCharname);
                        daoBean.setTitle(mTitle);
                        daoBean.setNiceDate(mTime);
                        daoBean.setLink(mUrl);
                        daoBean.setCharSuperName(mSupercharname);
                        daoBean.setAuthor(mName);
                        DaoUtil.getDaoUtils().insert(daoBean);
                }
//                //插入数据库
//                long insert = DaoUtil.getDaoUtils().insert(mDaoBean);
//                if (insert == -1) {
//                    boolean delete = DaoUtil.getDaoUtils().delete(mDaoBean);
//                    //代表数据库有，不能插入了
//                    if (delete) {
//                        //ToastUtil.showShort("取消收藏");
//                        Toast.makeText(HomeWebActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
//                        mHomeImgXin.setImageResource(R.drawable.follow_unselected);
//                    } else {
//                        mHomeImgXin.setImageResource(R.mipmap.xin);
//                    }
//                } else {
//                    //收藏成功 改成小红心
//                    mHomeImgXin.setImageResource(R.mipmap.xin);
//                    // ToastUtil.showShort("收藏成功");
//                    Toast.makeText(HomeWebActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    private void WebToolBar_OnClick() {
        mHomeWebToolbarName.setText(mName);
        mHomwWebToolbarId.setTitle("");
        setSupportActionBar(mHomwWebToolbarId);

        mHomwWebToolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void IntentWeb() {
        WebSettings settings = mHomeWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        mHomeWeb.setWebViewClient(new WebViewClient());
        mHomeWeb.loadUrl(mUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homeweboptionsmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // 让菜单同时显示图标和文字
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homewebshare: {
                share();
                break;
            }
            case R.id.homewebbrowser: {
                //打开系统浏览器
                Uri uri = Uri.parse(mUrl);
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "掌上生活");
        intent.putExtra(Intent.EXTRA_TEXT, mUrl);
        HomeWebActivity.this.startActivity(Intent.createChooser(intent, "分享"));
    }
}
