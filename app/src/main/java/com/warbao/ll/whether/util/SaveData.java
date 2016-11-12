package com.warbao.ll.whether.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/10/19.
 */
public class SaveData {
    private static Context context;
    private final static String whether = "Whether";

    public SaveData(Context context){
        this.context = context;
    }

    public static void sharedpreferences_save(String key, String content){
        SharedPreferences.Editor editor = context.getSharedPreferences(whether,context.MODE_PRIVATE).edit();
        editor.putString(key,content);
        editor.commit();
    }

    public static String sharedpreferences_get(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(whether, context.MODE_PRIVATE);
        String ret = sharedPreferences.getString(key, "");
        return ret;
    }
}
