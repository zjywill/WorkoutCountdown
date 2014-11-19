package com.comic.workoutcountdown.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.comic.workoutcountdown.CountdownData;
import com.comic.workoutcountdown.database.TimerProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjy on 11/18/14.
 */
public class DatabaseUtils {

    public static boolean saveCountdown(Context context, CountdownData countdown) {
        if (context != null && countdown != null) {
            ContentResolver contentResolver = context.getContentResolver();
            if (contentResolver == null) {
                return false;
            }

            ContentValues contentValues = new ContentValues();
            contentValues.put(TimerProvider.KEY_TIMER_WORKOUT_NAME, countdown.getName() == null ? "" : countdown.getName());
            contentValues.put(TimerProvider.KEY_TIMER_WORKOUT_TIME, countdown.getWorkoutTime());
            contentValues.put(TimerProvider.KEY_TIMER_REST_TIME, countdown.getRestTime());
            contentValues.put(TimerProvider.KEY_TIMER_PREPARE_TIME, countdown.getPrepareTime());
            contentValues.put(TimerProvider.KEY_TIMER_SETS, countdown.getSets());
            contentValues.put(TimerProvider.KEY_TIMER_REPET, countdown.getRepets());
            contentValues.put(TimerProvider.KEY_TIMER_CREATE_DATE, countdown.getCreateTime());

            contentResolver.insert(TimerProvider.CONTENT_URI_TIMER, contentValues);
            return true;
        }
        return false;
    }

    public static List<CountdownData> getCountDownData(Context context) {
        List<CountdownData> countdowns = new ArrayList<CountdownData>();

        if (context != null) {
            ContentResolver contentResolver = context.getContentResolver();
            if (contentResolver != null) {
                Cursor cursor = contentResolver.query(TimerProvider.CONTENT_URI_TIMER, null, null, null, TimerProvider.KEY_TIMER_CREATE_DATE);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            int idId = cursor.getColumnIndex(TimerProvider.KEY_TIMER_ID);
                            int workoutNameId = cursor.getColumnIndex(TimerProvider.KEY_TIMER_WORKOUT_NAME);
                            int workoutId = cursor.getColumnIndex(TimerProvider.KEY_TIMER_WORKOUT_TIME);
                            int prepareId = cursor.getColumnIndex(TimerProvider.KEY_TIMER_PREPARE_TIME);
                            int restId = cursor.getColumnIndex(TimerProvider.KEY_TIMER_REST_TIME);
                            int setId = cursor.getColumnIndex(TimerProvider.KEY_TIMER_SETS);
                            int repetId = cursor.getColumnIndex(TimerProvider.KEY_TIMER_REPET);
                            int createId = cursor.getColumnIndex(TimerProvider.KEY_TIMER_CREATE_DATE);

                            CountdownData item = new CountdownData();
                            item.setId(cursor.getLong(idId));
                            item.setName(cursor.getString(workoutNameId));
                            item.setWorkoutTime(cursor.getLong(workoutId));
                            item.setPrepareTime(cursor.getLong(prepareId));
                            item.setRestTime(cursor.getLong(restId));
                            item.setSets(cursor.getInt(setId));
                            item.setRepets(cursor.getInt(repetId));
                            item.setCreateTime(cursor.getLong(createId));

                            countdowns.add(item);

                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
            }
        }

        return countdowns;
    }
}
