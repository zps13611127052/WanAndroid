package com.example.wanandroid_2.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.wanandroid_2.R;
import com.example.wanandroid_2.app.MyApp;
import com.example.wanandroid_2.bean.UseWeb_Bean;
import com.example.wanandroid_2.http.ApiServer;
import com.example.wanandroid_2.webactivity.ProjectWebActivity;
import com.example.wanandroid_2.widgth.ColorUtil;
import com.example.wanandroid_2.widgth.FlowLayout;

import org.w3c.dom.Text;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebsiteActivity extends AppCompatActivity {

    private static final String TAG = "WebsiteActivity";
    /**
     * 常用网站
     */
    private TextView mWebtoolbarName;
    private Toolbar mWebtoolbarId;
    private FlowLayout mFlowlayoutId;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        initView();
        initData();
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiServer.Url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServer server = retrofit.create(ApiServer.class);
        Observable<UseWeb_Bean> data = server.getUseWebData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UseWeb_Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UseWeb_Bean value) {
                        if (value!=null){
                            List<UseWeb_Bean.DataBean> beans = value.getData();
                            for (int i = 0; i < beans.size(); i++) {
                                Log.i(TAG, "onNext: "+beans.get(i).getName());
                                mText = (TextView) LayoutInflater.from(WebsiteActivity.this).inflate(R.layout.useweb_text, null);
                                mText.setBackgroundColor(Color.parseColor(ColorUtil.getRandomColor()));

                                mText.setText(beans.get(i).getName());
                                final String name = beans.get(i).getName();
                                final String link = beans.get(i).getLink();

                                mFlowlayoutId.addView(mText);

                                mText.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(WebsiteActivity.this, ProjectWebActivity.class);
                                        intent.putExtra("name",name);
                                        intent.putExtra("url",link);
                                        startActivity(intent);
                                    }
                                });

                            }


                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView() {
        mWebtoolbarName = (TextView) findViewById(R.id.webtoolbar_name);
        mWebtoolbarId = (Toolbar) findViewById(R.id.webtoolbar_id);
        jiemian();
        mFlowlayoutId = (FlowLayout) findViewById(R.id.flowlayout_id);
    }



    private void jiemian() {
        mWebtoolbarId.setTitle("");
        setSupportActionBar(mWebtoolbarId);

        mWebtoolbarId.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
