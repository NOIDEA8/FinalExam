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

public class LogoView extends View {
    Paint outPaint = new Paint();
    Path outPath = new Path();
    Paint upPaint = new Paint();
    Path upPath = new Path();
    Paint openPaint = new Paint();
    Path openPath = new Path();
    float x;
    float y;
    ValueAnimator out = ValueAnimator.ofFloat(0, 1);
    ValueAnimator up = ValueAnimator.ofFloat(0, 1);
    ValueAnimator open = ValueAnimator.ofFloat(0, 1);
    AnimatorSet set = new AnimatorSet();
    boolean firstStart = true;

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        if (firstStart) {
            x = getWidth();
            y = getHeight();
            firstStart = false;
            set.start();
            return;
        }
        canvas.drawPath(openPath, openPaint);
        canvas.drawPath(outPath, outPaint);
        canvas.drawPath(upPath, upPaint);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        outPaint.setStyle(Paint.Style.FILL);
        outPaint.setColor(Color.parseColor("#c19af4"));
        upPaint.setStyle(Paint.Style.FILL);
        upPaint.setColor(Color.parseColor("#a574ed"));
        openPaint.setStyle(Paint.Style.FILL);
        openPaint.setColor(Color.parseColor("#8e56ea"));

        out.addUpdateListener(animation -> {
            outPath.reset();
            float value = (float) animation.getAnimatedValue();
            outPath.moveTo(x / 3, y);
            outPath.lineTo(x / 3, y - y * value / 2);
            outPath.rLineTo(x / 3, 0);
            outPath.lineTo(x - x / 3, y);
            outPath.close();
            invalidate();
        });
        out.setInterpolator(new DecelerateInterpolator());

        up.addUpdateListener(animation -> {
            outPath.reset();
            upPath.reset();
            float value = (float) animation.getAnimatedValue();
            outPath.moveTo(x / 3, y);
            outPath.lineTo(x / 3, y / 2);
            outPath.rLineTo(x / 3, 0);
            outPath.lineTo(x - x / 3, y);
            outPath.close();

            upPath.moveTo(x / 3, y / 2);
            upPath.lineTo(x / 3, y / 2 - y * value / 2);
            upPath.rLineTo(x / 3, 0);
            upPath.lineTo(x - x / 3, y / 2);
            upPath.close();
            invalidate();
        });
        up.setInterpolator(new DecelerateInterpolator());

        open.addUpdateListener(animation -> {
            outPath.reset();
            upPath.reset();
            openPath.reset();
            float value = (float) animation.getAnimatedValue();
            outPath.moveTo(x / 3 - x * value / 3, y);
            outPath.rLineTo(0, -y / 2);
            outPath.rLineTo(x / 3, 0);
            outPath.rLineTo(0, y / 2);
            outPath.close();
            outPath.moveTo(x / 3 + x * value / 3, y);
            outPath.rLineTo(0, -y / 2);
            outPath.rLineTo(x / 3, 0);
            outPath.rLineTo(0, y / 2);
            outPath.close();

            upPath.moveTo(x / 3 - x * value / 3, y / 2);
            upPath.lineTo(x / 3, 0);
            upPath.rLineTo(x / 3, 0);
            upPath.lineTo(x - x / 3 - x * value / 3, y / 2);
            upPath.close();

            openPath.moveTo(x / 3 + x * value / 3, y / 2);
            openPath.lineTo(x / 3, 0);
            openPath.rLineTo(x / 3, 0);
            openPath.lineTo(x - x / 3 + x * value / 3, y / 2);
            openPath.close();
            invalidate();
        });
        open.setInterpolator(new DecelerateInterpolator());

        set.play(up).before(open).after(out);
        set.setDuration(330);
    }

    public LogoView(Context context) {
        super(context);
    }

    public LogoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LogoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LogoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
