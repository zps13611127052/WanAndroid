package com.example.wanandroid_2.webactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.wanandroid_2.R;

import java.lang.reflect.Method;

public class OfficialWebActivity extends AppCompatActivity {

    private WebView mOfficialWeb;
    private String mUrl;
    /**
     * xxx
     */
    private TextView mOfficWebToolbarName;
    private Toolbar mOfficWebToolbarId;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_official_web);
        getUrl();
        initView();
    }

    private void getUrl() {
        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        mUrl = intent.getStringExtra("url");
    }

    private void initView() {
        mOfficialWeb = (WebView) findViewById(R.id.official_web);

        WebSettings settings = mOfficialWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        mOfficialWeb.setWebViewClient(new WebViewClient());
        mOfficialWeb.loadUrl(mUrl);
        mOfficWebToolbarName = (TextView) findViewById(R.id.offic_web_toolbar_name);
        mOfficWebToolbarId = (Toolbar) findViewById(R.id.offic_web_toolbar_id);

        mOfficWebToolbarName.setText(mName);
        mOfficWebToolbarId.setTitle("");
        setSupportActionBar(mOfficWebToolbarId);

        mOfficWebToolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homeweboptionsmenu,menu);
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
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "掌上生活");
                intent.putExtra(Intent.EXTRA_TEXT, mUrl);
                OfficialWebActivity.this.startActivity(Intent.createChooser(intent, "分享"));
                break;
            }

            case R.id.homewebbrowser:{
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
}
