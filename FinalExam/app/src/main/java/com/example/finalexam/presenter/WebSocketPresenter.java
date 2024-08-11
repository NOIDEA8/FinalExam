package com.example.finalexam.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.finalexam.client.MyWebSocketClient;
import com.example.finalexam.model.UserData;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


public class WebSocketPresenter {
    private MyWebSocketClient webSocketClient;
    private static WebSocketPresenter webSocketPresenter=new WebSocketPresenter();
    private List<UserData> userList;
    private static Context context;

    public static WebSocketPresenter getInstance(Context context) {
        WebSocketPresenter.context = context;
        return webSocketPresenter;
    }



    public MyWebSocketClient getWebSocketClient(int userId){
        String url="ws://47.113.224.195:31110/websocket/"+userId;
        try {
            webSocketClient=new MyWebSocketClient(new URI(url),context);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return webSocketClient;
    }

    public static Context getContext() {
        return context;
    }



    public List<UserData> getUserList() {
        return userList;
    }

    public void setUserList(List<UserData> userList) {
        this.userList = userList;
    }

}
