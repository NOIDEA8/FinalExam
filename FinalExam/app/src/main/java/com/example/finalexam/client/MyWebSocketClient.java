package com.example.finalexam.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import com.example.finalexam.activity.UserDesktop;
import com.example.finalexam.fragment.UserOverviewFragment;
import com.example.finalexam.info.InfoUser;
import com.example.finalexam.info.InfoUserList;
import com.example.finalexam.info.WebsocketInfo;
import com.example.finalexam.model.UserData;
import com.example.finalexam.presenter.SPPresenter;
import com.example.finalexam.presenter.UserPresenter;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.List;
import java.util.Objects;

public class MyWebSocketClient extends WebSocketClient {
    //网址：ws://47.113.224.195:31110/websocket/admin(管理员的话)
    private Context context;
    Gson gson=new Gson();

    public MyWebSocketClient(URI serverUri,Context context) {
        super(serverUri);
        this.context=context;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onMessage(String message) {
        WebsocketInfo info=gson.fromJson(message,WebsocketInfo.class);

        if(info.getType().equals("offline")||info.getType().equals("账号被冻结,有疑问联系管理员")){
            context.sendBroadcast(new Intent("com.example.FinalExam.FORCE_OFFSET"));
            SPPresenter.accordLoggedStatus(context.getApplicationContext(), false);
        } else if (info.getType().equals("warning")) {
            UserDesktop.callErrorLayout(Integer.getInteger(info.getData()),info.getMsg());
        } else {
            if(info!=null){//msg?
                if(!info.getData().isEmpty()){
                    List<UserData> list=UserOverviewFragment.list;
                    list.clear();
                    list.addAll(info.getUserList());
                    Objects.requireNonNull(UserOverviewFragment.usersRV.getAdapter()).notifyDataSetChanged();
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
