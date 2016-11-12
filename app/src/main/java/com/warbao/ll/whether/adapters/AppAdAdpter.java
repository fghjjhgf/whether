package com.warbao.ll.whether.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.warbao.ll.whether.R;
import com.warbao.ll.whether.util.AppAdItem;
import com.warbao.ll.whether.util.InitialInstrust;

import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */
public class AppAdAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AppAdItem> appAdItemList;

    public AppAdAdpter(List<AppAdItem> appAdItemList){
        this.appAdItemList = appAdItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.app_ad_item_layout, parent, false);
        return new AppAdItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AppAdItemViewHolder appAdItemViewHolder = (AppAdItemViewHolder)holder;
        InitialInstrust.imageLoader.displayImage(appAdItemList.get(position).iconsrc,appAdItemViewHolder.iconimage);
        appAdItemViewHolder.appname.setText(appAdItemList.get(position).appname);
        appAdItemViewHolder.pingfen.setText(appAdItemList.get(position).pingfen);

    }

    @Override
    public int getItemCount() {
        return appAdItemList.size();
    }

    public class AppAdItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView iconimage;
        public TextView appname;
        public TextView pingfen;
        public Button downloadbutton;

        public AppAdItemViewHolder(View itemView) {
            super(itemView);
            iconimage = (ImageView)itemView.findViewById(R.id.app_ad_item_imageview);
            appname = (TextView)itemView.findViewById(R.id.app_ad_item_appname);
            pingfen = (TextView)itemView.findViewById(R.id.app_ad_item_pingfen);
            downloadbutton = (Button)itemView.findViewById(R.id.app_ad_item_download);
        }
    }
}
