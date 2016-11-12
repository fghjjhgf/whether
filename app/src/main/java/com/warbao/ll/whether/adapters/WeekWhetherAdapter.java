package com.warbao.ll.whether.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.warbao.ll.whether.R;
import com.warbao.ll.whether.util.WhetherContent;
import com.warbao.ll.whether.util.InitialInstrust;

import java.util.List;

/**
 * Created by Administrator on 2016/10/16.
 */
public class WeekWhetherAdapter extends BaseAdapter {
    private Context context;
    private List<WhetherContent> whetherContentList;

    public WeekWhetherAdapter(Context context,List<WhetherContent> whetherContentList){
        this.context = context;
        this.whetherContentList = whetherContentList;
    }

    @Override
    public int getCount() {
        return whetherContentList.size();
    }

    @Override
    public Object getItem(int position) {
        return whetherContentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WhetherItem whetherItem;
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.week_whether_list_item_layout,null);
            whetherItem = new WhetherItem();
            whetherItem.week = (TextView)convertView.findViewById(R.id.week_whether_weekend);
            whetherItem.week_image = (ImageView)convertView.findViewById(R.id.whether_type_image);
            whetherItem.ht = (TextView)convertView.findViewById(R.id.heightest_temperature);
            whetherItem.lt = (TextView)convertView.findViewById(R.id.lowest_temperature);
            convertView.setTag(whetherItem);
        }else {
            whetherItem = (WhetherItem)convertView.getTag();
        }

        whetherItem.week.setText(whetherContentList.get(position).week);
        InitialInstrust.imageLoader.displayImage(whetherContentList.get(position).src, whetherItem.week_image);
        whetherItem.ht.setText(whetherContentList.get(position).heightest_temperature);
        whetherItem.lt.setText(whetherContentList.get(position).lowest_temperature);

        return convertView;
    }

    protected class WhetherItem{
        public TextView week;
        public ImageView week_image;
        public TextView ht;
        public TextView lt;
    }
}
