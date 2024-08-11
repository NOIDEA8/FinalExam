package com.example.finalexam.info;

import com.example.finalexam.info.data.UserLoginData;

public class InfoUserLogin {
    private int code;
    private String msg;
    private UserLoginData data;

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

    public UserLoginData getData() {
        return data;
    }

    public void setData(UserLoginData data) {
        this.data = data;
    }
}
