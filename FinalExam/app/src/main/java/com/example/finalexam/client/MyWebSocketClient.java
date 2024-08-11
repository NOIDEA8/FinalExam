package com.example.finalexam.client;

import android.content.Context;
import android.content.Intent;

import com.example.finalexam.activity.UserDetailActivity;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.info.InfoUserList;
import com.example.finalexam.presenter.WebSocketPresenter;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;

public class MyWebSocketClient extends WebSocketClient {
    //网址：ws://47.113.224.195:31110/websocket/admin(管理员的话)
    private UserDataShowInterface activity;
    private Context context;
    public MyWebSocketClient(URI serverUri,Context context,UserDataShowInterface activity) {
        super(serverUri);
        this.context=context;
        this.activity=activity;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {
        if(message.equals("offSet")){
            context.sendBroadcast(new Intent("com.example.FinalExam.FORCE_OFFSET"));
        } else {
            Gson gson=new Gson();
            InfoUserList info=gson.fromJson(message,InfoUserList.class);
            if(info!=null){//msg?
                if(!info.getData().isEmpty()){
                    activity.UserOnlineOrNotList(new ArrayList<>());
                }else {
                    activity.UserOnlineOrNotList(info.getData());
                }
            }
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}
