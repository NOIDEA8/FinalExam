package com.example.finalexam.info;

import com.example.finalexam.model.LogData;

import java.util.List;

public class InfoLogList {
    private int code;
    private String msg;
    private List<LogData> data;

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

    public List<LogData> getData() {
        return data;
    }

    public void setData(List<LogData> data) {
        this.data = data;
    }
}
