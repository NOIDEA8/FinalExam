package com.example.finalexam.info;

import com.example.finalexam.model.application.UserPublishApplication;

import java.util.List;

public class InfoPublishApplication {
    private int code;
    private String msg;
    private List<UserPublishApplication> data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<UserPublishApplication> getData() {
        return data;
    }
}
