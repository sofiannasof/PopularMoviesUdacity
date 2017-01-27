package com.popularmovies.udacity.android.popularmoviesudacity.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by smenesid on 26-Jan-17.
 */

public class SettingsUtils {

    public static final String PREF_ORDER_BY = "pref_order_by";
    public static final String PREF_FIRST_TIME = "pref_welcome_done";
    private static final String LOG_TAG = SettingsUtils.class.getName();

    public static void setPreferencesChange(Context context, boolean isPreferencesChanged) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(PREF_ORDER_BY, isPreferencesChanged).apply();
    }

    public static boolean isPreferencesChanged(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_ORDER_BY, true);
    }

    public static boolean isDefaultSortOrder(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_ORDER_BY, false);
    }

    public static String getDefaultSortOrder(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(PREF_ORDER_BY, "popular");
    }

    public static void registerOnSharedPreferenceChangeListener(final Context context,
                                                                SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.registerOnSharedPreferenceChangeListener(listener);
    }

    public static boolean isFirstRunProcessComplete(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_FIRST_TIME, false);
    }

    public static void markFirstRunProcessesDone(final Context context, boolean newValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_FIRST_TIME, newValue).apply();
    }
}
