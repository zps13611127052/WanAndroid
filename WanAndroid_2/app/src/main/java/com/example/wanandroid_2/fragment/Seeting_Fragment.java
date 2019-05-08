package com.example.wanandroid_2.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wanandroid_2.MainActivity;
import com.example.wanandroid_2.R;
import com.example.wanandroid_2.utils.ACache;
import com.example.wanandroid_2.utils.Constants;
import com.example.wanandroid_2.utils.SpUtil;
import com.example.wanandroid_2.utils.UIModeUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 张十八 on 2019/5/5.
 */

public class Seeting_Fragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.download)
    ImageView mDownload;
    @BindView(R.id.download_check)
    CheckBox mDownloadCheck;
    @BindView(R.id.noimage_img)
    ImageView mNoimageImg;
    @BindView(R.id.noimg_check)
    CheckBox mNoimgCheck;
    @BindView(R.id.night_img)
    ImageView mNightImg;
    @BindView(R.id.opinion_img)
    ImageView mOpinionImg;
    @BindView(R.id.eliminate_img)
    ImageView mEliminateImg;
    @BindView(R.id.eliminate_name)
    TextView mEliminateName;
    Unbinder unbinder;
    private View view;
    private CheckBox mNightCheck;
    private File cacheFile;
    private RelativeLayout mSettingRlv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.seetingfragment, null);
        unbinder = ButterKnife.bind(this, inflate);
        initView(inflate);
        //initListener();
        return inflate;
    }


    private void initView(View inflate) {
        mDownload = (ImageView) inflate.findViewById(R.id.download);
        mDownloadCheck = (CheckBox) inflate.findViewById(R.id.download_check);
        mNoimageImg = (ImageView) inflate.findViewById(R.id.noimage_img);
        mNoimgCheck = (CheckBox) inflate.findViewById(R.id.noimg_check);
        mNightImg = (ImageView) inflate.findViewById(R.id.night_img);
        mOpinionImg = (ImageView) inflate.findViewById(R.id.opinion_img);
        mEliminateImg = (ImageView) inflate.findViewById(R.id.eliminate_img);
        mEliminateName = (TextView) inflate.findViewById(R.id.eliminate_name);
        mNightCheck = (CheckBox) inflate.findViewById(R.id.night_check);

        //判断是否为夜间模式
        IfNightMode();

        //Check的监听方法
        initListener();

        cacheFile = new File(Constants.PATH_CACHE);
        String cacheSize = ACache.getCacheSize(cacheFile);
        Log.d("lcze", "cacheSize: "+cacheSize);
        mEliminateName.setText(cacheSize);

        mSettingRlv = (RelativeLayout) inflate.findViewById(R.id.setting_rlv);
        mSettingRlv.setOnClickListener(this);
    }


    private void clearCache() {
        ACache.deleteDir(cacheFile);
        mEliminateName.setText(ACache.getCacheSize(cacheFile));
    }

    private void IfNightMode() {
        int currentNightMode = getActivity().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            //判断当前是日间模式
            mNightCheck.setChecked(false);
        } else {
            mNightCheck.setChecked(true);
        }
    }

    protected void initListener() {
        //点击切换日夜间模式
        NightMode();

        //无图模式
        NoImg();


    }

    private void NoImg() {
        mNoimgCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SpUtil.setParam("img", isChecked);
                } else {
                    SpUtil.setParam("img", isChecked);
                }
            }
        });
    }

    private void NightMode() {
        mNightCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //切换模式
                //切换日夜间模式的时候Activity会重新创建
                //对应的这个碎片也会重建,重建的时候SwitchCompat会设置默认值
                //设置默认值的时候这个回调会被调用
                //if (用户点击的情况下){
                if (buttonView.isPressed()) {
                    //切换并保存模式
                    UIModeUtil.changeModeUI((MainActivity) getActivity());
                    //保存当前碎片的type
                    SpUtil.setParam(Constants.DAY_NIGHT_FRAGMENT_POS, MainActivity.mSeeting_fragment);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.setting_rlv:
                clearCache();
                break;
        }
    }
}
