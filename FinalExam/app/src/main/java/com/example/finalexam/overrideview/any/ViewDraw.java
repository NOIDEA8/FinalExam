package com.example.finalexam.overrideview.any;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import androidx.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewDraw {
    private static final ViewDraw viewDraw = new ViewDraw();

    public static void draw(@NonNull Canvas canvas, Path path, Paint paint, float x, float y, String drawMethod) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = viewDraw.getClass().getDeclaredMethod(drawMethod, Canvas.class, Path.class, Paint.class, float.class, float.class);
        method.setAccessible(true);
        method.invoke(viewDraw, canvas, path, paint, x, y);
    }

    private void addButton(@NonNull Canvas canvas, Path path, Paint paint, float x, float y) {
        float length = Math.min(x, y) / 3f;
        float centerX = x / 2f;
        float centerY = y / 2f;

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(7);

        path.moveTo(centerX - length / 2f, centerY);
        path.rLineTo(length, 0);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(centerX, centerY - length / 2f);
        path.rLineTo(0, length);
        canvas.drawPath(path, paint);
    }
}
