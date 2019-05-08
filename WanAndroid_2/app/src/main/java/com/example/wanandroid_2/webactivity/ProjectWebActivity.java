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
import android.widget.TextView;

import com.example.wanandroid_2.R;

import java.lang.reflect.Method;

public class ProjectWebActivity extends AppCompatActivity {

    private WebView mProjectWeb;
    private String mUrl;
    /**
     * xxx
     */
    private TextView mProjectWebToolbarName;
    private Toolbar mProjectWebToolbarId;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_web);
        getUrl();
        initView();

    }

    private void getUrl() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        mName = intent.getStringExtra("name");
    }

    private void initView() {
        mProjectWeb = (WebView) findViewById(R.id.project_web);

        WebSettings settings = mProjectWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        mProjectWeb.setWebViewClient(new WebViewClient());
        mProjectWeb.loadUrl(mUrl);
        mProjectWebToolbarName = (TextView) findViewById(R.id.project_web_toolbar_name);
        mProjectWebToolbarId = (Toolbar) findViewById(R.id.project_web_toolbar_id);

        mProjectWebToolbarName.setText(mName);
        mProjectWebToolbarId.setTitle("");
        setSupportActionBar(mProjectWebToolbarId);

        mProjectWebToolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.projectweboptionsmenu,menu);
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
            case R.id.homewebshare:{
                share();
                break;
            }
            case R.id.homewebbrowser:{
                openliulanqi();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void openliulanqi() {
        //打开系统浏览器
        Uri uri = Uri.parse(mUrl);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(uri);
        startActivity(intent);
    }

    private void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "掌上生活");
        intent.putExtra(Intent.EXTRA_TEXT, mUrl);
        ProjectWebActivity.this.startActivity(Intent.createChooser(intent, "分享"));
    }
}
