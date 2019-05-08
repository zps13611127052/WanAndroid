package com.example.wanandroid_2.adapter;

import android.content.Context;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.example.wanandroid_2.R;
import com.example.wanandroid_2.bean.DaoBean;
import com.example.wanandroid_2.bean.Home_Banner_Bean;
import com.example.wanandroid_2.bean.Home_Item_Bean;
import com.example.wanandroid_2.utils.DaoUtil;
import com.example.wanandroid_2.webactivity.HomeWebActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张十八 on 2019/4/30.
 */

public class Home_Rly_Adapter extends RecyclerView.Adapter {


    private final FragmentActivity mActivity;
    public final ArrayList<Home_Banner_Bean.DataBean> mBannerList;
    public final ArrayList<Home_Item_Bean.DatasBean> mItemList;
    private int mposition;
    private ArrayList<String> mtitles;

    public Home_Rly_Adapter(FragmentActivity activity, ArrayList<Home_Banner_Bean.DataBean> bannerList, ArrayList<Home_Item_Bean.DatasBean> itemList) {
        mActivity = activity;
        mBannerList = bannerList;
        mItemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mtitles = new ArrayList<>();
        for (Home_Banner_Bean.DataBean bean :mBannerList) {
            mtitles.add(bean.getTitle());
        }
        if (viewType == 1){
            View inflate = LayoutInflater.from(mActivity).inflate(R.layout.home_banner_buju, null);
            viewholderBanner viewholderBanner = new viewholderBanner(inflate);
            return viewholderBanner;
        }else {
            View inflate = LayoutInflater.from(mActivity).inflate(R.layout.home_item_buju, null, false);
            viewholderItem viewholderItem = new viewholderItem(inflate);
            return viewholderItem;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int i = getItemViewType(position);
        if (i == 1){
            viewholderBanner viewholderBanner = (Home_Rly_Adapter.viewholderBanner) holder;
            viewholderBanner.mBanner_id.setImages(mBannerList)
                    .setBannerTitles(mtitles)
                    .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            Home_Banner_Bean.DataBean img = (Home_Banner_Bean.DataBean) path;
                            Glide.with(mActivity).load(img.getImagePath()).into(imageView);
                        }
                    });
            viewholderBanner.mBanner_id.start();

            viewholderBanner.mBanner_id.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent = new Intent(mActivity,HomeWebActivity.class);
                    intent.putExtra("url",mBannerList.get(position).getUrl());
                    intent.putExtra("name",mBannerList.get(position).getTitle());
                    mActivity.startActivity(intent);
                }
            });
        }else{
            final viewholderItem viewholderItem = (Home_Rly_Adapter.viewholderItem) holder;
            mposition = position;
            if (mBannerList.size()>0){
                mposition = position-1;
            }
            viewholderItem.mAuthor.setText(mItemList.get(mposition).getAuthor());
            viewholderItem.mPlatform.setText(mItemList.get(mposition).getChapterName()+"/"+mItemList.get(mposition).getSuperChapterName());
            viewholderItem.mTime.setText(mItemList.get(mposition).getNiceDate());
            viewholderItem.mTitle.setText(mItemList.get(mposition).getTitle());

            if (mItemList.get(mposition).getTags().size()>0){
                viewholderItem.mDao.setVisibility(View.VISIBLE);
            }else {
                viewholderItem.mDao.setVisibility(View.GONE);
            }

            if (mItemList.get(mposition).isFresh()){
                viewholderItem.mNews.setVisibility(View.VISIBLE);
            }else {
                viewholderItem.mNews.setVisibility(View.GONE);
            }

            if (mItemList.get(mposition).getTags().size()>0&&mItemList.get(mposition).isFresh()){
                viewholderItem.mNews.setVisibility(View.VISIBLE);
                viewholderItem.mDao.setVisibility(View.GONE);
            }
            viewholderItem.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickListener!=null){
                        mOnClickListener.onClick(position-1);
                    }
                }
            });

            select(viewholderItem.mXin, (position - 1));//查询数据库

            viewholderItem.mXin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insert((position - 1), viewholderItem.mXin);
                }
            });
        }

    }

    private void insert(int position, ImageView xin) {
        Home_Item_Bean.DatasBean datasBean = mItemList.get(position);
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
        Home_Item_Bean.DatasBean datasBean = mItemList.get(position);
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
        if (mBannerList.size()>0){
            return mItemList.size()+1;
        }
        return mItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 1;
        }
        return 2;
    }

    class viewholderBanner extends RecyclerView.ViewHolder {

        private final Banner mBanner_id;

        public viewholderBanner(View itemView) {
            super(itemView);
            mBanner_id = itemView.findViewById(R.id.banner_id);
        }
    }

    class viewholderItem extends RecyclerView.ViewHolder{

        private final TextView mAuthor;
        private final TextView mPlatform;
        private final TextView mTitle;
        private final TextView mTime;
        private final TextView mNews;
        private final TextView mDao;
        private final ImageView mXin;

        public viewholderItem(View itemView) {
            super(itemView);
            mAuthor = itemView.findViewById(R.id.author);
            mPlatform = itemView.findViewById(R.id.platform);
            mTitle = itemView.findViewById(R.id.title);
            mTime = itemView.findViewById(R.id.time);
            mNews = itemView.findViewById(R.id.news_gone);
            mDao = itemView.findViewById(R.id.daohang_gone);
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
