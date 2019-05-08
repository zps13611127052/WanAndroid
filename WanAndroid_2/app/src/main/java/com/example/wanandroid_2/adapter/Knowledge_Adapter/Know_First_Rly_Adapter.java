package com.example.wanandroid_2.adapter.Knowledge_Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wanandroid_2.R;
import com.example.wanandroid_2.bean.KnowBean;
import com.example.wanandroid_2.widgth.ColorUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张十八 on 2019/4/30.
 */

public class Know_First_Rly_Adapter extends RecyclerView.Adapter {
    private final FragmentActivity mActivity;
    public final ArrayList<KnowBean.DataBean> mMlist;

    public Know_First_Rly_Adapter(FragmentActivity activity, ArrayList<KnowBean.DataBean> mlist) {
        mActivity = activity;
        mMlist = mlist;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.know_first_buju, null);
        viewholder viewholder = new viewholder(inflate);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        viewholder viewholder = (Know_First_Rly_Adapter.viewholder) holder;
        List<KnowBean.DataBean.ChildrenBean> children = mMlist.get(position).getChildren();
        StringBuffer st = new StringBuffer();
        for (int i = 0; i < children.size(); i++) {
            String name = children.get(i).getName();
            st.append(name);
            st.append("  ");

        }
        viewholder.mKnow_name.setText(mMlist.get(position).getName());
        viewholder.mKnow_name.setTextColor(Color.parseColor(ColorUtil.getRandomColor()));
        viewholder.mKnow_child_name.setText(st);

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
        private final TextView mKnow_name;
        private final TextView mKnow_child_name;

        public viewholder(View itemView) {
            super(itemView);
            mKnow_name = itemView.findViewById(R.id.know_name);
            mKnow_child_name = itemView.findViewById(R.id.know_child_name);
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
