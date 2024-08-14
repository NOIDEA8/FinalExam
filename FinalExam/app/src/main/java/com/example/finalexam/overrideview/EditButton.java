package com.example.finalexam.overrideview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalexam.helper.ColorHelper;

public class EditButton extends View {
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        Paint paint = new Paint();
        Path path = new Path();
        float x = getWidth();
        float y = getHeight();

        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        paint.setColor(ColorHelper.TEXT_GREY);

        path.moveTo(3f, y - 3f);
        path.lineTo(x - 3f, y - 3f);
        canvas.drawPath(path, paint);

        path.reset();
        paint.setColor(Color.BLACK);
        path.moveTo(3, y - 3);
        path.lineTo(3, y - y / 4);
        path.lineTo(x - x / 4, 3);
        path.lineTo(x - 3, y / 4);
        path.lineTo(x / 4, y - 3);
        path.close();
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(x - x / 3, y / 3 - y / 4);
        path.lineTo(x - x / 3 + x / 4, y / 3);
        canvas.drawPath(path, paint);
    }

    public EditButton(Context context) {
        super(context);
    }

    public EditButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EditButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EditButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
