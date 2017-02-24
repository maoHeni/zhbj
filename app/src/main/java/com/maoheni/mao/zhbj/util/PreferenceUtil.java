package com.maoheni.mao.zhbj.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ubuntu on 17-2-24.
 */

public class PreferenceUtil {
    public static String preName = "config";

    public static boolean getBoolean(Context context, String key, boolean defaultValue){
        SharedPreferences sp = context.getSharedPreferences(preName, Context.MODE_PRIVATE);

        return sp.getBoolean(key,defaultValue);
    }

    public static void setBoolean(Context context, String key, boolean value){
        SharedPreferences sp = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }
}
