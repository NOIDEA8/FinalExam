package com.example.finalexam.overrideview;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalexam.activity.ProjectDetailActivity;

public class DoubleGraphView extends View {
    private float x;
    private float y;
    private float interval;
    private int readMax = 0;
    private int errorMax = 0;
    private final Paint readPaint = new Paint();
    private final Path readPath = new Path();
    private final Paint errorPaint = new Paint();
    private final Path errorPath = new Path();
    public static final ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
    private boolean firstStart = true;
    private boolean firstPlay = true;

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        if (firstStart) {
            x = getWidth();
            y = getHeight();
            interval = x / 7f;
            init();
            firstStart = false;
            return;
        }
        canvas.drawPath(readPath, readPaint);
        canvas.drawPath(errorPath, errorPaint);
    }

    private void init() {
        readPaint.setStyle(Paint.Style.FILL);
        readPaint.setColor(Color.parseColor("#41b349"));

        errorPaint.setStyle(Paint.Style.STROKE);
        errorPaint.setStrokeWidth(7f);
        errorPaint.setStrokeCap(Paint.Cap.ROUND);
        errorPaint.setColor(Color.parseColor("#c04851"));

        animator.addUpdateListener(animation -> {
            if (firstPlay) {
                for (Integer i : ProjectDetailActivity.readDay)
                    readMax = i > readMax ? i : readMax;
                for (Integer i : ProjectDetailActivity.errorDay)
                    errorMax = i > errorMax ? i : errorMax;
                firstPlay = false;
            }

            readPath.reset();
            errorPath.reset();
            float value = (float) animation.getAnimatedValue();

            int num = 0;
            for (int i = 0; i < ProjectDetailActivity.readDay.size(); i++) {
                num = ProjectDetailActivity.readDay.get(i);
                readPath.moveTo(interval * (7 - i) - 6f, y);
                readPath.rLineTo(0, -y * num * value / readMax);
                readPath.rLineTo(-interval + 12f, 0);
                readPath.lineTo(interval * (7 - i - 1) + 6f, y);
                readPath.close();
            }
            for (int i = 0; i < ProjectDetailActivity.errorDay.size(); i++) {
                num = ProjectDetailActivity.readDay.get(i);
                if (i == 0) {
                    errorPath.moveTo(interval * 6.5f, y - y * num * value / errorMax);
                    continue;
                }
                errorPath.lineTo(interval * (6.5f - i), y - y * num * value / errorMax);
            }

            invalidate();
        });
        animator.setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator());
    }

    public DoubleGraphView(Context context) {
        super(context);
    }

    public DoubleGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DoubleGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DoubleGraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
