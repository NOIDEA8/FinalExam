package com.example.finalexam.overrideview.any;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import androidx.annotation.NonNull;

import com.example.finalexam.helper.ColorHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewDraw {
    private static final ViewDraw viewDraw = new ViewDraw();

    public static void draw(@NonNull Canvas canvas, Path path, Paint paint, float x, float y, String drawMethod) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = viewDraw.getClass().getDeclaredMethod(drawMethod, Canvas.class, Path.class, Paint.class, float.class, float.class);
        method.setAccessible(true);
        method.invoke(viewDraw, canvas, path, paint, x, y);
    }

    //addButton: 加号按钮
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

    private void editIcon(@NonNull Canvas canvas, Path path, Paint paint, float x, float y) {
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

    private void freezeIcon(@NonNull Canvas canvas, Path path, Paint paint, float x, float y) {
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        paint.setColor(Color.BLACK);

        path.moveTo(x / 8, y - 3);
        path.lineTo(x / 8, y / 2);
        path.lineTo(x - x / 8, y / 2);
        path.lineTo(x - x / 8, y - 3);
        path.lineTo(x / 8, y - 3);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(x / 4, y / 2);
        path.lineTo(x / 4, y / 2);
        path.quadTo(x / 4, 3, x / 2, 3);
        path.quadTo(x - x / 4, 3, x - x / 4, y / 4);
        path.lineTo(x - x / 4, y / 2);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(x / 2, y / 1.5f);
        path.lineTo(x / 2, y / 1.2f);
        canvas.drawPath(path, paint);
    }
}
