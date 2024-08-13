package com.example.finalexam.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.R;
import com.example.finalexam.activity.ManagerDesktop;
import com.example.finalexam.fragment.UserOverviewFragment;
import com.example.finalexam.helper.ColorHelper;
import com.example.finalexam.model.UserData;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<ProjectAdapter.ItemHolder> {
    private final Context context;
    private final List<UserData> list;
    public static UserData data;

    public UsersAdapter(Context context, List<UserData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProjectAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.project_rv_item, parent, false);
        return new ProjectAdapter.ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectAdapter.ItemHolder holder, int position) {
        UserData data = list.get(position);
        showProjectData(holder, data);
        holder.projectRVItem.setOnClickListener(v -> {
            UsersAdapter.data = data;
            ManagerDesktop.callUserLayout();
        });
    }

    @SuppressLint("SetTextI18n")
    private void showProjectData(ProjectAdapter.ItemHolder holder, UserData data) {
        //和ProjectAdapter基本一致，只是把用户状态放在了原本的项目名上
        String enabled = data.getEnabled();
        String isOnline = data.getOnline();
        String name = data.getUsername();

        enabled = enabled == null ? "未知" : enabled;
        isOnline = isOnline == null ? "未知" : isOnline;
        name = name == null ? "Name" : name;

        String colorHex = ColorHelper.createColorHex(name);
        int color = Color.parseColor(colorHex);

        holder.creatorName.setText(name);
        holder.projectName.setText(enabled + " " + isOnline);
        holder.creatorColor.setText(name.substring(0, 1));
        holder.creatorColor.setBackgroundTintList(ColorStateList.valueOf(color));

        if (ColorHelper.isBrightColor(colorHex))
            holder.creatorColor.setTextColor(Color.BLACK);
        else
            holder.creatorColor.setTextColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
