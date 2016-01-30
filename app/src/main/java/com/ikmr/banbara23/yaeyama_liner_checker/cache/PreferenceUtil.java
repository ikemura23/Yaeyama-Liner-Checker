
package com.ikmr.banbara23.yaeyama_liner_checker.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ikmr.banbara23.yaeyama_liner_checker.ApplicationController;
import com.ikmr.banbara23.yaeyama_liner_checker.Const;

final class PreferenceUtils {

    private static SharedPreferences getDefaultSharedPreferences(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static Context getContext() {
        return ApplicationController.getInstance().getApplicationContext();
    }

    private static SharedPreferences getSharedPreferences() {
        return getContext().getSharedPreferences(Const.PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void saveLong(String key, long value) {
        SharedPreferences.Editor editor = getDefaultSharedPreferences(getContext()).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void saveString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static int loadInt(Context context, String key) {
        return getDefaultSharedPreferences(context).getInt(key, -1);
    }

    protected static String loadString(String key) {
        return getSharedPreferences().getString(key, null);
    }

    protected static long loadLong(String key) {
        return getSharedPreferences().getLong(key, 0);
    }

    public static void remove(Context context, String key) {
        SharedPreferences.Editor editor = getDefaultSharedPreferences(context).edit();
        editor.remove(key);
        editor.apply();
    }

    public static boolean getBoolean(final Context context, final String key) {
        return getDefaultSharedPreferences(context).getBoolean(key, false);
    }

    private PreferenceUtils() {
    }
}
