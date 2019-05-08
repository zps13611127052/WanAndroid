package com.example.wanandroid_2.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanandroid_2.R;
import com.example.wanandroid_2.bean.HotWord_Bean;
import com.example.wanandroid_2.http.ApiServer;
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

public class SouSuoActivity extends AppCompatActivity {

    private static final String TAG = "SouSuoActivity";
    private ImageView mSouBack;
    /**
     * 发现更多干货
     */
    private EditText mSouEdId;
    private FlowLayout mFlowId;
    /**
     * 搜索历史
     */
    private TextView mHistory;
    /**
     * 清空
     */
    private TextView mEmpty;
    private TextView mLab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sou_suo);
        initView();
        mSouBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        initData();

    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiServer.Url)
                .build();

        ApiServer server = retrofit.create(ApiServer.class);
        Observable<HotWord_Bean> data = server.getHotData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotWord_Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HotWord_Bean value) {
                        if (value!=null){
                            List<HotWord_Bean.DataBean> beans = value.getData();
                            for (int i = 0; i < beans.size(); i++) {
                                Log.i(TAG, "onNext: "+beans.get(i).getName());

                                mLab = (TextView) LayoutInflater.from(SouSuoActivity.this).inflate(R.layout.useweb_text, null);
                                mLab.setBackgroundColor(Color.parseColor(ColorUtil.getRandomColor()));
                                mLab.setText(beans.get(i).getName());
                                mFlowId.addView(mLab);
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
        mSouBack = (ImageView) findViewById(R.id.sou_back);
        mSouEdId = (EditText) findViewById(R.id.sou_ed_id);
        mFlowId = (FlowLayout) findViewById(R.id.flow_id);
        mHistory = (TextView) findViewById(R.id.history);
        mEmpty = (TextView) findViewById(R.id.empty);
    }
}
