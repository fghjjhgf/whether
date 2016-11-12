package com.warbao.ll.whether;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.warbao.ll.whether.activity.AddPosActivity;
import com.warbao.ll.whether.adapters.WeekWhetherAdapter;
import com.warbao.ll.whether.dialog.AdDialog;
import com.warbao.ll.whether.util.InitialInstrust;
import com.warbao.ll.whether.util.TestString;
import com.warbao.ll.whether.util.WhetherContent;
import com.warbao.ll.whether.util.WhetherData;
import com.warbao.ll.whether.util.WhetherMes;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String TAG = "MainActivity";

    private SwipeRefreshLayout main_swiperefreshlayout;
    private TextView now_temperature;
    private TextView type_textview;
    private TextView ad_textview;
    private GridView week_whether_gridview;
    private TextView today_textview;

    private AdDialog.Builder builder;
    private List<WhetherContent> whetherContentList = new ArrayList<WhetherContent>();

    private String whetherresult = null;
    private List<WhetherData> whetherDataList;
    private WhetherMes whetherMes;
    private String pos = null;

    private final int NET_MSG = 101;
    private String todaytext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        findview();
        init();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(network).start();

    }

    /***
     * Stop location service
     */


    public String getCityName(){
        Log.d(TAG, "getCityName: ");
        LocationManager locationManager;
        String contextString = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getApplicationContext().getSystemService(contextString);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String cityName = null;
        // 取得效果最好的criteria
        Log.d(TAG, "getCityName: 1");
        String provider = locationManager.getBestProvider(criteria, true);
        if (provider == null) {
            return null;
        }
        Log.d(TAG, "getCityName: 2");
        // 得到坐标相关的信息
        Location location = null;
        try{
            location = locationManager.getLastKnownLocation(provider);
        }catch (SecurityException se){
            se.printStackTrace();
        }
        Log.d(TAG, "getCityName: 3");
        if (location == null) {
            return null;
        }
        Log.d(TAG, "getCityName: 4");
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            // 更具地理环境来确定编码
            Geocoder gc = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                // 取得地址相关的一些信息\经度、纬度
                List<Address> addresses = gc.getFromLocation(latitude, longitude, 1);
                StringBuilder sb = new StringBuilder();
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    sb.append(address.getLocality()).append("\n");
                    cityName = sb.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "getCityName: " + cityName);
        return cityName;
    }

    private void setTodayDate(){
        int mMonth;
        int mDay;
        final Calendar c = Calendar.getInstance();
        mMonth = c.get(Calendar.MONTH)+1;//获取当前月份
        mDay = c.get(Calendar.DAY_OF_MONTH);//获取当前月份的日期号码
        String date = mMonth+"月"+mDay+"日";
        today_textview.setText(date);
    }

    private void findview(){
        main_swiperefreshlayout = (SwipeRefreshLayout)findViewById(R.id.main_swiperefreshlayout);
        ad_textview = (TextView)findViewById(R.id.main_ad_text);
        week_whether_gridview = (GridView)findViewById(R.id.week_whether_gridview);
        now_temperature = (TextView)findViewById(R.id.now_temperature);
        type_textview = (TextView)findViewById(R.id.whether_type_textview);
        today_textview = (TextView)findViewById(R.id.week_date);
    }

    private void init(){
        new InitialInstrust(this);
        main_swiperefreshlayout.setColorSchemeResources(R.color.black);

        int width = InitialInstrust.winrect.width()/5;
        week_whether_gridview.setColumnWidth(width);
        WeekWhetherAdapter weekWhetherAdapter = new WeekWhetherAdapter(this, whetherContentList);
        week_whether_gridview.setAdapter(weekWhetherAdapter);

        builder = new AdDialog.Builder(this);
        builder.setTitle("提示");
        builder.setDownload(TestString.imagesrc);
        builder.setImageSrc(TestString.imagesrc);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
            }
        });

        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        setTodayDate();
    }

    private void setWhetherMes(){
        WhetherData whetherData = new WhetherData();
        if (whetherMes.isCorrect(whetherresult)){
            String wendu = whetherMes.getWendu(whetherresult);
            wendu += "℃";
            now_temperature.setText(wendu);

            whetherData = whetherDataList.get(0);
            String type = whetherData.type + " " + whetherData.low + "~" + whetherData.high;

            type_textview.setText(type);
            analysis_week_whether_json();
        }

    }

    private void initListener(){
        //下拉更新信息
        main_swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: ");
                main_swiperefreshlayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "onRefresh: end");
                        main_swiperefreshlayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });
        //广告按钮点击事件
        ad_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: ad_text");
                builder.create().show();
            }
        });
    }

    private void analysis_week_whether_json(){
        WhetherData whetherData = new WhetherData();
        JSONArray jsonArray = new JSONArray();
        for (int i=0;i<5;i++){
            whetherData = whetherDataList.get(i);
            JSONArray jsonArray1 = new JSONArray();
            jsonArray1.put(whetherData.date);
            jsonArray1.put(TestString.whetherimage);
            jsonArray1.put(whetherData.high);
            jsonArray1.put(whetherData.low);
            jsonArray.put(jsonArray1);
        }

        try{
            //JSONArray jsonArray = new JSONArray(TestString.week_whether);
            for (int i=0;i<jsonArray.length();i++){
                WhetherContent whetherContent = new WhetherContent();
                whetherContent.week = jsonArray.getJSONArray(i).getString(0);
                whetherContent.src = jsonArray.getJSONArray(i).getString(1);
                whetherContent.heightest_temperature = jsonArray.getJSONArray(i).getString(2);
                whetherContent.lowest_temperature = jsonArray.getJSONArray(i).getString(3);
                whetherContentList.add(whetherContent);
            }
            Log.d(TAG, "analysis_week_whether_json: " + whetherContentList.size());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    Runnable network = new Runnable() {
        @Override
        public void run() {
            pos = "广州";
            whetherMes = new WhetherMes();
            whetherresult = whetherMes.getMainMes(pos);
            Log.d("onResume",whetherresult);
            whetherDataList = whetherMes.getWhetherDataList();

            myhandle.sendEmptyMessage(NET_MSG);
        }
    };

    Handler myhandle = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case NET_MSG:
                    getCityName();
                    setWhetherMes();
                    break;
            }
        }
    };


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_app_ad) {
            Intent i = new Intent(this, AddPosActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //抽屉中的点击事件
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.drawer_add) {
            // Handle the camera action
        } else if (id == R.id.drawer_position) {

        } else if (id == R.id.drawer_setting) {

        } else if (id == R.id.drawer_feedback) {

        } else if (id == R.id.drawer_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
