package com.example.finalexam.Model;

import java.util.List;

public class UserData {
    private String msg;
    private String name;
    private String account;
    private String passwords;
    private int freezingStatus;//0为正常，1为冻结
    private int onlineStatus;//0为在线，1为离线
    private String releasePasswords;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getFreezingStatus() {
        return freezingStatus;
    }

    public void setFreezingStatus(int freezingStatus) {
        this.freezingStatus = freezingStatus;
    }

    public int getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(int onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public String getReleasePasswords() {
        return releasePasswords;
    }

    public void setReleasePasswords(String releasePasswords) {
        this.releasePasswords = releasePasswords;
    }
}
