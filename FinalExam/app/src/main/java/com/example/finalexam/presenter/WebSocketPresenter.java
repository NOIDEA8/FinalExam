package com.example.finalexam.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.finalexam.activity.LogActivity;
import com.example.finalexam.client.MyWebSocketClient;
import com.example.finalexam.model.UserData;
import com.example.finalexam.model.sendmodel.OffsetSend;
import com.example.finalexam.model.sendmodel.ReceiveWarningSend;
import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class WebSocketPresenter {
    private MyWebSocketClient webSocketClient;
    private static WebSocketPresenter webSocketPresenter=new WebSocketPresenter();
    private List<UserData> userList;
    private static Context context;
    private static int oldUserId;
    private static Timer timer;

    public static WebSocketPresenter getInstance(Context context) {
        WebSocketPresenter.context = context;
        return webSocketPresenter;
    }



    public MyWebSocketClient getWebSocketClient(int userId){
        String url;
        if(oldUserId==userId&&webSocketClient.isOpen()){
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

            String token=UserPresenter.getToken();
            Map<String, String> httpHeaders = new HashMap<>();
            httpHeaders.put("token", token);

            try {
                webSocketClient=new MyWebSocketClient(new URI(url),context,httpHeaders);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            Log.d("websocket", "getWebSocketClient: "+webSocketClient);
            return webSocketClient;
        }
    }
    public static void startHeart(TimerTask timerTask){
        if (timer==null) {
            timer=new Timer();
            timer.schedule(timerTask,1000,2000);
        }
    }
    public static void stopHeart(){
        if(timer!=null){
            timer.cancel();
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

    public void receiveWarning(int id){
        Gson gson=new Gson();
        String sendJson=gson.toJson(new ReceiveWarningSend("checkMessage",id));
        webSocketClient.send(sendJson);
    }

    public List<UserData> getUserList() {
        return userList;
    }

    public void setUserList(List<UserData> userList) {
        this.userList = userList;
    }
    public static Timer getTimer() {
        return timer;
    }
}
