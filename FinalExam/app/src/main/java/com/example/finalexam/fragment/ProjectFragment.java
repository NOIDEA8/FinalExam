package com.example.finalexam.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalexam.R;
import com.example.finalexam.adapter.ProjectAdapter;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.presenter.UserPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ProjectFragment extends Fragment implements UserDataShowInterface {
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
        requestData();

        return view;
    }


    private void requestData() {
        UserPresenter.getInstance(this).fetchAllBriefProject();
    }

    private void addTestData() {
        ProjectData projectData1 = new ProjectData();
        ProjectData projectData2 = new ProjectData();
        projectData1.setCreator("NOIDEA8");
        projectData2.setCreator("PPPoria");
        list.add(projectData1);
        list.add(projectData2);
        list.add(new ProjectData());
    }

    private void initRV() {
        projectListView.setLayoutManager(new LinearLayoutManager(requireContext()));
        projectListView.setAdapter(new ProjectAdapter(requireContext(), list));
    }

    private void initView() {
        projectListView = view.findViewById(R.id.project_list);
    }

    @Override
    public void applyMonitorPermission(int STATUS) {

    }

    @Override
    public void userLog(int STATUS) {

    }

    @Override
    public void userRegister(int STATUS) {

    }

    @Override
    public void projectPublishResult(int STATUS) {

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void projectListResult(int STATUS) {
        if (STATUS == UserPresenter.STATUS_NO_DATA) {

        } else if (STATUS == UserPresenter.STATUS_SUCCESS) {
            list.clear();
            list.addAll(UserPresenter.getInstance(this).getProjectList());
        }
        addTestData();
        Objects.requireNonNull(projectListView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void projectDetail(int STATUS) {

    }
}