package com.example.wanandroid_2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanandroid_2.activity.AboutWeActivity;
import com.example.wanandroid_2.activity.CollectActivity;
import com.example.wanandroid_2.activity.LoginActivity;
import com.example.wanandroid_2.activity.SouSuoActivity;
import com.example.wanandroid_2.activity.WebsiteActivity;
import com.example.wanandroid_2.fragment.Collect_fragment;
import com.example.wanandroid_2.fragment.HomePager_Fragment;
import com.example.wanandroid_2.fragment.Knowledge_Fragment;
import com.example.wanandroid_2.fragment.Navigation_Fragment;
import com.example.wanandroid_2.fragment.Official_Fragment;
import com.example.wanandroid_2.fragment.Project_Fragment;
import com.example.wanandroid_2.fragment.Seeting_Fragment;
import com.example.wanandroid_2.utils.Constants;
import com.example.wanandroid_2.utils.SpUtil;

public class MainActivity extends AppCompatActivity {

    /**
     * 首页
     */
    private TextView mToolbarName;
    private Toolbar mToolbarId;
    private FrameLayout mFragmentId;
    /**
     * 首页
     */
    private RadioButton mRbHomepage;
    /**
     * 知识体系
     */
    private RadioButton mRbKnowledge;
    /**
     * 公众号
     */
    private RadioButton mRbOfficial;
    /**
     * 导航
     */
    private RadioButton mRbNavigation;
    /**
     * 项目
     */
    private RadioButton mRbProject;
    private RadioGroup mGroupId;
    private LinearLayout mMl;
    private RelativeLayout mRl;
    private NavigationView mNavigationId;
    private DrawerLayout mDrawerlayoutId;
    private FragmentManager mManager;
    private HomePager_Fragment mHomePager_fragment;
    private Knowledge_Fragment mKnowledge_fragment;
    private Official_Fragment mOfficial_fragment;
    private Project_Fragment mProject_fragment;
    private Navigation_Fragment mNavigation_fragment;
    public static Seeting_Fragment mSeeting_fragment;
    private TextView mTextright;
    private Collect_fragment mCollect_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //点击打开侧滑
        ClickOpenSideslip();
        //Navigation监听
        NavigationMonitor();
        //初始化碎片
        initFragment();
        //侧滑推开页面
        initNa();
        //第一个显示的界面
        ShowFirstFragment();
        //点击radiobutton切换碎片
        RadioButtonClick();

        mNavigationId.getHeaderView(0).findViewById(R.id.head_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initNa() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerlayoutId, mToolbarId, R.string.app_name, R.string.about) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
                View mContent = mDrawerlayoutId.getChildAt(0);
                float scale = 1 - slideOffset;
                float endScale = 0.8f + scale * 0.2f;
                float startScale = 1 - 0.3f * scale;

