package com.example.finalexam.model;

import com.google.gson.annotations.Expose;

import kotlin.jvm.Transient;

public class UserData {



    private int enabled;//0未冻结、1冻结?
    private String online;
    private String password;
    private String registerTime;
    private String username;//这个就是账号

    private int userId;//操作必要




    public int getEnabled() {
        return enabled;
    }

    public String getOnline() {
        return online;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
