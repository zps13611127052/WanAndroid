package com.example.wanandroid_2.adapter.collectAdapter;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wanandroid_2.R;
import com.example.wanandroid_2.bean.DaoBean;
import com.example.wanandroid_2.utils.DaoUtil;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by 张十八 on 2019/5/6.
 */

public class Collect_Adapter extends RecyclerView.Adapter {
    private final FragmentActivity mActivity;
    public final List<DaoBean> mList;

    public Collect_Adapter(FragmentActivity activity, List<DaoBean> list) {
        mActivity = activity;
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mActivity).inflate(R.layout.official_item_buju, null);
        viewholder viewholder = new viewholder(inflate);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        viewholder viewholder = (Collect_Adapter.viewholder) holder;
        viewholder.mAuthor.setText(mList.get(position).getAuthor());
        viewholder.mPlatform.setText(mList.get(position).getCharname()+mList.get(position).getCharSuperName());
        viewholder.mTime.setText(mList.get(position).getNiceDate());
        viewholder.mTitle.setText(mList.get(position).getTitle());
        viewholder.mXin.setImageResource(R.mipmap.xin);
        //ImageView xin = LayoutInflater.from(mActivity).inflate(R.layout.official_item_buju, null).findViewById(R.id.xin_img);
        viewholder.mXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoBean daoBean = mList.get(position);
                DaoUtil.getDaoUtils().delete(daoBean);
                mList.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener!=null){
                    mOnClickListener.onListener(position);
                }
            }
        });

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (mOnClickLongListener!=null){
//                    mOnClickLongListener.onClickLong(position);
//                }
//                return false;
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class viewholder extends RecyclerView.ViewHolder {

        private final TextView mAuthor;
        private final TextView mTitle;
        private final TextView mPlatform;
        private final TextView mTime;
        private final ImageView mXin;

        public viewholder(View itemView) {
            super(itemView);
            mAuthor = itemView.findViewById(R.id.author);
            mTitle = itemView.findViewById(R.id.title);
            mPlatform = itemView.findViewById(R.id.platform);
            mTime = itemView.findViewById(R.id.time);
            mXin = itemView.findViewById(R.id.xin_img);
        }
    }

    private onClickListener mOnClickListener;
    public void setOnClickListener(onClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface onClickListener{
        void onListener(int position);
    }

//
//    private onClickLongListener mOnClickLongListener;
//
//    public void setOnClickLongListener(onClickLongListener onClickLongListener) {
//        mOnClickLongListener = onClickLongListener;
//    }
//
//    public interface onClickLongListener{
//        void onClickLong(int position);
//    }

}
