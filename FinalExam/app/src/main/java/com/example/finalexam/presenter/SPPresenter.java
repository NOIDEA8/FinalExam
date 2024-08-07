package com.example.finalexam.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SPPresenter {//SPPresenter为工具类保存原有打开shareprefrence的取操作


    public static void accordLoggedStatus(Context context, boolean isLogged) {
        SharedPreferences sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean("isLogged", isLogged);
        ed.commit();
    }
    //记录账号
    public static void accordUsername(Context context, String username) {
        SharedPreferences sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor ed = sp.edit();
        ed.putString("username", username);
        ed.commit();
    }
    //记录密码
    public static void accordPassword(Context context, String password) {
        SharedPreferences sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor ed = sp.edit();
        ed.putString("password", password);
        ed.commit();
    }
}
