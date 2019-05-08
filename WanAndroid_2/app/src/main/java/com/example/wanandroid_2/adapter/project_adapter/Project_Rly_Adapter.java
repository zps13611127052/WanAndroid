package com.example.wanandroid_2.adapter.project_adapter;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wanandroid_2.R;
import com.example.wanandroid_2.bean.Project_Item_Bean;
import com.example.wanandroid_2.utils.SpUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by 张十八 on 2019/4/30.
 */

public class Project_Rly_Adapter extends RecyclerView.Adapter {
    private final FragmentActivity mActivity;
    public final ArrayList<Project_Item_Bean.DataBean.DatasBean> mMlist;

    public Project_Rly_Adapter(FragmentActivity activity, ArrayList<Project_Item_Bean.DataBean.DatasBean> mlist) {
        mActivity = activity;
        mMlist = mlist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.project_item_buju, null);
        viewholder viewholder = new viewholder(inflate);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        viewholder viewholder = (Project_Rly_Adapter.viewholder) holder;
        viewholder.mProject_author.setText(mMlist.get(position).getAuthor());
        viewholder.mProject_synopsis.setText(mMlist.get(position).getTitle());
        viewholder.mProject_title.setText(mMlist.get(position).getDesc());
        viewholder.mProject_time.setText(mMlist.get(position).getNiceDate());

        boolean img = (boolean) SpUtil.getParam("img", false);
        if(img){
            Glide.with(mActivity).load(R.drawable.wan).into(viewholder.mProject_img);
        }else {
            Glide.with(mActivity).load(mMlist.get(position).getEnvelopePic()).into(viewholder.mProject_img);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener!=null){
                    mOnClickListener.onClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMlist.size();
    }

    class viewholder extends RecyclerView.ViewHolder {
        private final TextView mProject_synopsis;
        private final TextView mProject_title;
        private final TextView mProject_time;
        private final TextView mProject_author;
        private final ImageView mProject_img;

        public viewholder(View itemView) {
            super(itemView);
            mProject_author = itemView.findViewById(R.id.project_author);
            mProject_synopsis = itemView.findViewById(R.id.project_synopsis);
            mProject_title = itemView.findViewById(R.id.project_title);
            mProject_time = itemView.findViewById(R.id.project_time);
            mProject_img = itemView.findViewById(R.id.project_img);
        }
    }

    private onClickListener mOnClickListener;

    public void setOnClickListener(onClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface onClickListener{
        void onClick(int position);
    }
}
