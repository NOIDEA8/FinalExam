package com.example.finalexam.QGApplication;

import android.app.Application;

import com.example.monitor.QGExceptionHandler;

public class QGApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        QGExceptionHandler handler = QGExceptionHandler.getInstance();
        handler.install(this);
        handler.avoidCrash();
        handler.setExceptionTip("有会导致崩溃的错误");
    }
}
