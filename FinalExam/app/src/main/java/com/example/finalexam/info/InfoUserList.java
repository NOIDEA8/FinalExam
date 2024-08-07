package com.example.finalexam.info;

import com.example.finalexam.model.ProjectData;
import com.example.finalexam.model.UserData;

import java.util.List;

public class InfoUserList {
    private int code;
    private String msg;
    private List<UserData> data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<UserData> getData() {
        return data;
    }
}
