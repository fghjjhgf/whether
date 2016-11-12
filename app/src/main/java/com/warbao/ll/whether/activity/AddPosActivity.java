package com.warbao.ll.whether.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.warbao.ll.whether.R;
import com.warbao.ll.whether.adapters.AddPosAdapter;
import com.warbao.ll.whether.util.AddPosItem;
import com.warbao.ll.whether.util.TestString;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class AddPosActivity extends AppCompatActivity {
    private String TAG = "AddPosActivity";
    private RecyclerView recyclerView;
    private List<AddPosItem> addPosItemList = new ArrayList<AddPosItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pos);

        findview();
        init();
    }

    private void findview(){
        recyclerView = (RecyclerView)findViewById(R.id.add_pos_recyclerview);
    }

    private void init(){
        initData();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        AddPosAdapter addPosAdapter = new AddPosAdapter(addPosItemList);
        recyclerView.setAdapter(addPosAdapter);
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);

    }

    private void initData(){
        try{
            JSONArray jsonArray = new JSONArray(TestString.posjson);
            for (int i=0;i<jsonArray.length();i++){
                AddPosItem addPosItem = new AddPosItem();
                addPosItem.pos = jsonArray.getJSONArray(i).getString(0);
                addPosItem.imagesrc = jsonArray.getJSONArray(i).getString(1);
                addPosItem.temperature = jsonArray.getJSONArray(i).getString(2);
                addPosItemList.add(addPosItem);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space=space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space;
        }
    }

}
