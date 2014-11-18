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
}
