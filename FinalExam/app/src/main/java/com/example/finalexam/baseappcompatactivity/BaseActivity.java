package com.example.finalexam.baseappcompatactivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalexam.activity.LogActivity;
import com.example.finalexam.activity.UserDesktop;
import com.example.finalexam.info.WebsocketInfo;
import com.example.finalexam.presenter.UserPresenter;
import com.example.finalexam.presenter.WebSocketPresenter;

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
        intentFilter.addAction("com.example.FinalExam.USER_FROZEN");
        intentFilter.addAction("com.example.FinalExam.MULTILOG");
        intentFilter.addAction("com.example.FinalExam.WARNING");
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
        String action = Intent.getAction();
        if ("com.example.FinalExam.FORCE_OFFSET".equals(action)) {
            ActivityCollector.finishAll();
            context.startActivity(new Intent(context, LogActivity.class));
            Toast.makeText(context.getApplicationContext(),"您被管理员下线", Toast.LENGTH_SHORT).show();
        } else if ("com.example.FinalExam.USER_FROZEN".equals(action)) {
            ActivityCollector.finishAll();
            context.startActivity(new Intent(context, LogActivity.class));
            Toast.makeText(context.getApplicationContext(),"您被管理员冻结", Toast.LENGTH_SHORT).show();
        } else if ("com.example.FinalExam.MULTILOG".equals(action)) {
            ActivityCollector.finishAll();
            context.startActivity(new Intent(context, LogActivity.class));
            Toast.makeText(context.getApplicationContext(),"账号已在别处登录", Toast.LENGTH_SHORT).show();
        }
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