                //设置左边菜单滑动后的占据屏幕大小
                drawerView.setScaleX(startScale);
                drawerView.setScaleY(startScale);
                //设置菜单透明度
                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));

                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                //设置内容界面操作无效（比如有button就会点击无效）
                mContent.invalidate();
                //设置右边菜单滑动后的占据屏幕大小
                mContent.setScaleX(endScale);
                mContent.setScaleY(endScale);
            }
        };
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        mDrawerlayoutId.addDrawerListener(toggle);
    }

    private void initView() {
        mToolbarName = (TextView) findViewById(R.id.toolbar_name);
        mToolbarId = (Toolbar) findViewById(R.id.toolbar_id);
        mFragmentId = (FrameLayout) findViewById(R.id.fragment_id);
        mRbHomepage = (RadioButton) findViewById(R.id.rb_homepage);
        mRbKnowledge = (RadioButton) findViewById(R.id.rb_knowledge);
        mRbOfficial = (RadioButton) findViewById(R.id.rb_official);
        mRbNavigation = (RadioButton) findViewById(R.id.rb_navigation);
        mRbProject = (RadioButton) findViewById(R.id.rb_project);
        mGroupId = (RadioGroup) findViewById(R.id.group_id);
        mMl = (LinearLayout) findViewById(R.id.ml);
        mRl = (RelativeLayout) findViewById(R.id.rl);
        mNavigationId = (NavigationView) findViewById(R.id.navigation_id);
        mDrawerlayoutId = (DrawerLayout) findViewById(R.id.drawerlayout_id);

        mToolbarId.setTitle("");
        setSupportActionBar(mToolbarId);

        mMl = (LinearLayout) findViewById(R.id.ml);
        mRl = (RelativeLayout) findViewById(R.id.rl);
        mTextright = (TextView) findViewById(R.id.textright);
    }

    //点击打开侧滑
    private void ClickOpenSideslip() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerlayoutId, mToolbarId, R.string.app_name, R.string.about);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        mDrawerlayoutId.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void RadioButtonClick() {
        mGroupId.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_homepage: {
                        FragmentTransaction transaction = mManager.beginTransaction();
                        transaction.show(mHomePager_fragment).hide(mCollect_fragment).hide(mKnowledge_fragment).hide(mOfficial_fragment).hide(mNavigation_fragment).hide(mSeeting_fragment).hide(mProject_fragment);
                        transaction.commit();
                        mToolbarName.setText(R.string.homepage);
                        mGroupId.setVisibility(View.VISIBLE);
                        break;
                    }
                    case R.id.rb_knowledge: {
                        FragmentTransaction transaction = mManager.beginTransaction();
                        transaction.show(mKnowledge_fragment).hide(mCollect_fragment).hide(mSeeting_fragment).hide(mHomePager_fragment).hide(mOfficial_fragment).hide(mNavigation_fragment).hide(mProject_fragment);
                        transaction.commit();
                        mToolbarName.setText(R.string.knowledge);
                        break;
                    }
                    case R.id.rb_official: {
                        FragmentTransaction transaction = mManager.beginTransaction();
                        transaction.show(mOfficial_fragment).hide(mCollect_fragment).hide(mSeeting_fragment).hide(mHomePager_fragment).hide(mKnowledge_fragment).hide(mNavigation_fragment).hide(mProject_fragment);
                        transaction.commit();
                        mToolbarName.setText(R.string.Official);
                        break;
                    }
                    case R.id.rb_navigation: {
                        FragmentTransaction transaction = mManager.beginTransaction();
                        transaction.show(mNavigation_fragment).hide(mCollect_fragment).hide(mSeeting_fragment).hide(mHomePager_fragment).hide(mOfficial_fragment).hide(mKnowledge_fragment).hide(mProject_fragment);
                        transaction.commit();
                        mToolbarName.setText(R.string.navigation);
                        break;
                    }
                    case R.id.rb_project: {
                        FragmentTransaction transaction = mManager.beginTransaction();
                        transaction.show(mProject_fragment).hide(mCollect_fragment).hide(mSeeting_fragment).hide(mHomePager_fragment).hide(mOfficial_fragment).hide(mNavigation_fragment).hide(mKnowledge_fragment);
                        transaction.commit();
                        mToolbarName.setText(R.string.project);
                        break;
                    }
                }
            }
        });
    }

    private void ShowFirstFragment() {
        //如果是因为切换日夜间模式导致Activyt重建,需要直接进入设置Fragment
       // int type = (int) SpUtil.getParam(Constants.DAY_NIGHT_FRAGMENT_POS, mHomePager_fragment);
        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.show(mHomePager_fragment).hide(mCollect_fragment).hide(mSeeting_fragment).hide(mKnowledge_fragment).hide(mOfficial_fragment).hide(mNavigation_fragment).hide(mProject_fragment);
        transaction.commit();
    }

    private void initFragment() {
        mManager = getSupportFragmentManager();
        mHomePager_fragment = new HomePager_Fragment();
        mKnowledge_fragment = new Knowledge_Fragment();
        mOfficial_fragment = new Official_Fragment();
        mNavigation_fragment = new Navigation_Fragment();
        mProject_fragment = new Project_Fragment();
        mSeeting_fragment = new Seeting_Fragment();
        mCollect_fragment = new Collect_fragment();
        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.add(R.id.fragment_id, mHomePager_fragment);
        transaction.add(R.id.fragment_id, mKnowledge_fragment);
        transaction.add(R.id.fragment_id, mOfficial_fragment);
        transaction.add(R.id.fragment_id, mNavigation_fragment);
        transaction.add(R.id.fragment_id, mProject_fragment);
        transaction.add(R.id.fragment_id, mSeeting_fragment);
        transaction.add(R.id.fragment_id, mCollect_fragment);
        transaction.commit();
    }

    //Navigation监听
    private void NavigationMonitor() {
        mNavigationId.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.wanandroid_id: {
                        //Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
                        FragmentTransaction transaction = mManager.beginTransaction();
                        transaction.show(mHomePager_fragment).hide(mCollect_fragment).hide(mProject_fragment).hide(mSeeting_fragment).hide(mOfficial_fragment).hide(mNavigation_fragment).hide(mKnowledge_fragment);
                        transaction.commit();
                        mDrawerlayoutId.closeDrawer(Gravity.LEFT);
                        mToolbarName.setText("首页");
                        mGroupId.setVisibility(View.VISIBLE);
                        mDrawerlayoutId.closeDrawer(Gravity.LEFT);
                        mRbHomepage.setChecked(true);
                        break;
                    }
                    case R.id.heart: {
//                        FragmentTransaction transaction = mManager.beginTransaction();
//                        transaction.show(mCollect_fragment).hide(mSeeting_fragment).hide(mProject_fragment).hide(mHomePager_fragment).hide(mOfficial_fragment).hide(mNavigation_fragment).hide(mKnowledge_fragment);
//                        transaction.commit();
//                        mToolbarName.setText("收藏");
                        Intent intent = new Intent(MainActivity.this, CollectActivity.class);
                        startActivity(intent);
                       // mGroupId.setVisibility(View.GONE);
                        break;
                    }
                    case R.id.setting: {
                        //Toast.makeText(MainActivity.this, "3", Toast.LENGTH_SHORT).show();
                        FragmentTransaction transaction = mManager.beginTransaction();
                        transaction.show(mSeeting_fragment)
                                .hide(mCollect_fragment)
                                .hide(mProject_fragment)
                                .hide(mHomePager_fragment)
                                .hide(mOfficial_fragment)
                                .hide(mNavigation_fragment)
                                .hide(mKnowledge_fragment);
                        transaction.commit();
                        mToolbarName.setText("设置");
                        mGroupId.setVisibility(View.GONE);
                        mDrawerlayoutId.closeDrawer(Gravity.LEFT);
                        break;
                    }
                    case R.id.about: {
                        Intent intent = new Intent(MainActivity.this, AboutWeActivity.class);

                        startActivity(intent);
                        mGroupId.setVisibility(View.GONE);
                        mDrawerlayoutId.closeDrawer(Gravity.LEFT);
                        break;
                    }
                }

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionsmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1: {
                //Toast.makeText(this, "常用网站", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, WebsiteActivity.class);
                CircularAnimUtil.startActivity(MainActivity.this, intent, mTextright, R.color.color_303C59);
                break;
            }
            case R.id.op2: {
                //Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SouSuoActivity.class);
                CircularAnimUtil.startActivity(MainActivity.this, intent, mTextright, R.color.color_303C59);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 回退键点击回调
     */
    @Override
    public void onBackPressed() {
            super.onBackPressed();
            SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS,mHomePager_fragment);
        }

}