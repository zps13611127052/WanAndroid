package com.example.wanandroid_2.adapter.Knowledge_Adapter;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanandroid_2.R;
import com.example.wanandroid_2.bean.DaoBean;
import com.example.wanandroid_2.bean.Home_Item_Bean;
import com.example.wanandroid_2.bean.Know_Item_Data;
import com.example.wanandroid_2.utils.DaoUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张十八 on 2019/5/4.
 */

public class Know_Item_Adapter extends RecyclerView.Adapter {
    private final FragmentActivity mActivity;
    public final ArrayList<Know_Item_Data.DataBean.DatasBean> mMlist;

    public Know_Item_Adapter(FragmentActivity activity, ArrayList<Know_Item_Data.DataBean.DatasBean> mlist) {

        mActivity = activity;
        mMlist = mlist;
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
        final viewholder viewholder = (Know_Item_Adapter.viewholder) holder;
        viewholder.mAuthor.setText(mMlist.get(position).getAuthor());
        viewholder.mPlatform.setText(mMlist.get(position).getSuperChapterName()+"/"+mMlist.get(position).getChapterName());
        viewholder.mTime.setText(mMlist.get(position).getNiceDate());
        viewholder.mTitle.setText(mMlist.get(position).getTitle());

        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener!=null){
                    mOnClickListener.onClick(position);
                }
            }
        });

        select(viewholder.mXin, (position));//查询数据库

        viewholder.mXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert((position), viewholder.mXin);
            }
        });

    }

    private void insert(int position, ImageView xin) {
        //Home_Item_Bean.DatasBean datasBean = mMlist.get(position);
        Know_Item_Data.DataBean.DatasBean datasBean = mMlist.get(position);
        //Bean daoBean = new Bean();
        DaoBean daoBean = new DaoBean();
        daoBean.setAuthor(datasBean.getAuthor());
        daoBean.setCharname(datasBean.getChapterName());
        daoBean.setCharSuperName(datasBean.getSuperChapterName());
        daoBean.setId((long) datasBean.getId());
        daoBean.setLink(datasBean.getLink());
        daoBean.setNiceDate(datasBean.getNiceDate());
        daoBean.setTitle(datasBean.getTitle());

        collect(xin, daoBean);
    }

    private void collect(ImageView xin, DaoBean daoBean) {
        //插入数据库
        long insert = DaoUtil.getDaoUtils().insert(daoBean);
//判断是否插入成功
        if (insert == -1) {
            boolean delete = DaoUtil.getDaoUtils().delete(daoBean);
            //代表数据库有，不能插入了
            if (delete) {
                //ToastUtil.showShort("取消收藏");
                Toast.makeText(mActivity, "取消收藏", Toast.LENGTH_SHORT).show();
                xin.setImageResource(R.drawable.follow_unselected);
            } else {
                xin.setImageResource(R.mipmap.xin);
            }
        } else {
            //收藏成功 改成小红心
            xin.setImageResource(R.mipmap.xin);
            // ToastUtil.showShort("收藏成功");
            Toast.makeText(mActivity, "收藏成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void select(ImageView xin, int position) {
        //Home_Item_Bean.DatasBean datasBean = mMlist.get(position);
        Know_Item_Data.DataBean.DatasBean datasBean = mMlist.get(position);
        List<DaoBean> beans = DaoUtil.getDaoUtils().query();

        xin.setImageResource(R.drawable.follow_unselected);
        for (int i = 0; i < beans.size(); i++) {
            String link = beans.get(i).getLink();
            if (link.equals(datasBean.getLink())) {
                xin.setImageResource(R.mipmap.xin);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMlist.size();
    }

    class viewholder extends RecyclerView.ViewHolder {

        private final TextView mAuthor;
        private final TextView mPlatform;
        private final TextView mTitle;
        private final TextView mTime;
        private final ImageView mXin;

        public viewholder(View itemView) {
            super(itemView);
            mAuthor = itemView.findViewById(R.id.author);
            mPlatform = itemView.findViewById(R.id.platform);
            mTitle = itemView.findViewById(R.id.title);
            mTime = itemView.findViewById(R.id.time);
            mXin = itemView.findViewById(R.id.xin_img);

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
