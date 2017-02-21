package com.pokkt.demo.utility;

import android.content.Context;
import android.text.TextUtils;

public class Storage {

    public static void storeVideoPoints(Context context, float points) {
        float oldPoints = getVideoPoints(context);
        context.getSharedPreferences("MyVideoPrefs", Context.MODE_PRIVATE).edit()
                .putFloat("points", oldPoints + points).apply();
    }

    public static float getVideoPoints(Context context) {
        return context.getSharedPreferences("MyVideoPrefs", Context.MODE_PRIVATE).getFloat("points", 0);
    }

    public static String getAppId(Context context) {
        return context.getSharedPreferences("MyVideoPrefs", Context.MODE_PRIVATE).getString("appId", "a2717a45b835b5e9f50284a38d62a74e");// video demo
    }

    public static void setAppId(Context context, String appId) {
        if (!TextUtils.isEmpty(appId)) {
            context.getSharedPreferences("MyVideoPrefs", Context.MODE_PRIVATE).edit().putString("appId", appId).apply();
        }
    }

    public static String getSecurityKey(Context context) {
        return context.getSharedPreferences("MyVideoPrefs", Context.MODE_PRIVATE).getString("securityKey", "iJ02lJss0M"); // video demo
    }

    public static void setSecurityKey(Context context, String appId) {
        if (!TextUtils.isEmpty(appId)) {
            context.getSharedPreferences("MyVideoPrefs", Context.MODE_PRIVATE).edit().putString("securityKey", appId).apply();
        }
    }

}