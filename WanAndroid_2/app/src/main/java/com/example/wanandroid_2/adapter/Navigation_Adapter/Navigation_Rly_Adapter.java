package com.example.wanandroid_2.adapter.Navigation_Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wanandroid_2.R;
import com.example.wanandroid_2.bean.Navigation_Tab_Bean;
import com.example.wanandroid_2.webactivity.HomeWebActivity;
import com.example.wanandroid_2.widgth.ColorUtil;
import com.example.wanandroid_2.widgth.FlowLayout;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by 张十八 on 2019/5/6.
 */

public class Navigation_Rly_Adapter extends RecyclerView.Adapter {
    private final FragmentActivity mActivity;
    public final List<Navigation_Tab_Bean.DataBean> mData;
    private TextView mLab;
    private String mLink;
    private String mName;
    private String mTitle;

    public Navigation_Rly_Adapter(FragmentActivity activity, List<Navigation_Tab_Bean.DataBean> data) {
        mActivity = activity;
        mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.navigation_item_layout, null);
        viewholder viewholder = new viewholder(inflate);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        viewholder viewholder = (Navigation_Rly_Adapter.viewholder) holder;
        viewholder.mFlow_id.removeAllViews();
        final List<Navigation_Tab_Bean.DataBean.ArticlesBean> articles = mData.get(position).getArticles();
        viewholder.mNa_tv.setText(mData.get(position).getName());

        for (int i = 0; i < articles.size(); i++) {
            mLab = (TextView) LayoutInflater.from(mActivity).inflate(R.layout.navigation_text, null);
            mLab.setTextColor(Color.parseColor(ColorUtil.getRandomColor()));
            mTitle = articles.get(i).getTitle();
            mLab.setText(mTitle);
            viewholder.mFlow_id.addView(mLab);

            final int finalI = i;
            mLab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, HomeWebActivity.class);
                    String Title = articles.get(finalI).getTitle();
                    String Link = articles.get(finalI).getLink();
                    intent.putExtra("name", Title);
                    intent.putExtra("url", Link);
                    mActivity.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class viewholder extends RecyclerView.ViewHolder {
        private final TextView mNa_tv;
        private final FlowLayout mFlow_id;

        public viewholder(View itemView) {
            super(itemView);
            mNa_tv = itemView.findViewById(R.id.na_tv);
            mFlow_id = itemView.findViewById(R.id.flow_id);
        }
    }

    private onClickListener mOnClickListener;

    public void setOnClickListener(onClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface onClickListener {
        void onClick(int position, String link, String name);
    }

}
