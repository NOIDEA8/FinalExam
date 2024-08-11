package com.example.finalexam.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.R;
import com.example.finalexam.activity.UserDetailActivity;
import com.example.finalexam.helper.ColorHelper;
import com.example.finalexam.model.UserData;

import java.util.List;

public class UserOvervIewAdapter extends RecyclerView.Adapter<UserOvervIewAdapter.ItemHolder> {

    private static final String TAG = "UserOvervIewAdapter";
    private List<UserData> userList;
    public static int clickId;
    private Context context;

    public UserOvervIewAdapter(Context context, List<UserData> userList) {
        this.context = context;
        this.userList=userList;
    }

    @NonNull
    @Override
    public UserOvervIewAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.project_rv_item, parent, false);
        return new UserOvervIewAdapter.ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserOvervIewAdapter.ItemHolder holder, int position) {
        boolean isOnline=false;
        if(userList.get(position).getOnline().equals("在线")){
            isOnline=true;
        }

        showUserData(holder,userList.get(position),isOnline);
        setDetailListener(holder,userList.get(position));
    }

    @Override
    public int getItemCount() {
        if (userList == null) return 0;
        return userList.size();
    }

    private void setDetailListener(UserOvervIewAdapter.ItemHolder holder, UserData data) {
        holder.userRVItem.setOnClickListener(v -> {
            clickId = data.getUserId();
            //接下来启动一个activity去查看详情页
            context.startActivity(new Intent(context, UserDetailActivity.class));
        });
    }


    private void showUserData(UserOvervIewAdapter.ItemHolder holder, UserData data, boolean isOnline) {
        //获取项目名字和发布者名字
        String projectName = data.getUsername();
        String creatorName = data.getOnline();
        projectName = projectName == null ? "projectName" : projectName;
        creatorName = creatorName == null ? "creatorName" : creatorName;

        //获取颜色
        String creatorColor;
        creatorColor = ColorHelper.createColorHex(creatorName);
        int color = Color.parseColor(creatorColor);

        holder.userName.setText(projectName);
        holder.online.setText(creatorName);

        holder.userColor.setText(creatorName.substring(0, 1));
        holder.userColor.setBackgroundTintList(ColorStateList.valueOf(color));

        //判断是否为深色，是则把头像字体改为白色，更加显眼
        if (ColorHelper.isBrightColor(color))
            holder.userColor.setTextColor(Color.BLACK);
        else
            holder.userColor.setTextColor(Color.WHITE);

        if (isOnline)
            holder.offLine.setVisibility(View.INVISIBLE);
        else
            holder.offLine.setVisibility(View.VISIBLE);
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
