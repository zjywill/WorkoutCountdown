package com.comic.workoutcountdown;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.comic.workoutcountdown.utils.Utils;

import java.lang.reflect.Field;

/**
 * Created by zjy on 11/18/14.
 */
public class EditActivity extends Activity {

    private View mWorkoutPanel;
    private TextView mWorkoutTitle;
    private NumberPicker mWorkoutMin;
    private NumberPicker mWorkoutSecond;

    private View mPreparePanel;
    private TextView mPrepareTitle;
    private NumberPicker mPrepareMin;
    private NumberPicker mPrepareSecond;

    private View mRestPanel;
    private TextView mRestTitle;
    private NumberPicker mRestMin;
    private NumberPicker mRestSecond;

    private View mSetPanel;
    private TextView mSetTitle;
    private NumberPicker mSetPicker;

    private View mRepetPanel;
    private TextView mRepetTitle;
    private NumberPicker mRepetPicker;


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

        mWorkoutTitle = (TextView) mWorkoutPanel.findViewById(R.id.title_text);
        mWorkoutTitle.setText(R.string.workout_title);

        mWorkoutMin.setMaxValue(60);
        mWorkoutMin.setMinValue(0);
        mWorkoutMin.setValue(1);
        mWorkoutMin.setWrapSelectorWheel(false);
        mWorkoutMin.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(this, mWorkoutMin, getResources().getColor(R.color.primary_text_default_material_light), 30);
        setNumberDivider(this, mWorkoutMin, R.drawable.picker_divider);

        mWorkoutSecond.setMaxValue(60);
        mWorkoutSecond.setMinValue(0);
        mWorkoutSecond.setWrapSelectorWheel(false);
        mWorkoutSecond.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(this, mWorkoutSecond, getResources().getColor(R.color.primary_text_default_material_light), 30);
        setNumberDivider(this, mWorkoutSecond, R.drawable.picker_divider);


        mPreparePanel = (View) findViewById(R.id.prepare_time_picker);
        mPrepareMin = (NumberPicker) mPreparePanel.findViewById(R.id.min);
        mPrepareSecond = (NumberPicker) mPreparePanel.findViewById(R.id.second);

        mPrepareTitle = (TextView) mPreparePanel.findViewById(R.id.title_text);
        mPrepareTitle.setText(R.string.prepare_title);

        mPrepareMin.setMaxValue(60);
        mPrepareMin.setMinValue(0);
        mPrepareMin.setWrapSelectorWheel(false);
        mPrepareMin.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(this, mPrepareMin, getResources().getColor(R.color.primary_text_default_material_light), 30);
        setNumberDivider(this, mPrepareMin, R.drawable.picker_divider);

        mPrepareSecond.setMaxValue(60);
        mPrepareSecond.setMinValue(0);
        mPrepareSecond.setValue(20);
        mPrepareSecond.setWrapSelectorWheel(false);
        mPrepareSecond.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(this, mPrepareSecond, getResources().getColor(R.color.primary_text_default_material_light), 30);
        setNumberDivider(this, mPrepareSecond, R.drawable.picker_divider);

        mRestPanel = (View) findViewById(R.id.rest_time_picker);
        mRestMin = (NumberPicker) mRestPanel.findViewById(R.id.min);
        mRestSecond = (NumberPicker) mRestPanel.findViewById(R.id.second);

        mRestTitle = (TextView) mRestPanel.findViewById(R.id.title_text);
        mRestTitle.setText(R.string.rest_title);

        mRestMin.setMaxValue(60);
        mRestMin.setMinValue(0);
        mRestMin.setWrapSelectorWheel(false);
        mRestMin.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(this, mRestMin, getResources().getColor(R.color.primary_text_default_material_light), 30);
        setNumberDivider(this, mRestMin, R.drawable.picker_divider);

        mRestSecond.setMaxValue(60);
        mRestSecond.setMinValue(0);
        mRestSecond.setValue(30);
        mRestSecond.setWrapSelectorWheel(false);
        mRestSecond.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(this, mRestSecond, getResources().getColor(R.color.primary_text_default_material_light), 30);
        setNumberDivider(this, mRestSecond, R.drawable.picker_divider);

        mSetPanel = (View) findViewById(R.id.set_picker);
        mSetTitle = (TextView) mSetPanel.findViewById(R.id.title_text);
        mSetPicker = (NumberPicker) mSetPanel.findViewById(R.id.times);

        mSetTitle.setText(R.string.sets_title);

        mSetPicker.setMaxValue(100);
        mSetPicker.setMinValue(0);
        mSetPicker.setValue(8);
        mSetPicker.setWrapSelectorWheel(false);
        mSetPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(this, mSetPicker, getResources().getColor(R.color.primary_text_default_material_light), 30);
        setNumberDivider(this, mSetPicker, R.drawable.picker_divider);

        mRepetPanel = (View) findViewById(R.id.repet_picker);
        mRepetTitle = (TextView) mRepetPanel.findViewById(R.id.title_text);
        mRepetPicker = (NumberPicker) mRepetPanel.findViewById(R.id.times);

        mRepetTitle.setText(R.string.repetitions_title);

        mRepetPicker.setMaxValue(100);
        mRepetPicker.setMinValue(0);
        mRepetPicker.setValue(1);
        mRepetPicker.setWrapSelectorWheel(false);
        mRepetPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(this, mRepetPicker, getResources().getColor(R.color.primary_text_default_material_light), 30);
        setNumberDivider(this, mRepetPicker, R.drawable.picker_divider);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save: {
                this.finish();
            }
            break;
        }
        return true;
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
