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

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ItemHolder> {

    private static final String TAG = "ItemAdapter";
    public static int clickId = 114;

    private List<ProjectData> list;

    private Context context;

    public ProjectAdapter(Context context, List<ProjectData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.project_rv_item, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        if (list == null) return;
        else if (list.isEmpty()) return;

        ProjectData data = list.get(position);

        showProjectData(holder,data);
        setListener(holder,data);
    }

    private void setListener(ItemHolder holder,ProjectData data) {
        holder.projectRVItem.setOnClickListener(v->{
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

        //判断是否为深色，是则把字体改为白色，更加显眼
        if (ColorHelper.isBrightColor(color))
            holder.creatorColor.setTextColor(Color.BLACK);
        else
            holder.creatorColor.setTextColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout projectRVItem;
        public TextView projectName;
        public TextView creatorName;
        public TextView creatorColor;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            projectRVItem = itemView.findViewById(R.id.project_rv_item);
            projectName = itemView.findViewById(R.id.project_name);
            creatorName = itemView.findViewById(R.id.project_creator_name);
            creatorColor = itemView.findViewById(R.id.project_creator_color);
        }
    }

}

