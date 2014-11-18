package com.comic.workoutcountdown;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.comic.workoutcountdown.utils.Utils;

import java.lang.reflect.Field;

/**
 * Created by zjy on 11/18/14.
 */
public class EditActivity extends Activity {

    private View mWorkoutPanel;
    private NumberPicker mWorkoutMin;
    private NumberPicker mWorkoutSecond;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_page);
        initViews();
    }


    private void initViews() {

        mWorkoutPanel = (View) findViewById(R.id.workout_time_picker);
        mWorkoutMin = (NumberPicker) mWorkoutPanel.findViewById(R.id.min);
        mWorkoutSecond = (NumberPicker) mWorkoutPanel.findViewById(R.id.second);

        mWorkoutMin.setMaxValue(60);
        mWorkoutMin.setMinValue(0);
        mWorkoutMin.setWrapSelectorWheel(false);
        setNumberPickerTextColor(this, mWorkoutMin, getResources().getColor(R.color.primary_text_default_material_light), 30);
        setNumberDivider(this, mWorkoutMin, R.drawable.picker_divider);

        mWorkoutSecond.setMaxValue(60);
        mWorkoutSecond.setMinValue(0);
        mWorkoutSecond.setWrapSelectorWheel(false);
        setNumberPickerTextColor(this, mWorkoutSecond, getResources().getColor(R.color.primary_text_default_material_light), 30);
        setNumberDivider(this, mWorkoutSecond, R.drawable.picker_divider);
    }

    public static boolean setNumberPickerTextColor(Context context, NumberPicker numberPicker, int color, int textSize) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setTextSize(Utils.getDensityDimen(context, textSize));
                    ((EditText) child).setTextColor(color);
                    ((EditText) child).setTextSize(textSize);
                    numberPicker.invalidate();
                    return true;
                } catch (NoSuchFieldException e) {
                    Loge.w("setNumberPickerTextColor", e);
                } catch (IllegalAccessException e) {
                    Loge.w("setNumberPickerTextColor", e);
                } catch (IllegalArgumentException e) {
                    Loge.w("setNumberPickerTextColor", e);
                }
            }
        }
        return false;
    }

    public static boolean setNumberDivider(Context context, NumberPicker numberPicker, int drawable) {
        // change divider
        java.lang.reflect.Field[] pickerFields = NumberPicker.class
                .getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    Loge.d("change divider");
                    pf.set(numberPicker, context.getResources().getDrawable(drawable));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }


}
