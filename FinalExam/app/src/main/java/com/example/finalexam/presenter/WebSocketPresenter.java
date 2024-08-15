package com.example.finalexam.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.finalexam.activity.LogActivity;
import com.example.finalexam.client.MyWebSocketClient;
import com.example.finalexam.model.UserData;
import com.example.finalexam.model.sendmodel.OffsetSend;
import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


public class WebSocketPresenter {
    private MyWebSocketClient webSocketClient;
    private static WebSocketPresenter webSocketPresenter=new WebSocketPresenter();
    private List<UserData> userList;
    private static Context context;
    private static int oldUserId;

    public static WebSocketPresenter getInstance(Context context) {
        WebSocketPresenter.context = context;
        return webSocketPresenter;
    }



    public MyWebSocketClient getWebSocketClient(int userId){
        String url;
        if(oldUserId==userId){
            Log.d("websocket", "getWebSocketClient: "+webSocketClient);
            return webSocketClient;
        }
        else {
            oldUserId=userId;
            if(userId!=-1){
                url="ws://47.113.224.195:31111/websocket/"+userId;
            }else{
                url="ws://47.113.224.195:31111/websocket/admin";
            }

            try {
                webSocketClient=new MyWebSocketClient(new URI(url),context);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            Log.d("websocket", "getWebSocketClient: "+webSocketClient);
            return webSocketClient;
        }
    }

    public static Context getContext() {
        return context;
    }

    public void adminSendOffset(int userId){
        String id=String.valueOf(userId);
        OffsetSend send=new OffsetSend(id,"logoutUser");
        Gson gson=new Gson();
        String sendJson=gson.toJson(send);
        webSocketClient.send(sendJson);
    }

    public List<UserData> getUserList() {
        return userList;
    }

    public void setUserList(List<UserData> userList) {
        this.userList = userList;
    }

}
