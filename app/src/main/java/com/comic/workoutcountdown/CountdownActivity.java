package com.comic.workoutcountdown;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.comic.workoutcountdown.utils.Utils;

/**
 * Created by zjy on 11/19/14.
 */
public class CountdownActivity extends Activity {

    private CountdownData mCountdownData;

    private RoundProgress mRoundProgress;
    private TextView mCurrentTitle;
    private TextView mCurrentRemain;
    private TextView mTotalTimeText;

    private int mPrepareColor;
    private int mWorkoutColor;
    private int mRestColor;

    private long mTotalTime;

    private CountDownTimer mPrepareTimer;
    private CountDownTimer mWorkoutTimer;
    private CountDownTimer mRestTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_page);
        getExtraData();
        initRes();
        initViews();

        mTotalTimeText.setText(Utils.formatTimeText(mTotalTime));

        if (mCountdownData != null) {
            mRoundProgress.setMaxProgress((int) mCountdownData.getPrepareTime() - 2);
            mRoundProgress.setRingColor(mPrepareColor);

            mPrepareTimer = new CountDownTimer(mCountdownData.getPrepareTime() * 1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    long step = millisUntilFinished / 1000;
                    int timePass = (int) (mCountdownData.getPrepareTime() - step - 1);
                    Loge.d("PrepareTimer onTick: " + timePass);
                    mRoundProgress.setProgress(timePass);
                    mCurrentRemain.setText(Utils.formatTimeText(timePass));
                }

                @Override
                public void onFinish() {
                }
            };
            mRoundProgress.setProgress(0);
            mPrepareTimer.start();
        }
    }

    private void getExtraData() {
        Intent extra = getIntent();
        if (extra != null) {
            mCountdownData = new CountdownData();
            mCountdownData.setId(extra.getLongExtra(CountdownData.KEY_TIMER_ID, 0));
            mCountdownData.setName(extra.getStringExtra(CountdownData.KEY_TIMER_WORKOUT_NAME));
            mCountdownData.setWorkoutTime(extra.getLongExtra(CountdownData.KEY_TIMER_WORKOUT_TIME, 0));
            mCountdownData.setPrepareTime(extra.getLongExtra(CountdownData.KEY_TIMER_PREPARE_TIME, 0));
            mCountdownData.setRestTime(extra.getLongExtra(CountdownData.KEY_TIMER_REST_TIME, 0));
            mCountdownData.setSets(extra.getIntExtra(CountdownData.KEY_TIMER_SETS, 0));
            mCountdownData.setRepets(extra.getIntExtra(CountdownData.KEY_TIMER_REPET, 0));
            mCountdownData.setCreateTime(extra.getLongExtra(CountdownData.KEY_TIMER_CREATE_DATE, 0));
            mCountdownData.printData();

            mTotalTime = mCountdownData.getPrepareTime() + (mCountdownData.getWorkoutTime() + mCountdownData.getRestTime()) * mCountdownData.getSets() * mCountdownData.getRepets();
        }
    }

    private void initViews() {
        mRoundProgress = (RoundProgress) findViewById(R.id.round_progress);
        mCurrentTitle = (TextView) findViewById(R.id.current_title);
        mCurrentRemain = (TextView) findViewById(R.id.current_time);
        mTotalTimeText = (TextView) findViewById(R.id.total_time_text);
    }

    private void initRes() {
        mPrepareColor = getResources().getColor(R.color.yellow_500);
        mWorkoutColor = getResources().getColor(R.color.green_500);
        mRestColor = getResources().getColor(R.color.red_500);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
