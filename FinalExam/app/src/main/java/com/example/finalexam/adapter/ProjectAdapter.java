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
import com.example.finalexam.activity.ProjectDetailActivity;
import com.example.finalexam.helper.ColorHelper;
import com.example.finalexam.model.ProjectData;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ItemHolder> {

    private static final String TAG = "ItemAdapter";
    public static int clickId = 114;

    private List<ProjectData> all;
    private List<ProjectData> self;
    private List<ProjectData> monitor;
    private List<ProjectData> tempList = new ArrayList<>();
    private int projectNum = 0;
    private int allNum = 0;
    private int selfNum = 0;
    private int monitorNum = 0;

    private Context context;

    public ProjectAdapter(Context context, List<ProjectData> all, List<ProjectData> self, List<ProjectData> monitor) {
        this.context = context;
        this.all = all;
        this.self = self;
        this.monitor = monitor;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.project_rv_item, parent, false);
        try {
            allNum = all.size();
            selfNum = self.size();
            monitorNum = monitor.size();

            tempList.addAll(self);
            tempList.addAll(monitor);
            all.removeAll(self);
            all.removeAll(monitor);
            tempList.addAll(all);
            all.clear();
            all.addAll(tempList);
            tempList.clear();
        } catch (NullPointerException e) {
            throw e;
        }
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        if (all == null) return;
        else if (all.isEmpty()) return;

        ProjectData data = all.get(position);

        showProjectData(holder, data);
        setListener(holder, data);
    }

    private void setListener(ItemHolder holder, ProjectData data) {
        holder.projectRVItem.setOnClickListener(v -> {
            clickId = data.getProjectId();
            context.startActivity(new Intent(context, ProjectDetailActivity.class));
        });
    }

    private void showProjectData(ItemHolder holder, ProjectData data) {
        //获取项目名字和发布者名字
        String projectName = data.getProjectName();
        String creatorName = data.getCreator();
        projectName = projectName == null ? "projectName" : projectName;
        creatorName = creatorName == null ? "creatorName" : creatorName;

        //获取颜色
        String creatorColor = "#FFFFFF";
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
        if (all == null) return 0;
        return all.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout projectRVItem;
        public TextView projectName;
        public TextView creatorName;
        public TextView creatorColor;
        public View noMonitor;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            projectRVItem = itemView.findViewById(R.id.project_rv_item);
            projectName = itemView.findViewById(R.id.project_name);
            creatorName = itemView.findViewById(R.id.project_creator_name);
            creatorColor = itemView.findViewById(R.id.project_creator_color);
            noMonitor = itemView.findViewById(R.id.project_item_no_monitor);
        }
    }

}

