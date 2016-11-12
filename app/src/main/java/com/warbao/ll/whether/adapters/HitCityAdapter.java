package com.warbao.ll.whether.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warbao.ll.whether.R;

import java.util.List;

/**
 * Created by Administrator on 2016/10/23.
 */
public class HitCityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> stringList;

    public HitCityAdapter(List<String> stringList){
        this.stringList = stringList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_pos_item_layout, parent, false);
        return new HitCityItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HitCityItemViewHolder hitCityItemViewHolder = (HitCityItemViewHolder)holder;
        hitCityItemViewHolder.textView.setText(stringList.get(position));
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class HitCityItemViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;

        public HitCityItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.hit_city_item_textview);
        }
    }
}
