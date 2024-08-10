package com.example.finalexam.baseappcompatactivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalexam.activity.LogActivity;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    private ForceOfflineReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.FinalExam.FORCE_OFFSET");
        receiver=new ForceOfflineReceiver();
        registerReceiver(receiver,intentFilter);
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(receiver!=null){
            unregisterReceiver(receiver);
            receiver=null;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}

class ForceOfflineReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent Intent) {
        ActivityCollector.finishAll();
        context.startActivity(new Intent(context, LogActivity.class));
    }
}

class ActivityCollector{
    public static List<Activity> activities=new ArrayList<>();
    public static void addActivity(Activity activity){
            activities.add(activity);
    }
    public static void removeActivity(Activity activity){
            activities.remove(activity);
    }
    public static void finishAll(){
        for(Activity activity:activities){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}