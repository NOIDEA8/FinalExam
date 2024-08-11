package com.example.finalexam.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.finalexam.activity.UserDetailActivity;
import com.example.finalexam.client.MyWebSocketClient;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.UserData;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


public class WebSocketPresenter {
    private MyWebSocketClient webSocketClient;
    private static WebSocketPresenter webSocketPresenter=new WebSocketPresenter();
    private List<UserData> userList;
    private static Context context;
    private static UserDataShowInterface activity;

    public static WebSocketPresenter getInstance(Context context, UserDataShowInterface activity) {
        WebSocketPresenter.context = context;
        WebSocketPresenter.activity =activity;
        return webSocketPresenter;
    }



    public MyWebSocketClient getWebSocketClient(int userId){
        if (webSocketClient==null||webSocketClient.isClosed()) {
            String url="ws://47.113.224.195:31110/websocket/"+userId;
            try {
                webSocketClient=new MyWebSocketClient(new URI(url),context,activity);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return webSocketClient;
    }

    public static Context getContext() {
        return context;
    }
    public void setActivity(UserDataShowInterface activity) {//只在展示用户的页面使用
        this.activity = activity;
    }

}
