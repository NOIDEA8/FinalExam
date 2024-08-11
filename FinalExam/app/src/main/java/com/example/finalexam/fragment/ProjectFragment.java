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

        addTestData();
        initView();
        initRV();
        requestData();

        return view;
    }


    private void requestData() {
        UserPresenter.getInstance(this).fetchAllBriefProject();
        UserPresenter.getInstance(this).fetchSelfProjects();
        UserPresenter.getInstance(this).fetchMonitorProjects();
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
    public void UserOnlineOrNotList(List<UserData> userData) {

    }

    @Override
    public void projectPublishResult(int STATUS) {

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void briefProjectList(int STATUS) {
        if (STATUS == UserPresenter.STATUS_NO_DATA) {
            Toast.makeText(requireContext(), "暂无项目", Toast.LENGTH_SHORT).show();
        } else if (STATUS == UserPresenter.STATUS_SUCCESS) {
            allProjectList.clear();
            allProjectList.addAll(UserPresenter.getInstance(this).getProjectList());
            ProjectListSortHelper.sortWithCreator(allProjectList);
        }

        if (++requestNum == 3) {
            requestNum = 0;
            Objects.requireNonNull(projectListView.getAdapter()).notifyDataSetChanged();
            Log.d(TAG, "RV notify set change");
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void selfProjectList(int STATUS) {
        if (STATUS == UserPresenter.STATUS_NO_DATA) {
            Toast.makeText(requireContext(), "暂无项目", Toast.LENGTH_SHORT).show();
        } else if (STATUS == UserPresenter.STATUS_SUCCESS) {
            selfProjectList.clear();
            selfProjectList.addAll(UserPresenter.getInstance(this).getProjectList());
            ProjectListSortHelper.sortWithCreator(selfProjectList);
        }

        if (++requestNum == 3) {
            requestNum = 0;
            Objects.requireNonNull(projectListView.getAdapter()).notifyDataSetChanged();
            Log.d(TAG, "RV notify set change");
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void monitorProjectList(int STATUS) {
        if (STATUS == UserPresenter.STATUS_NO_DATA) {
            Toast.makeText(UserPresenter.getContext(), "暂无项目", Toast.LENGTH_SHORT).show();
        } else if (STATUS == UserPresenter.STATUS_SUCCESS) {
            monitorProjectList.clear();
            monitorProjectList.addAll(UserPresenter.getInstance(this).getProjectList());
            ProjectListSortHelper.sortWithCreator(monitorProjectList);
        }

        if (++requestNum == 3) {
            requestNum = 0;
            Objects.requireNonNull(projectListView.getAdapter()).notifyDataSetChanged();
            Log.d(TAG, "RV notify set change");
        }
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