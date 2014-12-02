package com.comic.workoutcountdown;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.comic.workoutcountdown.utils.DatabaseUtils;
import com.comic.workoutcountdown.utils.Utils;

import java.lang.reflect.Field;

/**
 * Created by zjy on 11/18/14.
 */
public class EditActivity extends Activity {

    private EditText mNameEdit;

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

    private CountdownData mCountdownData;

    private boolean mEdit = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_page);
        getExtraData();
        initViews();
    }

    private void getExtraData() {
        Intent extra = getIntent();
        mCountdownData = new CountdownData();
        if (extra != null) {
            if (extra.hasExtra(CountdownData.KEY_TIMER_ID)) {
                mEdit = true;
            }
            mCountdownData.setId(extra.getLongExtra(CountdownData.KEY_TIMER_ID, 0));
            mCountdownData.setName(extra.getStringExtra(CountdownData.KEY_TIMER_WORKOUT_NAME));
            mCountdownData.setWorkoutTime(extra.getLongExtra(CountdownData.KEY_TIMER_WORKOUT_TIME, 60));
            mCountdownData.setPrepareTime(extra.getLongExtra(CountdownData.KEY_TIMER_PREPARE_TIME, 20));
            mCountdownData.setRestTime(extra.getLongExtra(CountdownData.KEY_TIMER_REST_TIME, 30));
            mCountdownData.setSets(extra.getIntExtra(CountdownData.KEY_TIMER_SETS, 8));
            mCountdownData.setRepets(extra.getIntExtra(CountdownData.KEY_TIMER_REPET, 1));
            mCountdownData.setCreateTime(extra.getLongExtra(CountdownData.KEY_TIMER_CREATE_DATE, 0));
        } else {
            mCountdownData.setId(0);
            mCountdownData.setName("");
            mCountdownData.setWorkoutTime(60);
            mCountdownData.setPrepareTime(20);
            mCountdownData.setRestTime(30);
            mCountdownData.setSets(8);
            mCountdownData.setRepets(1);
            mCountdownData.setCreateTime(0);
        }
        mCountdownData.printData();
    }


    private void initViews() {

        mNameEdit = (EditText) findViewById(R.id.name_edit);
        if (mCountdownData.getName() != null && mCountdownData.getName().length() > 0) {
            mNameEdit.setText(mCountdownData.getName());
        }

        mWorkoutPanel = (View) findViewById(R.id.workout_time_picker);
        mWorkoutMin = (NumberPicker) mWorkoutPanel.findViewById(R.id.min);
        mWorkoutSecond = (NumberPicker) mWorkoutPanel.findViewById(R.id.second);

        mWorkoutTitle = (TextView) mWorkoutPanel.findViewById(R.id.title_text);
        mWorkoutTitle.setText(R.string.workout_title);

        mWorkoutMin.setMaxValue(60);
        mWorkoutMin.setMinValue(0);
        mWorkoutMin.setValue((int) mCountdownData.getWorkoutTime() / 60);
        mWorkoutMin.setWrapSelectorWheel(false);
        mWorkoutMin.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(this, mWorkoutMin, getResources().getColor(R.color.primary_text_default_material_light), 30);
        setNumberDivider(this, mWorkoutMin, R.drawable.picker_divider);

        mWorkoutSecond.setMaxValue(59);
        mWorkoutSecond.setMinValue(0);
        mWorkoutSecond.setValue((int) mCountdownData.getWorkoutTime() % 60);
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
        mPrepareMin.setValue((int) mCountdownData.getPrepareTime() / 60);
        mPrepareMin.setWrapSelectorWheel(false);
        mPrepareMin.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(this, mPrepareMin, getResources().getColor(R.color.primary_text_default_material_light), 30);
        setNumberDivider(this, mPrepareMin, R.drawable.picker_divider);

        mPrepareSecond.setMaxValue(59);
        mPrepareSecond.setMinValue(0);
        mPrepareSecond.setValue((int) mCountdownData.getPrepareTime() % 60);
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
        mRestMin.setValue((int) mCountdownData.getRestTime() / 60);
        mRestMin.setWrapSelectorWheel(false);
        mRestMin.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(this, mRestMin, getResources().getColor(R.color.primary_text_default_material_light), 30);
        setNumberDivider(this, mRestMin, R.drawable.picker_divider);

        mRestSecond.setMaxValue(59);
        mRestSecond.setMinValue(0);
        mRestSecond.setValue((int) mCountdownData.getRestTime() % 60);
        mRestSecond.setWrapSelectorWheel(false);
        mRestSecond.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(this, mRestSecond, getResources().getColor(R.color.primary_text_default_material_light), 30);
        setNumberDivider(this, mRestSecond, R.drawable.picker_divider);

        mSetPanel = (View) findViewById(R.id.set_picker);
        mSetTitle = (TextView) mSetPanel.findViewById(R.id.title_text);
        mSetPicker = (NumberPicker) mSetPanel.findViewById(R.id.times);

        mSetTitle.setText(R.string.sets_title);

        mSetPicker.setMaxValue(20);
        mSetPicker.setMinValue(1);
        mSetPicker.setValue(mCountdownData.getSets());
        mSetPicker.setWrapSelectorWheel(false);
        mSetPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        setNumberPickerTextColor(this, mSetPicker, getResources().getColor(R.color.primary_text_default_material_light), 30);
        setNumberDivider(this, mSetPicker, R.drawable.picker_divider);

        mRepetPanel = (View) findViewById(R.id.repet_picker);
        mRepetTitle = (TextView) mRepetPanel.findViewById(R.id.title_text);
        mRepetPicker = (NumberPicker) mRepetPanel.findViewById(R.id.times);

        mRepetTitle.setText(R.string.repetitions_title);

        mRepetPicker.setMaxValue(20);
        mRepetPicker.setMinValue(1);
        mRepetPicker.setValue(mCountdownData.getRepets());
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
                getValuesAndSave();
                this.setResult(Activity.RESULT_OK);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EditActivity.this.finish();
                    }
                }, 200);
            }
            break;
        }
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imeManager = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
                imeManager.hideSoftInputFromWindow(mNameEdit.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 200);
    }

    private void getValuesAndSave() {

        final String name = mNameEdit.getText().toString();
        final long workoutTime = mWorkoutMin.getValue() * 60 + mWorkoutSecond.getValue();
        final long prepareTime = mPrepareMin.getValue() * 60 + mPrepareSecond.getValue();
        final long restTime = mRestMin.getValue() * 60 + mRestSecond.getValue();
        final int sets = mSetPicker.getValue();
        final int repets = mRepetPicker.getValue();

        new Thread(new Runnable() {
            @Override
            public void run() {
                CountdownData countdownData = new CountdownData();
                countdownData.setId(mCountdownData.getId());
                countdownData.setName(name);
                countdownData.setWorkoutTime(workoutTime);
                countdownData.setPrepareTime(prepareTime);
                countdownData.setRestTime(restTime);
                countdownData.setSets(sets);
                countdownData.setRepets(repets);
                countdownData.setCreateTime(System.currentTimeMillis());

                if (mEdit) {
                    DatabaseUtils.updateCountdown(EditActivity.this, countdownData);
                } else {
                    DatabaseUtils.saveCountdown(EditActivity.this, countdownData);
                }
            }
        }).start();
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
