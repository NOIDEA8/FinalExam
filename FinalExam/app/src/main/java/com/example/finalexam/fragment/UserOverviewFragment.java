package com.example.finalexam.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalexam.R;
import com.example.finalexam.adapter.ProjectAdapter;
import com.example.finalexam.adapter.UsersAdapter;
import com.example.finalexam.helper.ColorHelper;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.model.UserData;

import java.util.ArrayList;
import java.util.List;

public class UserOverviewFragment extends Fragment {
    private View view;
    public static final List<UserData> list = new ArrayList<>();
    private RecyclerView usersRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_user_overview, container, false);
        addTestData();
        initView();
        initRV();
        return view;
    }

    private void addTestData() {
        UserData data1 = new UserData();
        data1.setEnabled("冻结");
        data1.setUsername("刀酱");
        data1.setIsOnline("offline");
        UserData data2 = new UserData();
        data2.setUsername("Zoki");

        list.add(data1);
        list.add(data2);
    }

    private void initRV() {
        usersRV.setLayoutManager(new LinearLayoutManager(requireContext()));
        usersRV.setAdapter(new UsersAdapter(requireContext(), list));
    }

    private void initView() {
        usersRV = view.findViewById(R.id.user_overview_list);
    }
}