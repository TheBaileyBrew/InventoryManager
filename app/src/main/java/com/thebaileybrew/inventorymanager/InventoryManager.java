package com.thebaileybrew.inventorymanager;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.CURRENT_ORDER_VALUE;
import static com.thebaileybrew.inventorymanager.data.AllAboutTheConstants.INITIAL_ORDER_CREATED;

public class InventoryManager extends Application {
    public static int myUniqueID = 0;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void setDefaultPrefs(String key, int value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getDefaultPrefs(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, 0);
    }

    public static Boolean checkForDefaultPrefs(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        switch (key) {
            case INITIAL_ORDER_CREATED:
                if (TextUtils.isEmpty(preferences.getString(key, null)) || preferences.getString(key, null) == null) {
                    //If Preference is NULL or EMPTY, return false - no value assigned
                    return false;
                } else {
                    //If Preference is not NULL or EMPTY, return true - yes value assigned
                    return true;
                }
            case CURRENT_ORDER_VALUE:
                return false;
            default:
                return false;
        }

    }
}
