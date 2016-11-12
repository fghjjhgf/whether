package com.warbao.ll.whether.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.warbao.ll.whether.R;
import com.warbao.ll.whether.util.AddPosItem;
import com.warbao.ll.whether.util.InitialInstrust;

import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */
public class AddPosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AddPosItem> addPosItemList;

    public AddPosAdapter(List<AddPosItem> addPosItemList){
        this.addPosItemList = addPosItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_pos_item_layout, parent, false);
        return new AddItemPosViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AddItemPosViewHolder addItemPosViewHolder = (AddItemPosViewHolder)holder;
        addItemPosViewHolder.pos.setText(addPosItemList.get(position).pos);
        InitialInstrust.imageLoader.displayImage(addPosItemList.get(position).imagesrc,addItemPosViewHolder.imageView);
        addItemPosViewHolder.t.setText(addPosItemList.get(position).temperature);
    }

    @Override
    public int getItemCount() {
        return addPosItemList.size();
    }

    public class AddItemPosViewHolder extends RecyclerView.ViewHolder{
        public TextView pos;
        public ImageView imageView;
        public TextView t;

        public AddItemPosViewHolder(View itemView) {
            super(itemView);
            pos = (TextView)itemView.findViewById(R.id.add_pos_item_pos);
            imageView = (ImageView)itemView.findViewById(R.id.add_pos_item_imageview);
            t = (TextView)itemView.findViewById(R.id.add_pos_item_temperature);
        }
    }
}
