package com.example.finalexam.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalexam.R;
import com.example.finalexam.adapter.ProjectAdapter;
import com.example.finalexam.helper.ProjectListSortHelper;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.presenter.UserPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ProjectOverviewFragment extends Fragment implements UserDataShowInterface {
    private View view;
    public static final List<ProjectData> list = new ArrayList<>();
    public static final List<ProjectData> tempList = new ArrayList<>();
    private int requestNum = 0;
    private RecyclerView projectRV;
    private ProjectAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_project_overview, container, false);
        initView();
        initRV();
        requestData();
        return view;
    }

    private void requestData() {
        UserPresenter.getInstance(this).fetchFreezeOrNotProject(UserPresenter.PROJECT_ACTIVE);
        UserPresenter.getInstance(this).fetchFreezeOrNotProject(UserPresenter.PROJECT_FROZEN);
    }

    private void initRV() {
        projectRV.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ProjectAdapter(requireContext(), list, tempList, new ArrayList<>());
        projectRV.setAdapter(adapter);
    }

    private void initView() {
        projectRV = view.findViewById(R.id.manager_project_list);
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
    public void projectPublishResult(int STATUS) {

    }

    @SuppressLint("NotifyDataSetChanged")
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
        if (requestNum++ == 0) {
            list.clear();
            tempList.clear();
        }

        if (STATUS == UserPresenter.STATUS_SUCCESS) {
            list.addAll(UserPresenter.getInstance(this).getProjectList());
            ProjectListSortHelper.sortWithCreator(list);
            for (ProjectData data : list) {
                if (data.getCreator() == null)
                    data.setCreator("冻结");
            }
            tempList.addAll(UserPresenter.getInstance(this).getProjectList());
        }
        if (requestNum == 2)
            adapter.resetList(2);
    }

    @Override
    public void applyOrNotProjectList(int STATUS) {

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
}