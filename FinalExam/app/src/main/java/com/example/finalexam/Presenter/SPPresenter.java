package com.example.finalexam.Presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SPPresenter {//SPPresenter为工具类保存原有打开shareprefrence的取操作

    //获取登录状态，已登录则返回true
    public static boolean isLogged(Context context) {
        try {
            SharedPreferences sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
            return sp.getBoolean("isLogged", false);
        } catch (Exception e) {

        }
        return false;
    }
    //获取账号
    public static String getAccount(Context context) {
        SharedPreferences sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        return sp.getString("account", null);
    }
    //获取密码
    public static String getPassword(Context context) {
        SharedPreferences sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        return sp.getString("password", "null");
    }

    public static void accordLoggedStatus(Context context, boolean isLogged) {
        SharedPreferences sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean("isLogged", isLogged);
        ed.commit();
    }
    //记录账号
    public static void accordAccount(Context context, String account) {
        SharedPreferences sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor ed = sp.edit();
        ed.putString("account", account);
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
