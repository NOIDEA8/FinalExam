package com.example.finalexam.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalexam.R;
import com.example.finalexam.adapter.ProjectAdapter;
import com.example.finalexam.model.ProjectData;

import java.util.ArrayList;
import java.util.List;


public class ProjectFragment extends Fragment {
    private static final String TAG = "ProjectFragment";
    private View view;

    private static final List<ProjectData> list = new ArrayList<>();
    private RecyclerView projectListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project, container, false);

        initView();
        initRV();

        return view;
    }

    private void initRV() {
        projectListView.setLayoutManager(new LinearLayoutManager(requireContext()));

        list.add(new ProjectData());
        list.add(new ProjectData());
        list.add(new ProjectData());

        projectListView.setAdapter(new ProjectAdapter(requireContext(), list));
    }

    private void initView() {
        projectListView = view.findViewById(R.id.project_list);
    }
}