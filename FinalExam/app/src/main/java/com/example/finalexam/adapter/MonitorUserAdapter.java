package com.example.finalexam.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.R;
import com.example.finalexam.activity.UserDetailActivity;
import com.example.finalexam.activity.VerifyActivity;
import com.example.finalexam.helper.ColorHelper;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.model.UserData;

import java.util.List;

public class MonitorUserAdapter extends RecyclerView.Adapter<MonitorUserAdapter.ItemHolder> {

private static final String TAG = "MonitorUserAdapter";
private List<UserData> userList;
public static int clickId;
private Context context;


public MonitorUserAdapter(Context context, List<UserData> userList) {
    this.context = context;
    this.userList=userList;
}

@NonNull
@Override
public MonitorUserAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(context).inflate(R.layout.project_rv_item, parent, false);
    return new MonitorUserAdapter.ItemHolder(itemView);
}

@Override
public void onBindViewHolder(@NonNull MonitorUserAdapter.ItemHolder holder, int position) {

}

@Override
public int getItemCount() {
    if (userList == null) return 0;
    return userList.size();
}

public static class ItemHolder extends RecyclerView.ViewHolder {
    public ConstraintLayout userRVItem;
    public TextView userName;
    public TextView online;
    public TextView userColor;
    public View offLine;
    public ItemHolder(@NonNull View itemView) {
        super(itemView);
        userRVItem = itemView.findViewById(R.id.user_rv_item);
        userName = itemView.findViewById(R.id.user_name_overview);
        online = itemView.findViewById(R.id.online_overview);
        userColor = itemView.findViewById(R.id.user_color);
        offLine =itemView.findViewById(R.id.user_item_no_online);
    }
}

}
