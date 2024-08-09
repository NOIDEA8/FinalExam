package com.example.finalexam.info;

import com.example.finalexam.model.application.UserMonitorApplication;

import java.util.List;

public class InfoMonitorApplication {
    private int code;
    private String msg;
    private List<UserMonitorApplication> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<UserMonitorApplication> getData() {
        return data;
    }

    public void setData(List<UserMonitorApplication> data) {
        this.data = data;
    }
}
