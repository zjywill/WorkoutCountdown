package com.comic.workoutcountdown;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.comic.workoutcountdown.utils.SharedPreferencesUtils;
import com.comic.workoutcountdown.utils.Utils;

/**
 * Created by zjy on 11/19/14.
 */
public class CountdownActivity extends Activity implements View.OnClickListener {

    private final static int PREPARATION = 1;
    private final static int WORKOUT = 2;
    private final static int REST = 3;

    private CountdownData mCountdownData;

    private RoundProgress mRoundProgress;
    private TextView mCurrentTitle;
    private TextView mCurrentRemain;
    private TextView mTotalTimeText;
    private TextView mRepetText;
    private TextView mSetText;

    private Button mPauseButton;
    private Button mFinishButton;

    private int mPrepareColor;
    private int mWorkoutColor;
    private int mRestColor;

    private long mTotalTime;
    private long mTotalTimePlus;
    private long mTotalTimeNow;

    private MyCountDownTimer mMainTimer;

    private int mCurrentStatus;

    private long mRemainTime;
    private int mCurrentSet;
    private int mCuttentRepet;

    private boolean mVibrationState;

    private Vibrator mVibrator;

    private FinishDialog mFinishDialog = null;

    private SoundPool mSoundPool;

    private int mDiId;
    private int mPrepareId;
    private int mWorkoutId;
    private int mRestId;
    private int mWorkoutCompleteId;

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

            mCurrentSet = mCountdownData.getSets();
            mCuttentRepet = mCountdownData.getRepets();
            mRepetText.setText(getString(R.string.repetitions_count, mCuttentRepet));
            mSetText.setText(getString(R.string.sets_count, mCurrentSet));

            restProgress((int) mCountdownData.getPrepareTime(), R.string.prepare_title);
            mRemainTime = mTotalTime - mCountdownData.getPrepareTime();

