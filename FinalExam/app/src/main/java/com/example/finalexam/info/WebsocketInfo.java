package com.example.finalexam.info;

import com.example.finalexam.model.UserData;

import java.util.List;

public class WebsocketInfo {
    private List<UserData> userList;
    private int data;
    private String msg;
    private String type;


    public List<UserData> getUserList() {
        return userList;
    }

    public void setUserList(List<UserData> userList) {
        this.userList = userList;
    }


    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
