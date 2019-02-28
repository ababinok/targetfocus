package com.ofnicon.targetfocus.core;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Set;

public class MySharedPreferences {

    private static final String APP_PREFERENCES = "StopDrinkingSettings";

    static void setStringParameter(Context context, String parameterName, String value) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(parameterName, value);
        editor.apply();
    }

    static String getStringParameter(Context context, String parameterName) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String result = "";
        if (preferences.contains(parameterName)) {
            result = preferences.getString(parameterName, "");
        }
        return result;
    }

    static void setBooleanParameter(Context context, String parameterName, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(parameterName, value);
        editor.apply();
    }

    static boolean getBooleanParameter(Context context, String parameterName) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean result = false;
        if (preferences.contains(parameterName)) {
            result = preferences.getBoolean(parameterName, false);
        }
        return result;
    }

    public static void saveStringSet(Context context, String parameterName, Set<String> value) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(parameterName, value);
        editor.apply();
    }

    public static Set<String> getStringSet(Context context, String parameterName) {
        SharedPreferences preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Set<String> result = null;
        if (preferences.contains(parameterName)) {
            result = preferences.getStringSet(parameterName, null);
        }
        return result;
    }

}