            mMainTimer = new MyCountDownTimer(mTotalTimePlus * 1000, 1000);
            mRoundProgress.setProgress(0);
            mMainTimer.start();
        }
    }

    private void restProgress(int maxProgress, int titleId) {
        mRoundProgress.setMaxProgress(maxProgress);
        mRoundProgress.setProgress(maxProgress);
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

            mTotalTime = (mCountdownData.getPrepareTime() + (mCountdownData.getWorkoutTime() + mCountdownData.getRestTime()) * mCountdownData.getSets()) * mCountdownData.getRepets();
            mTotalTimePlus = mTotalTime + 2;
        }
    }

    private void initViews() {
        mRoundProgress = (RoundProgress) findViewById(R.id.round_progress);
        mCurrentTitle = (TextView) findViewById(R.id.current_title);
        mCurrentRemain = (TextView) findViewById(R.id.current_time);
        mTotalTimeText = (TextView) findViewById(R.id.total_time_text);
        mRepetText = (TextView) findViewById(R.id.repet_text);
        mSetText = (TextView) findViewById(R.id.set_text);
        mPauseButton = (Button) findViewById(R.id.pause_button);
        mFinishButton = (Button) findViewById(R.id.stop_button);

        mPauseButton.setOnClickListener(this);
        mFinishButton.setOnClickListener(this);
    }

    private void initRes() {
        mVibrationState = SharedPreferencesUtils.getVibration(this);
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        mPrepareColor = getResources().getColor(R.color.yellow_500);
        mWorkoutColor = getResources().getColor(R.color.green_500);
        mRestColor = getResources().getColor(R.color.red_500);

        mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        mDiId = mSoundPool.load(this, R.raw.di, 1);
        mPrepareId = mSoundPool.load(this, R.raw.prepare, 1);
        mWorkoutId = mSoundPool.load(this, R.raw.workout, 1);
        mRestId = mSoundPool.load(this, R.raw.rest, 1);
        mWorkoutCompleteId = mSoundPool.load(this, R.raw.workoutcompleted, 1);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.pause_button: {
                if (mMainTimer != null) {
                    mMainTimer.cancel();
                    mMainTimer = null;
                    mPauseButton.setText(R.string.resume);
                    mPauseButton.setTextColor(mWorkoutColor);
                } else {
                    mMainTimer = new MyCountDownTimer(mTotalTimeNow * 1000, 1000);
                    mMainTimer.start();
                    mPauseButton.setText(R.string.pause);
                    mPauseButton.setTextColor(mPrepareColor);
                }
            }
            break;
            case R.id.stop_button: {
                if (mMainTimer != null) {
                    mMainTimer.cancel();
                }

                if (mFinishDialog == null) {
                    mFinishDialog = new FinishDialog(this, R.string.stop_dialog_content);
                }
                mFinishDialog.setContentText(R.string.stop_dialog_content);
                mFinishDialog.show();
            }
            break;
        }
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

    private void vibrate() {
        if (mVibrationState && mVibrator != null) {
            mVibrator.vibrate(600);
        }
    }

    private void vibrateLong() {
        if (mVibrationState && mVibrator != null) {
            mVibrator.vibrate(1200);
            playDong(mWorkoutCompleteId);
        }
    }

    private void playDi() {
        if (mSoundPool != null) {
            AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            int volume = am.getStreamVolume(AudioManager.STREAM_SYSTEM);
            mSoundPool.play(mDiId, volume, volume, 100, 0, 1);
        }
    }


    private void playDong(int id) {
        Loge.d("playDong id: " + id);
        if (mSoundPool != null) {
            AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            int volume = am.getStreamVolume(AudioManager.STREAM_SYSTEM);
            mSoundPool.play(id, volume, volume, 100, 0, 1);
        }
    }


    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long tick = millisUntilFinished / 1000;
            mTotalTimeNow = tick;
            mTotalTimeText.setText(Utils.formatTimeText(tick - 1));
            switch (mCurrentStatus) {
                case PREPARATION: {
                    mRoundProgress.setRingColor(mPrepareColor);
                    long step = tick - mRemainTime - 1;
                    long restep = mCountdownData.getPrepareTime() - step;
                    Loge.d("MainTimer PREPARATION onTick step: " + step);
                    Loge.d("MainTimer PREPARATION onTick restep: " + restep);
                    if (step == mCountdownData.getPrepareTime()) {
                        playDong(mPrepareId);
                    }
                    if (restep >= 0 && step >= 0) {
                        mRoundProgress.setProgress((int) restep);
                        if (step > 0 && step <= 5) {
                            playDi();
                        }
                        if (step == 0) {
                            mCurrentStatus = WORKOUT;
                            mRemainTime -= mCountdownData.getWorkoutTime();
                            restProgress((int) mCountdownData.getWorkoutTime(), R.string.workout_title);
                            mCurrentRemain.setText(Utils.formatTimeText(mCountdownData.getWorkoutTime()));
                            vibrate();
                            playDong(mWorkoutId);
                        } else {
                            mCurrentRemain.setText(Utils.formatTimeText(step));
                        }
                    }
                }
                break;
                case WORKOUT: {
                    mRoundProgress.setRingColor(mWorkoutColor);
                    long step = tick - mRemainTime - 1;
                    long restep = mCountdownData.getWorkoutTime() - step;
                    Loge.d("MainTimer WORKOUT onTick step: " + step);
                    Loge.d("MainTimer WORKOUT onTick restep: " + restep);

                    if (restep >= 0 && step >= 0) {
                        mRoundProgress.setProgress((int) restep);
                        if (step > 0 && step <= 5) {
                            playDi();
                        }
                        if (step == 0) {
                            mCurrentStatus = REST;
                            mRemainTime -= mCountdownData.getRestTime();
                            restProgress((int) mCountdownData.getRestTime(), R.string.rest_title);
                            mCurrentRemain.setText(Utils.formatTimeText(mCountdownData.getRestTime()));
                            vibrate();
                            playDong(mRestId);
                        } else {
                            mCurrentRemain.setText(Utils.formatTimeText(step));
                        }
                    }
                }
                break;
                case REST: {

                    mRoundProgress.setRingColor(mRestColor);

                    long step = tick - mRemainTime - 1;
                    long restep = mCountdownData.getRestTime() - step;
                    Loge.d("MainTimer REST onTick step: " + step);
                    Loge.d("MainTimer REST onTick restep: " + restep);

                    if (restep >= 0 && step >= 0) {
                        mCurrentRemain.setText(Utils.formatTimeText(step));
                        mRoundProgress.setProgress((int) restep);
                        if (step > 0 && step <= 5) {
                            playDi();
                        }
                        if (step == 0) {
                            mCurrentSet--;
                            if (mCurrentSet == 0) {
                                mCuttentRepet--;
                                mRepetText.setText(getString(R.string.repetitions_count, mCuttentRepet));
                                if (mCuttentRepet != 0) {
                                    mCurrentSet = mCountdownData.getSets();
                                    mSetText.setText(getString(R.string.sets_count, mCurrentSet));

                                    mCurrentStatus = PREPARATION;
                                    mRemainTime -= mCountdownData.getPrepareTime();
                                    restProgress((int) mCountdownData.getPrepareTime(), R.string.prepare_title);
                                    mCurrentRemain.setText(Utils.formatTimeText(mCountdownData.getPrepareTime()));
                                } else {
                                    mSetText.setText(getString(R.string.sets_count, mCurrentSet));
                                    mCurrentTitle.setText(R.string.finish);


                                    if (mFinishDialog == null) {
                                        mFinishDialog = new FinishDialog(CountdownActivity.this, R.string.finish_dialog_content);
                                    }
                                    mFinishDialog.setContentText(R.string.finish_dialog_content);
                                    mFinishDialog.show();
                                }
                                vibrateLong();
                            } else {
                                mSetText.setText(getString(R.string.sets_count, mCurrentSet));
                                mCurrentStatus = WORKOUT;
                                mRemainTime -= mCountdownData.getWorkoutTime();
                                restProgress((int) mCountdownData.getWorkoutTime(), R.string.workout_title);
                                mCurrentRemain.setText(Utils.formatTimeText(mCountdownData.getWorkoutTime()));
                                vibrate();
                                playDong(mWorkoutId);
                            }
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
    }
}
