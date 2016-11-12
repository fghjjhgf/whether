package com.warbao.ll.whether.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.warbao.ll.whether.R;
import com.warbao.ll.whether.adapters.HitCityAdapter;

import java.util.ArrayList;
import java.util.List;

public class HitPosActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> stringList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hit_pos);

        findview();
        init();
    }

    private void findview(){
        recyclerView = (RecyclerView)findViewById(R.id.hit_pos_recyclerview);
        HitCityAdapter hitCityAdapter = new HitCityAdapter(stringList);
        recyclerView.setAdapter(hitCityAdapter);
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);

    }

    private void init(){
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

    }

    private void initData(){

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
