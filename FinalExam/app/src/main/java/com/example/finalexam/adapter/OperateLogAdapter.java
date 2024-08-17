package com.example.finalexam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.R;
import com.example.finalexam.activity.ProjectDetailActivity;
import com.example.finalexam.model.LogData;

import java.util.List;

public class OperateLogAdapter extends RecyclerView.Adapter<OperateLogAdapter.LogHolder> {

private Context context;
private final List<LogData> list;
public static LogData data;

public OperateLogAdapter(Context context, List<LogData> list) {
    this.context = context;
    this.list = list;
}

@NonNull
@Override
public OperateLogAdapter.LogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(context).inflate(R.layout.log_rv_item, parent, false);
    return new OperateLogAdapter.LogHolder(itemView);
}

@Override
public void onBindViewHolder(@NonNull OperateLogAdapter.LogHolder holder, int position) {
    LogData logData = list.get(position);
    String oneLine;
    String type;
    String time;

    String request=logData.getIllegalRequest();
    String name=logData.getUserName();
    String illegalRequestTimelogData=logData.getIllegalRequestTime();
    String OperateTime=logData.getOperateTime();
    String operate=logData.getOperate();
    String ipAddress=logData.getIpAddress();

    if(ipAddress!=null) oneLine=ipAddress;
    else oneLine=name;
    if (request!=null) type=oneLine+request;
    else type=oneLine+operate;
    if(OperateTime!=null)time=OperateTime;
    else time=illegalRequestTimelogData;

    holder.logTypeView.setText(type);
    holder.logTimeView.setText(time);
    holder.logRVItem.setOnClickListener(v -> {
        data = logData;
        ProjectDetailActivity.callLogLayout();
    });

}

@Override
public int getItemCount() {
    return list.size();
}

public static class LogHolder extends RecyclerView.ViewHolder {
    public ConstraintLayout logRVItem;
    public TextView logTypeView;
    public TextView logTimeView;

    public LogHolder(@NonNull View view) {
        super(view);
        logRVItem = view.findViewById(R.id.log_item);
        logTypeView = view.findViewById(R.id.log_item_type);
        logTimeView = view.findViewById(R.id.log_item_time);
    }
}
}

