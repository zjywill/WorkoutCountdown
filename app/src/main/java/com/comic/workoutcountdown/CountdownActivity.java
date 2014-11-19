package com.comic.workoutcountdown;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;
import android.widget.TextView;

import com.comic.workoutcountdown.utils.Utils;

/**
 * Created by zjy on 11/19/14.
 */
public class CountdownActivity extends Activity {

    private final static int PREPARATION = 1;
    private final static int WORKOUT = 2;
    private final static int REST = 3;

    private CountdownData mCountdownData;

    private RoundProgress mRoundProgress;
    private TextView mCurrentTitle;
    private TextView mCurrentRemain;
    private TextView mTotalTimeText;

    private int mPrepareColor;
    private int mWorkoutColor;
    private int mRestColor;

    private long mTotalTime;
    private long mTotalTimePlus;

    private CountDownTimer mMainTimer;

    private int mCurrentStatus;

    private long mRemainTime;
    private int mCurrentSet;
    private int mCuttentRepet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.count_page);
        mCurrentStatus = PREPARATION;
        getExtraData();
        initRes();
        initViews();

        mTotalTimeText.setText(Utils.formatTimeText(mTotalTime));


        if (mCountdownData != null) {
            restProgress(mPrepareColor, (int) mCountdownData.getPrepareTime(), R.string.prepare_title);
            mRemainTime = mTotalTime - mCountdownData.getPrepareTime();

            mMainTimer = new CountDownTimer(mTotalTimePlus * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long tick = millisUntilFinished / 1000;
                    mTotalTimeText.setText(Utils.formatTimeText(tick - 1));
                    switch (mCurrentStatus) {
                        case PREPARATION: {
                            long step = tick - mRemainTime - 1;
                            long restep = mCountdownData.getPrepareTime() - step;
                            Loge.d("MainTimer PREPARATION onTick step: " + step);
                            if (restep >= 0 && step >= 0) {
                                mRoundProgress.setProgress((int) restep);

                                if (step == 0) {
                                    mCurrentStatus = WORKOUT;
                                    mRemainTime -= mCountdownData.getWorkoutTime();
                                    restProgress(mWorkoutColor, (int) mCountdownData.getWorkoutTime(), R.string.workout_title);
                                    mCurrentRemain.setText(Utils.formatTimeText(mCountdownData.getWorkoutTime()));
                                } else {
                                    mCurrentRemain.setText(Utils.formatTimeText(step));
                                }
                            }
                        }
                        break;
                        case WORKOUT: {
                            long step = tick - mRemainTime - 1;
                            long restep = mCountdownData.getWorkoutTime() - step;
                            Loge.d("MainTimer WORKOUT onTick step: " + step);

                            if (restep >= 0 && step >= 0) {
                                mRoundProgress.setProgress((int) restep);

                                if (step == 0) {
                                    mCurrentStatus = REST;
                                    mRemainTime -= mCountdownData.getRestTime();
                                    restProgress(mRestColor, (int) mCountdownData.getRestTime(), R.string.rest_title);
                                    mCurrentRemain.setText(Utils.formatTimeText(mCountdownData.getRestTime()));
                                } else {
                                    mCurrentRemain.setText(Utils.formatTimeText(step));
                                }
                            }
                        }
                        break;
                        case REST: {

                            long step = tick - mRemainTime - 1;
                            long restep = mCountdownData.getRestTime() - step;
                            Loge.d("MainTimer WORKOUT onTick step: " + step);
                            Loge.d("MainTimer WORKOUT onTick restep: " + restep);

                            if (restep >= 0 && step >= 0) {
                                mCurrentRemain.setText(Utils.formatTimeText(step));
                                mRoundProgress.setProgress((int) restep);

                                if (step == 0) {
                                    mCurrentStatus = WORKOUT;
                                    mRemainTime -= mCountdownData.getWorkoutTime();
                                    restProgress(mWorkoutColor, (int) mCountdownData.getWorkoutTime(), R.string.workout_title);
                                    mCurrentRemain.setText(Utils.formatTimeText(mCountdownData.getWorkoutTime()));
                                } else {
                                    mCurrentRemain.setText(Utils.formatTimeText(step));
                                }
                            }

                        }
                        break;
                    }
                }

                @Override
                public void onFinish() {
                }
            };
            mRoundProgress.setProgress(0);
            mMainTimer.start();
        }
    }

    private void restProgress(int color, int maxProgress, int titleId) {
        mRoundProgress.setMaxProgress(maxProgress);
        mRoundProgress.setRingColor(color);
        mRoundProgress.setProgress(0);
        mCurrentTitle.setText(titleId);
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
            mTotalTimePlus = mTotalTime + 2;
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
        if (mMainTimer != null) {
            mMainTimer.cancel();
        }
    }
}
