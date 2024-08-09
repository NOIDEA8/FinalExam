package com.example.finalexam.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalexam.R;
import com.example.finalexam.activity.UserDesktop;
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

    private final int ALL_PROJECT = 0;
    private final int SELF_PROJECT = 1;
    private final int MONITOR_PROJECT = 2;
    private int projectType = 0;
    private static final List<ProjectData> allProjectList = new ArrayList<>();
    private static final List<ProjectData> selfProjectList = new ArrayList<>();
    private static final List<ProjectData> monitorProjectList = new ArrayList<>();//监管的且非自己发布的项目
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
        projectData1.setProjectId(114);
        projectData2.setCreator("PPPoria");
        projectData2.setProjectId(514);
        allProjectList.add(projectData1);
        allProjectList.add(projectData2);
        allProjectList.add(new ProjectData());
    }

    private void initRV() {
        projectListView.setLayoutManager(new LinearLayoutManager(requireContext()));
        projectListView.setAdapter(new ProjectAdapter(requireContext(), allProjectList, selfProjectList, monitorProjectList));
    }

    private void initView() {
        projectListView = view.findViewById(R.id.project_list);
    }

    @Override
    public void applyMonitorPermission(int STATUS) {

    }

    @Override
    public void checkMonitorResult(int STATUS) {

    }

    @Override
    public void freeze(int STATUS) {

    }

    @Override
    public void userLog(int STATUS) {

    }

    @Override
    public void userRegister(int STATUS) {

    }

    @Override
    public void userListResult(int STATUS) {

    }

    @Override
    public void userDetail(int STATUS) {

    }

    @Override
    public void verify(int STATUS) {

    }

    @Override
    public void projectPublishResult(int STATUS) {

    }

    @Override
    public void briefProjectList(int STATUS) {

    }

    @Override
    public void selfProjectList(int STATUS) {

    }

    @Override
    public void monitorProjectList(int STATUS) {

    }

    @Override
    public void applyingMonitorProjectList(int STATUS) {

    }

    @Override
    public void applyingProjectList(int STATUS) {

    }

    @Override
    public void projectDetail(int STATUS) {

    }

    @Override
    public void updateProject(int STATUS) {

    }

    @Override
    public void cancelMonitor(int STATUS) {

    }

    @Override
    public void deleteProject(int STATUS) {

    }

    @Override
    public void freezeOrNotProjectList(int STATUS) {

    }

    @Override
    public void applyOrNotProjectList(int STATUS) {

    }
}