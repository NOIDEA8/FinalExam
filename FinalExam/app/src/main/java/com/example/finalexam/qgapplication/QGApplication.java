package com.example.finalexam.qgapplication;

import android.app.Application;

import com.example.monitor.FPSCounter;
import com.example.monitor.MemoryInfoProvider;
import com.example.monitor.QGExceptionHandler;
import com.example.monitor.UploadPresenter;

public class QGApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        QGExceptionHandler handler = QGExceptionHandler.getInstance();
        handler.install(this);
        handler.avoidCrash(true);
        handler.setExceptionToast("有会导致崩溃的错误");

        FPSCounter.initFPSCounter();
        MemoryInfoProvider.initMemoryInfoProvider();

        UploadPresenter.initUploadPresenter(this, "http://47.113.224.195:31108/sdk/", 114);
    }
}
