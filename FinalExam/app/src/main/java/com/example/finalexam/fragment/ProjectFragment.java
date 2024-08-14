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
import com.example.finalexam.adapter.ProjectAdapter;
import com.example.finalexam.helper.ProjectListSortHelper;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.model.UserData;
import com.example.finalexam.presenter.UserPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ProjectFragment extends Fragment implements UserDataShowInterface {
    private static final String TAG = "ProjectFragment";
    private View view;

    private int requestNum = 0;
    private static final List<ProjectData> allProjectList = new ArrayList<>();
    private static final List<ProjectData> selfProjectList = new ArrayList<>();
    private static final List<ProjectData> monitorProjectList = new ArrayList<>();//监管的且非自己发布的项目
    private RecyclerView projectListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project, container, false);

        //addTestData();
        initView();
        initRV();
        requestData();

        return view;
    }


    private void requestData() {
        UserPresenter.getInstance(this).fetchAllBriefProject();
    }

    private void addTestData() {
        ProjectData monitor = new ProjectData();
        monitor.setCreator("NOIDEA");
        monitor.setProjectId(114);

        ProjectData self1 = new ProjectData();
        self1.setCreator("Poria");
        self1.setProjectId(514);

        ProjectData self2 = new ProjectData();
        self2.setCreator("Poria");
        self2.setProjectId(1919);

        monitorProjectList.add(monitor);
        selfProjectList.add(self1);
        selfProjectList.add(self2);
        allProjectList.add(new ProjectData());
        allProjectList.add(new ProjectData());
        allProjectList.add(new ProjectData());
        allProjectList.addAll(selfProjectList);
        allProjectList.addAll(monitorProjectList);
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
    public void monitorUserListResult(int STATUS) {

    }

    @Override
    public void userDetail(int STATUS) {

    }

    @Override
    public void verify(int STATUS) {

    }

    @Override
    public void application(int STATUS) {

    }

    @Override
    public void attackServerLogList(int STATUS) {

    }

    @Override
    public void allUserOperationLogList(int STATUS) {

    }

    @Override
    public void logDataListByGroup(int STATUS) {

    }

    @Override
    public void projectPresentationDateOneWeek(int STATUS) {

    }

    @Override
    public void ViewProjectOperateLog(int STATUS) {

    }

    @Override
    public void increaseView(int STATUS) {

    }

    @Override
    public void verifyMonitorApplication(int STATUS) {

    }

    @Override
    public void setErrorRate(int STATUS) {

    }

    @Override
    public void logDetail(int STATUS) {

    }

    @Override
    public void weekLogNum(int STATUS) {

    }


    @Override
    public void projectPublishResult(int STATUS) {

    }

    @Override
    public void briefProjectList(int STATUS) {
        if (STATUS == UserPresenter.STATUS_SUCCESS || STATUS == UserPresenter.STATUS_NO_DATA) {
            allProjectList.clear();
            allProjectList.addAll(UserPresenter.getInstance(this).getProjectList());
            ProjectListSortHelper.sortWithCreator(allProjectList);
        }
        UserPresenter.getInstance(this).fetchSelfProjects();
    }

    @Override
    public void selfProjectList(int STATUS) {
        if (STATUS == UserPresenter.STATUS_SUCCESS || STATUS == UserPresenter.STATUS_NO_DATA) {
            selfProjectList.clear();
            selfProjectList.addAll(UserPresenter.getInstance(this).getProjectList());
            String creator = UserPresenter.getUserName(UserPresenter.getContext());
            for (ProjectData data : selfProjectList) {
                data.setCreator(creator);
            }
        }
        UserPresenter.getInstance(this).fetchMonitorProjects();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void monitorProjectList(int STATUS) {
        if (STATUS == UserPresenter.STATUS_SUCCESS || STATUS == UserPresenter.STATUS_NO_DATA) {
            monitorProjectList.clear();
            monitorProjectList.addAll(UserPresenter.getInstance(this).getProjectList());
            ProjectListSortHelper.sortWithCreator(monitorProjectList);
        }
        Objects.requireNonNull(projectListView.getAdapter()).notifyDataSetChanged();
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