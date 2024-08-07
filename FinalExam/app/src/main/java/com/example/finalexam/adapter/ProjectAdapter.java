package com.example.finalexam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.R;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ItemHolder>{

    private static final String TAG = "ItemAdapter";

    private List<ProjectData> itemList;

    private Context context;

    public ProjectAdapter(Context context, List<ProjectData> itemList) {
        this.context = context;
        this.itemList = itemList;
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
        if (itemList == null) return 0;
        return itemList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

        }

        @Override
        public void onClick(View v) {

        }
    }

}

