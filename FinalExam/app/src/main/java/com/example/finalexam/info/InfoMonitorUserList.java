package com.example.finalexam.info;

import com.example.finalexam.model.user.MonitorUser;

import java.util.List;

public class InfoMonitorUserList {
    private int code;
    private String msg;
    private List<MonitorUser> data;

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

    public List<MonitorUser> getData() {
        return data;
    }

    public void setData(List<MonitorUser> data) {
        this.data = data;
    }
}
