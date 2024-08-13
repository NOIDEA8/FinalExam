package com.example.finalexam.info;

import com.example.finalexam.model.LogData;

public class InfoLog {
    private int code;
    private String msg;
    private LogData data;

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

    public LogData getData() {
        return data;
    }

    public void setData(LogData data) {
        this.data = data;
    }
}
