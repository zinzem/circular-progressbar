package com.zinzem.circularprogressbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

public class CircularProgressBar extends View {

    private float mProgress = 0f;
    private int mStartAngle = -90;
    private int mSweepAngle = 360;
    private int mAnimationDuration = 0;
    private Interpolator mAnimationInterpolator = new AccelerateDecelerateInterpolator();

    private RectF mRectF;
    private Paint mProgressPaint;
    private Paint mBackgroundPaint;

    private ValueAnimator mValueAnimator = new ValueAnimator();

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        float progressStrokeWidth = getResources().getDimension(R.dimen.default_progress_stroke_width);
        int progressColor = Color.BLACK;
        float backgroundStrokeWidth = getResources().getDimension(R.dimen.default_background_stroke_width);
        int backgroundColor = Color.GRAY;

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircularProgressBar, 0, 0);
        try {
            mProgress = typedArray.getFloat(R.styleable.CircularProgressBar_progress, mProgress);
            mStartAngle = typedArray.getInt(R.styleable.CircularProgressBar_start_angle, mStartAngle);
            mSweepAngle = typedArray.getInt(R.styleable.CircularProgressBar_sweep_angle, mSweepAngle);
            progressStrokeWidth = typedArray.getDimension(R.styleable.CircularProgressBar_progressbar_width, progressStrokeWidth);
            progressColor = typedArray.getInt(R.styleable.CircularProgressBar_progressbar_color, progressColor);
            backgroundStrokeWidth = typedArray.getDimension(R.styleable.CircularProgressBar_progressbar_background_width, backgroundStrokeWidth);
            backgroundColor = typedArray.getInt(R.styleable.CircularProgressBar_progressbar_background_color, backgroundColor);
            mAnimationDuration = typedArray.getInt(R.styleable.CircularProgressBar_animation_duration, mAnimationDuration);
        } finally {
            typedArray.recycle();
        }

        mRectF = new RectF();

        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(progressColor);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(progressStrokeWidth);

        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(backgroundColor);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setStrokeWidth(backgroundStrokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int minDimension = Math.min(width, height);
        float thickestStroke = (mProgressPaint.getStrokeWidth() > mBackgroundPaint.getStrokeWidth()) ? mProgressPaint.getStrokeWidth() : mBackgroundPaint.getStrokeWidth();

        setMeasuredDimension(minDimension, minDimension);

        mRectF.set(thickestStroke / 2, thickestStroke / 2, minDimension - thickestStroke / 2, minDimension - thickestStroke / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRectF, mStartAngle, mSweepAngle, false, mBackgroundPaint);
        canvas.drawArc(mRectF, mStartAngle, mSweepAngle * mProgress / 100, false, mProgressPaint);
    }

    public void animateProgress(float from, float to) {
        mValueAnimator.cancel();
        mValueAnimator.removeAllUpdateListeners();
        mValueAnimator.setFloatValues(from, to);
        mValueAnimator.setDuration(mAnimationDuration);
        mValueAnimator.setInterpolator(mAnimationInterpolator);
        mValueAnimator.start();
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mProgress = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
    }

    public Integer getProgress() {
        return (int) mProgress;
    }
    public float getProgressStrokeWidth() {
        return mProgressPaint.getStrokeWidth();
    }
    public int getProgressColor() {
        return mProgressPaint.getColor();
    }
    public Paint getProgressPaint() {
        return mProgressPaint;
    }
    public float getBackgroundStrokeWidth() {
        return mBackgroundPaint.getStrokeWidth();
    }
    public int getBackgroundColor() {
        return mBackgroundPaint.getColor();
    }
    public Paint getBackgroundPaint() {
        return mBackgroundPaint;
    }
    public long getAnimationDuration() {
        return mAnimationDuration;
    }
    public Interpolator getAnimationInterpolator() {
        return mAnimationInterpolator;
    }

    public void setProgress(Integer progress) {
        if (progress <= 100) {
            if (mAnimationDuration <= 0) {
                mProgress = progress;
                invalidate();
            } else {
                animateProgress(mProgress, progress);
            }
        }
    }
    public void setProgressStrokeWidth(float strokeWidth) {
        mProgressPaint.setStrokeWidth(strokeWidth);
        requestLayout();
    }
    public void setProgressColor(int progressColor) {
        mProgressPaint.setColor(progressColor);
        invalidate();
    }
    public void setProgressPaint(Paint progressPaint) {
        mProgressPaint = progressPaint;
        invalidate();
    }
    public void setBackgroundStrokeWidth(float backgroundStrokeWidth) {
        mBackgroundPaint.setStrokeWidth(backgroundStrokeWidth);
        invalidate();
    }
    public void setBackgroundColor(int backgroundColor) {
        mBackgroundPaint.setColor(backgroundColor);
        invalidate();
    }
    public void setBackgroundPaint(Paint backgroundPaint) {
        mBackgroundPaint = backgroundPaint;
        invalidate();
    }
    public void setAnimationDuration(int animationDuration) {
        mAnimationDuration = animationDuration;
    }
    public void setAnimationInterpolator(Interpolator animationInterpolator) {
        mAnimationInterpolator = animationInterpolator;
    }
}
