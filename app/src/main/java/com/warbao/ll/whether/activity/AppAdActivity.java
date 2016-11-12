package com.warbao.ll.whether.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.warbao.ll.whether.R;
import com.warbao.ll.whether.adapters.AppAdAdpter;
import com.warbao.ll.whether.util.AppAdItem;
import com.warbao.ll.whether.util.TestString;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class AppAdActivity extends AppCompatActivity {
    private String TAG = "AppAdActivity";

    private RecyclerView recyclerView;
    private List<AppAdItem> appAdItemList = new ArrayList<AppAdItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_ad);

        findview();
        init();
    }

    private void findview(){
        recyclerView = (RecyclerView)findViewById(R.id.app_ad_recyclerview);
    }

    private void init(){
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        initData();
        //SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        //recyclerView.addItemDecoration(decoration);
        AppAdAdpter appAdAdpter = new AppAdAdpter(appAdItemList);
        recyclerView.setAdapter(appAdAdpter);

    }

    private void initData(){
        try{
            JSONArray jsonArray = new JSONArray(TestString.adjson);
            for (int i=0;i<jsonArray.length();i++){
                AppAdItem appAdItem = new AppAdItem();
                appAdItem.iconsrc = jsonArray.getJSONArray(i).getString(0);
                appAdItem.appname = jsonArray.getJSONArray(i).getString(1);
                appAdItem.pingfen = jsonArray.getJSONArray(i).getString(2);
                appAdItem.downloadsrc = jsonArray.getJSONArray(i).getString(3);
                appAdItemList.add(appAdItem);
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
