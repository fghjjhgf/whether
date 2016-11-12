package com.warbao.ll.whether.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/6.
 */
public class WhetherMes {
    private String TAG = "WhetherMes";
    private static final String url = "http://wthrcdn.etouch.cn/weather_mini?city=";
    private final String res_key = "desc";
    private final String correct_res = "OK";
    private final String falut_res = "invilad-citykey";
    private List<WhetherData> listWhetherData = new ArrayList<WhetherData>();
    public String result = "";

    public WhetherMes(){}

    public String getMainMes(String pos){
        StringBuilder sb = new StringBuilder();
        try {
            String urlstr = url + URLEncoder.encode(pos,"utf-8");
            Log.d(TAG, "getMainMes: "+ urlstr);
            //URL url = new URL("http://wthrcdn.etouch.cn/weather_mini?city=%E5%B9%BF%E5%B7%9E");
            URL url = new URL(urlstr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
            //.setConnectTimeout(5000);
            //connection.setDoInput(true);
            //connection.setDoOutput(true);
            //connection.setUseCaches(true);
            //connection.setRequestMethod("GET");
            //connection.setRequestProperty("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");
            connection.connect();// 连接会话
            // 获取输入流
            //InputStream inputStream = connection.getInputStream();
            //InputStream inputStream = connection.getErrorStream();
            //InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            //BufferedReader br = new BufferedReader(inputStreamReader);
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            result = sb.toString();
            br.close();// 关闭流
            connection.disconnect();// 断开连接

            Log.d(TAG, "getMainMes: " + result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(sb.toString());
        }
        return result;
    }

    public boolean isCorrect(String result){
        try{
            JSONObject jsonObject = new JSONObject(result);
            String res = jsonObject.getString(res_key);
            if (res.equals(correct_res)){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public String getWendu(String result){
        String wendu = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject data = jsonObject.getJSONObject("data");
            wendu = data.getString("wendu");
        }catch (Exception e){
            e.printStackTrace();
        }
        return wendu;
    }

    public List<WhetherData> getWhetherDataList(){
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray forecast = data.getJSONArray("forecast");
            for (int i=0;i<forecast.length();i++){
                WhetherData whetherData = new WhetherData();
                whetherData.fengxiang = forecast.getJSONObject(i).getString("fengxiang");
                whetherData.fengli = forecast.getJSONObject(i).getString("fengli");
                whetherData.high = forecast.getJSONObject(i).getString("high");
                whetherData.high = whetherData.high.substring(3);
                whetherData.low = forecast.getJSONObject(i).getString("low");
                whetherData.low = whetherData.low.substring(3);
                whetherData.type = forecast.getJSONObject(i).getString("type");
                if (i == 0){
                    whetherData.date = "今天";
                }else {
                    whetherData.date = forecast.getJSONObject(i).getString("date");
                    whetherData.date = whetherData.date.substring(whetherData.date.length()-3);
                }

                listWhetherData.add(whetherData);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return listWhetherData;
    }
}
