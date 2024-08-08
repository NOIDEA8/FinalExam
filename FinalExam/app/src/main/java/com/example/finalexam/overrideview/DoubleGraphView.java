package com.example.finalexam.overrideview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DoubleGraphView extends View {
    private static final String TAG = "DoubleGraphView";
    private boolean already = false;
    private Paint paint;
    private Path path;
    private ValueAnimator animator;
    private float viewX;
    private float viewY;

    private final float MAX_FPS = 160f;
    private final int DOWN_SPEED = 3;
    public final List<Integer> targetFPS = new ArrayList<>();//上限120，下限0
    public final List<Integer> trackFPS = new ArrayList<>();//动画用

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        if (!already) init();
        canvas.drawPath(path, paint);
    }

    private void init() {
        paint = new Paint();
        path = new Path();

        viewX = getWidth() - 6f;
        viewY = getHeight() - 6f;

        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#F2E269"));
        paint.setStrokeWidth(7);

        path.moveTo(3f, 3f);
        path.rLineTo(viewX, 0);

        animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(1000);

        initTrackFPS();

        animator.addUpdateListener(animation -> {
            int num = targetFPS.size();
            if (num == 0) return;

            //如果只有一个数据。那么就是直线
            if (num == 1) {
                path.reset();
                int fps = targetFPS.get(0);
                path.moveTo(3, 3 + viewY * (1 - fps / MAX_FPS));
                path.rLineTo(viewX, 0);
                return;
            }

            //否则为多点曲线
            path.reset();
            float interval = viewX / (num - 1);

            for (int i = 0; i < num; i++) {
                int target = targetFPS.get(i);
                int track = trackFPS.get(i);
                if (track < target) {
                    track += DOWN_SPEED;
                    trackFPS.set(i, ++track);
                }
                if (i == 0) {
                    path.moveTo(3, 3 + viewY * (1 - track / MAX_FPS));
                } else {
                    path.lineTo(i * interval, 3 + viewY * (1 - track / MAX_FPS));
                }
            }
            invalidate();
        });
        animator.start();

        already = true;
    }

    private void initTrackFPS() {
        int num = targetFPS.size();
        if (num == 0 || num == 1) return;

        trackFPS.addAll(targetFPS);
        for (int i = 0; i < num; i++) {
            trackFPS.set(i, 0);
        }
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
