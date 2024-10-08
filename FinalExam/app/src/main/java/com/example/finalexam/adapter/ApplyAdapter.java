package com.example.finalexam.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.R;
import com.example.finalexam.activity.VerifyActivity;
import com.example.finalexam.helper.ColorHelper;
import com.example.finalexam.model.ProjectData;

import java.util.List;

public class ApplyAdapter extends RecyclerView.Adapter<ProjectAdapter.ItemHolder> {
    private final Context context;
    private final List<ProjectData> list;
    public static ProjectData clickData;

    public ApplyAdapter(Context context, List<ProjectData> list) {
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
        ProjectData data = list.get(position);
        showProjectData(holder, data);
        holder.projectRVItem.setOnClickListener(v -> {
            clickData = data;
            context.startActivity(new Intent(context, VerifyActivity.class));
        });
    }

    private void showProjectData(ProjectAdapter.ItemHolder holder, ProjectData data) {
        //获取项目名字和发布者名字
        String projectName = data.getProjectName();
        String creatorName = data.getCreator();
        projectName = projectName == null ? "projectName" : projectName;
        creatorName = creatorName == null ? "creatorName" : creatorName;

        //获取颜色
        String creatorColor;
        creatorColor = ColorHelper.createColorHex(creatorName);
        int color = Color.parseColor(creatorColor);

        holder.projectName.setText(projectName);
        holder.creatorName.setText(creatorName);

        holder.creatorColor.setText(creatorName.substring(0, 1));
        holder.creatorColor.setBackgroundTintList(ColorStateList.valueOf(color));

        //判断是否为深色，是则把头像字体改为白色，更加显眼
        if (ColorHelper.isBrightColor(color))
            holder.creatorColor.setTextColor(Color.BLACK);
        else
            holder.creatorColor.setTextColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
