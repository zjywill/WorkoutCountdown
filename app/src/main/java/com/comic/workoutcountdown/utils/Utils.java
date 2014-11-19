package com.comic.workoutcountdown.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by zjy on 11/18/14.
 */
public class Utils {

    public static int getDensityDimen(Context context, int dimen) {
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return ((int) (dimen * dm.density));
    }

    public static String formatTimeText(long time) {
        String timeText = "";
        long min = time / 60;
        if (min < 10) {
            timeText = timeText + "0" + min;
        } else {
            timeText = timeText + min;
        }
        timeText = timeText + ":";
        long second = time % 60;
        if (second < 10) {
            timeText = timeText + "0" + second;
        } else {
            timeText = timeText + second;
        }

        return timeText;
    }
}
