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
import com.example.finalexam.adapter.ApplyAdapter;
import com.example.finalexam.helper.ProjectListSortHelper;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.presenter.UserPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ApplyOverviewFragment extends Fragment implements UserDataShowInterface {
    private View view;
    public static final List<ProjectData> list = new ArrayList<>();
    private static RecyclerView applyRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manager_page, container, false);
        addTestData();
        initView();
        initRV();
        requestData();
        return view;
    }

    private void requestData() {
        UserPresenter.getInstance(this).fetchReviewOrNotProject(UserPresenter.PROJECT_PROCESSING);
    }

    private void addTestData() {
        ProjectData data1 = new ProjectData();
        data1.setCreator("醒觉");
        data1.setProjectName("HuiDianWo");
        data1.setDescription("感觉不如原神");
        data1.setProjectUrl("https://apifox.com/apidoc/shared-0176effd-6d34-4849-8bd7-54759c5a0e9a/api-203512238");

        ProjectData data2 = new ProjectData();
        data2.setCreator("如泣似诉");

        list.add(data1);
        list.add(data2);
    }

    private void initRV() {
        applyRV.setLayoutManager(new LinearLayoutManager(requireContext()));
        applyRV.setAdapter(new ApplyAdapter(requireContext(), list));
    }

    @SuppressLint("NotifyDataSetChanged")
    public static void notifyChange(){
        Objects.requireNonNull(applyRV.getAdapter()).notifyDataSetChanged();
    }

    private void initView() {
        applyRV = view.findViewById(R.id.manager_apply_list);
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

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void applyOrNotProjectList(int STATUS) {
        if (STATUS == UserPresenter.STATUS_SUCCESS) {
            list.clear();
            list.addAll(UserPresenter.getInstance(this).getProjectList());
            ProjectListSortHelper.sortWithCreator(list);
            Objects.requireNonNull(applyRV.getAdapter()).notifyDataSetChanged();
        }
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
}