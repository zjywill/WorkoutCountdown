package com.comic.workoutcountdown;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.comic.workoutcountdown.utils.Utils;

public class RoundProgress extends View {

    private static final int TOTAL_PROGRESS = 100;
    public static final int DEFAULT_COLOR_RING = 0xff0a82fa;

    static final int UI_RING_STROKE_SIZE = 6;

    private float mRingSize;
    private float mStrokeSize;

    private int mProgress;
    private int mMaxProgress;

    private Paint mRingPaint;

    private int mRingColor;


    public RoundProgress(Context context, int ring_color) {
        super(context);
        setWillNotDraw(false);
        mRingColor = ring_color;
        initRes();
        mProgress = 0;
        mMaxProgress = TOTAL_PROGRESS;
    }

    public RoundProgress(Context context, AttributeSet attr) {
        super(context, attr);
        setWillNotDraw(false);
        mRingColor = DEFAULT_COLOR_RING;
        initRes();
        mProgress = 0;
        mMaxProgress = TOTAL_PROGRESS;
    }

    public void setRingColor(int ring_color) {
        mRingColor = ring_color;
        mRingPaint.setColor(mRingColor);
    }

    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
    }

    private void initRes() {
        mStrokeSize = Utils.getDensityDimen(getContext(), UI_RING_STROKE_SIZE);
        mStrokeSize = mStrokeSize * 3 / 4;

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRingSize = getMeasuredWidth() - mStrokeSize;
        float xCenter = getMeasuredWidth() / 2;
        float yCenter = getMeasuredHeight() / 2;

        if (mProgress > 0) {
            float ringRadius = mRingSize / 2;
            RectF oval = new RectF();
            oval.left = (xCenter - ringRadius);
            oval.top = (yCenter - ringRadius);
            oval.right = oval.left + mRingSize;
            oval.bottom = oval.top + mRingSize;

            float percent = ((float) mProgress / mMaxProgress);
            float angle = 360 * percent;

            mRingPaint.setStyle(Style.STROKE);
            canvas.drawArc(oval, -90, angle, false, mRingPaint);

        }
    }

}
