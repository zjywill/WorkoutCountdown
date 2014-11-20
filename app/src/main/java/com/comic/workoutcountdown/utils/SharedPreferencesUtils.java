package com.comic.workoutcountdown.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {

    public static final int SOUND_TYPE_TICK = 1;
    public static final int SOUND_TYPE_VOICE = 0;

    public static final String PREFERENCES_NAME = "workoutcountdown";

    public static void enableVibration(Context context, long time) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putBoolean("vibration", true);
        editor.commit();
    }

    public static void disableVibration(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putBoolean("vibration", false);
        editor.commit();
    }

    public static boolean getVibration(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        return pref.getBoolean("vibration", true);
    }

    public static void enableSound(Context context, long time) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putBoolean("sound", true);
        editor.commit();
    }

    public static void disableSound(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putBoolean("sound", false);
        editor.commit();
    }

    public static boolean getSound(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        return pref.getBoolean("sound", true);
    }


    public static void setSoundType(Context context, int type) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        Editor editor = pref.edit();
        editor.putInt("sound_type", type);
        editor.commit();
    }

    public static int getSoundType(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        return pref.getInt("sound_type", 0);
    }


}
