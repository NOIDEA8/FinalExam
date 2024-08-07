package com.example.finalexam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.R;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ItemHolder>{

    private static final String TAG = "ItemAdapter";

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

    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public TextView projectName;
        public TextView userName;
        public TextView userColor;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            projectName = itemView.findViewById(R.id.project_name);
            userName = itemView.findViewById(R.id.project_user_name);
            userColor = itemView.findViewById(R.id.project_user_color);
        }
    }

}

