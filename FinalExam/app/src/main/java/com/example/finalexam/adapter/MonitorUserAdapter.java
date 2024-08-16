package com.example.finalexam.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.R;
import com.example.finalexam.helper.ColorHelper;
import com.example.finalexam.model.UserData;

import java.util.List;

public class MonitorUserAdapter extends RecyclerView.Adapter<MonitorUserAdapter.ItemHolder> {
    private final List<UserData> list;
    private final Context context;
    private int num;


    public MonitorUserAdapter(Context context, List<UserData> list) {
        this.context = context;
        this.list = list;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void resetList() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.monitor_user_item, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        String name = list.get(position * 2).getUsername();
        Log.d("Test", name);
        String colorHex = ColorHelper.createColorHex(name);
        int color = Color.parseColor(colorHex);
        holder.userName1.setText(name);
        holder.userColor1.setText(name.substring(0, 1));
        holder.userColor1.setBackgroundTintList(ColorStateList.valueOf(color));
        if (ColorHelper.isBrightColor(color))
            holder.userColor1.setTextColor(Color.BLACK);
        else
            holder.userColor1.setTextColor(Color.WHITE);

        if (position * 2 + 1 == num) return;

        name = list.get(position * 2 + 1).getUsername();
        colorHex = ColorHelper.createColorHex(name);
        color = Color.parseColor(colorHex);
        holder.userName2.setText(name);
        holder.userColor2.setText(name.substring(0, 1));
        holder.userColor2.setBackgroundTintList(ColorStateList.valueOf(color));
        if (ColorHelper.isBrightColor(color))
            holder.userColor2.setTextColor(Color.BLACK);
        else
            holder.userColor2.setTextColor(Color.WHITE);
    }

    @Override
    public int getItemCount() {
        num = list.size();
        return num % 2 == 0 ? num / 2 : num / 2 + 1;
    }

    public static class ItemHolder extends RecyclerView.ViewHolder {
        public TextView userColor1;
        public TextView userName1;
        public TextView userColor2;
        public TextView userName2;

        public ItemHolder(@NonNull View view) {
            super(view);
            userColor1 = view.findViewById(R.id.monitor_user1);
            userName1 = view.findViewById(R.id.monitor_user1_name);
            userColor2 = view.findViewById(R.id.monitor_user2);
            userName2 = view.findViewById(R.id.monitor_user2_name);
        }
    }

}
