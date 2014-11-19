package com.comic.workoutcountdown;

/**
 * Created by zjy on 11/19/14.
 */
public class CountdownData {

    public static final String KEY_TIMER_ID = "id";
    public static final String KEY_TIMER_WORKOUT_NAME = "name";
    public static final String KEY_TIMER_WORKOUT_TIME = "workout";
    public static final String KEY_TIMER_REST_TIME = "rest";
    public static final String KEY_TIMER_PREPARE_TIME = "prepare";
    public static final String KEY_TIMER_SETS = "sets";
    public static final String KEY_TIMER_REPET = "repet";
    public static final String KEY_TIMER_CREATE_DATE = "create_time";

    private long mId;
    private String mName;
    private long mWorkoutTime;
    private long mRestTime;
    private long mPrepareTime;
    private int mSets;
    private int mRepets;
    private long mCreateTime;

    public void setId(long mId) {
        this.mId = mId;
    }

    public long getId() {
        return mId;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getName() {
        return mName;
    }

    public void setWorkoutTime(long mWorkoutTime) {
        this.mWorkoutTime = mWorkoutTime;
    }

    public long getWorkoutTime() {
        return mWorkoutTime;
    }

    public void setRestTime(long mRestTime) {
        this.mRestTime = mRestTime;
    }

    public long getRestTime() {
        return mRestTime;
    }

    public void setPrepareTime(long mPrepareTime) {
        this.mPrepareTime = mPrepareTime;
    }

    public long getPrepareTime() {
        return mPrepareTime;
    }

    public void setSets(int mSets) {
        this.mSets = mSets;
    }

    public int getSets() {
        return mSets;
    }

    public void setRepets(int mRepets) {
        this.mRepets = mRepets;
    }

    public int getRepets() {
        return mRepets;
    }


    public void setCreateTime(long mCreateTime) {
        this.mCreateTime = mCreateTime;
    }

    public long getCreateTime() {
        return mCreateTime;
    }

    public void printData() {
        Loge.d("CountdownData name: " + mName);
        Loge.d("CountdownData workoutTime: " + mWorkoutTime);
        Loge.d("CountdownData prepareTime: " + mPrepareTime);
        Loge.d("CountdownData restTime: " + mRestTime);
        Loge.d("CountdownData sets: " + mSets);
        Loge.d("CountdownData repets: " + mRepets);
        Loge.d("CountdownData createTime: " + mCreateTime);
    }

}
