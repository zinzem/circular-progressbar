package com.zinzem.circularprogressbar;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
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
    private int mAnimationDuration = 700;
    private Interpolator mAnimationInterpolator = new AccelerateDecelerateInterpolator();

    private RectF mRectF;
    private Paint mProgressPaint;
    private Paint mBackgroundPaint;

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircularProgressBar, 0, 0);
        float progressStrokeWidth = getResources().getDimension(R.dimen.default_progress_stroke_width);
        int progressColor = Color.BLACK;
        float backgroundStrokeWidth = getResources().getDimension(R.dimen.default_background_stroke_width);
        int backgroundColor = Color.BLACK;

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

    @BindingAdapter("progress")
    public static void setProgressWithBinding(CircularProgressBar circularProgressBar, Number progress) {
        float progressFloatValue = 0;

        if (progress instanceof Double) {
            progressFloatValue = Double.valueOf((double) progress).floatValue();
        } else if (progress instanceof Float) {
            progressFloatValue = (float) progress;
        } else if (progress instanceof Long) {
            progressFloatValue = (long) progress;
        } else if (progress instanceof Integer) {
            progressFloatValue = (int) progress;
        }
        circularProgressBar.animateProgressTo(progressFloatValue);
    }

    public void animateProgressTo(float progress) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress);

        objectAnimator.setDuration(mAnimationDuration);
        objectAnimator.setInterpolator(mAnimationInterpolator);
        objectAnimator.start();
    }

    public float getProgress() {
        return mProgress;
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

    public void setProgress(float progress) {
        mProgress = (progress <= 100) ? progress : 100;
        invalidate();
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
