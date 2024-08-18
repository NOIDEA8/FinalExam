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
    private final int WHITE = Color.WHITE;
    private final int BLACK = Color.BLACK;
    private final int TEXT_GREY = Color.parseColor("#969696");
    private final int PINK = Color.parseColor("#F4B9BB");
    private final int BLUE_PURPLE = Color.parseColor("#6979F2");
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
        paint.setColor(TEXT_GREY);

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
        paint.setStrokeCap(Paint.Cap.SQUARE);
        path.moveTo(x - x / 2.3f, y / 2.3f - y / 4);
        path.lineTo(x - x / 2.3f + x / 4, y / 2.3f);
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
        path.lineTo(x / 4, y / 3);
        path.quadTo(x / 4, 5, x / 2, 5);
        path.quadTo(x - x / 4, 5, x - x / 4, y / 3);
        path.lineTo(x - x / 4, y / 2);
        canvas.drawPath(path, paint);

        path.reset();
        paint.setColor(TEXT_GREY);
        path.moveTo(x / 2, y / 1.5f);
        path.lineTo(x / 2, y / 1.2f);
        canvas.drawPath(path, paint);
    }

    private void projectIconStroke(@NonNull Canvas canvas, Path path, Paint paint, float x, float y) {
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        paint.setColor(Color.BLACK);

        path.moveTo(x - x / 7 - x / 4, 3);
        path.lineTo(x / 7, 3);
        path.lineTo(x / 7, y - 3);
        path.lineTo(x - x / 7, y - 3);
        path.lineTo(x - x / 7, 3 + y / 4);
        canvas.drawPath(path, paint);

        paint.setColor(TEXT_GREY);
        path.reset();
        path.moveTo(x - x / 7 - x / 4, 3);
        path.lineTo(x - x / 7, 3 + y / 4);
        canvas.drawPath(path, paint);

        path.reset();
        paint.setColor(Color.BLACK);
        path.moveTo(x - x / 7 - x / 4, 3);
        path.lineTo(x - x / 7 - x / 4, 3 + y / 4);
        path.lineTo(x - x / 7, 3 + y / 4);
        canvas.drawPath(path, paint);

        path.reset();
        paint.setColor(TEXT_GREY);
        path.moveTo(x / 3, y / 2);
        path.lineTo(x - x / 3, y / 2);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(x / 3, y - y / 3);
        path.lineTo(x - x / 3 - x / 6, y - y / 3);
        canvas.drawPath(path, paint);
    }

    private void projectIconPink(@NonNull Canvas canvas, Path path, Paint paint, float x, float y) {
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        paint.setColor(BLUE_PURPLE);

        path.moveTo(x - x / 7 - x / 4, 3);
        path.lineTo(x / 7, 3);
        path.lineTo(x / 7, y - 3);
        path.lineTo(x - x / 7, y - 3);
        path.lineTo(x - x / 7, 3 + y / 4);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(x - x / 7 - x / 4, 3);
        path.lineTo(x - x / 7, 3 + y / 4);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(x - x / 7 - x / 4, 3);
        path.lineTo(x - x / 7 - x / 4, 3 + y / 4);
        path.lineTo(x - x / 7, 3 + y / 4);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(x / 3, y / 2);
        path.lineTo(x - x / 3, y / 2);
        canvas.drawPath(path, paint);

        path.reset();
        path.moveTo(x / 3, y - y / 3);
        path.lineTo(x - x / 3 - x / 6, y - y / 3);
        canvas.drawPath(path, paint);
    }

    private void personIconStroke(@NonNull Canvas canvas, Path path, Paint paint, float x, float y) {
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        paint.setColor(TEXT_GREY);

        path.moveTo(x - x / 7f, y - 3);
        path.lineTo(x / 7f, y - 3);
        path.quadTo(x / 6f, y / 1.5f, x / 2f, y / 1.5f);
        path.quadTo(x - x / 6f, y / 1.5f, x - x / 7f, y - 3);
        canvas.drawPath(path, paint);

        path.reset();
        paint.setColor(BLACK);
        canvas.drawCircle(x / 2f, (y / 1.5f) / 2f, y / 5f, paint);
    }

    private void personIconPink(@NonNull Canvas canvas, Path path, Paint paint, float x, float y) {
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        paint.setColor(BLUE_PURPLE);

        path.moveTo(x - x / 7f, y - 3);
        path.lineTo(x / 7f, y - 3);
        path.quadTo(x / 6f, y / 1.5f, x / 2f, y / 1.5f);
        path.quadTo(x - x / 6f, y / 1.5f, x - x / 7f, y - 3);
        canvas.drawPath(path, paint);

        path.reset();
        canvas.drawCircle(x / 2f, (y / 1.5f) / 2f, y / 5f, paint);
    }

    private void verifyIconStroke(@NonNull Canvas canvas, Path path, Paint paint, float x, float y) {
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        paint.setColor(TEXT_GREY);

        path.moveTo(x - 3, y - 3);
        path.lineTo(x / 2, y / 2);
        canvas.drawPath(path, paint);

        paint.setColor(WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle((x - x / 8) / 2, (y - y / 8) / 2, (x - x / 8) / 2 - 4, paint);

        paint.setColor(BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle((x - x / 8) / 2, (y - y / 8) / 2, (x - x / 8) / 2 - 4, paint);

        path.reset();
        paint.setColor(WHITE);
        path.moveTo(x / 2.3f, y / 1.7f-7);
        path.lineTo(x - 3, 3);
        canvas.drawPath(path,paint);

        path.reset();
        paint.setColor(TEXT_GREY);
        path.moveTo(x / 4, y / 2.5f);
        path.lineTo(x / 2.3f, y / 1.7f);
        path.lineTo(x - 3, 10);
        canvas.drawPath(path,paint);
    }

    private void verifyIconPink(@NonNull Canvas canvas, Path path, Paint paint, float x, float y) {
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        paint.setColor(BLUE_PURPLE);

        path.moveTo(x - 3, y - 3);
        path.lineTo(x / 2, y / 2);
        canvas.drawPath(path, paint);

        paint.setColor(WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle((x - x / 8) / 2, (y - y / 8) / 2, (x - x / 8) / 2 - 4, paint);

        paint.setColor(BLUE_PURPLE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle((x - x / 8) / 2, (y - y / 8) / 2, (x - x / 8) / 2 - 4, paint);

        path.reset();
        paint.setColor(WHITE);
        path.moveTo(x / 2.3f, y / 1.7f-7);
        path.lineTo(x - 3, 3);
        canvas.drawPath(path,paint);

        path.reset();
        paint.setColor(BLUE_PURPLE);
        path.moveTo(x / 4, y / 2.5f);
        path.lineTo(x / 2.3f, y / 1.7f);
        path.lineTo(x - 3, 10);
        canvas.drawPath(path,paint);
    }
}
