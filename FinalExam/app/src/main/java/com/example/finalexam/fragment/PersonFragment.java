package com.example.finalexam.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalexam.R;
import com.example.finalexam.activity.LogActivity;
import com.example.finalexam.activity.MyApplyActivity;
import com.example.finalexam.activity.OtherApplyActivity;
import com.example.finalexam.helper.ColorHelper;
import com.example.finalexam.helper.ProjectListSortHelper;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.model.UserData;
import com.example.finalexam.presenter.SPPresenter;
import com.example.finalexam.presenter.UserPresenter;
import com.example.finalexam.presenter.WebSocketPresenter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PersonFragment extends Fragment implements UserDataShowInterface {
    private View view;

    private TextView greetView;
    private TextView colorView;
    private TextView nameView;
    private TextView logoutButton;
    private ConstraintLayout toMineButton;
    private TextView mineNumView;
    private ConstraintLayout toOtherButton;
    private TextView otherNumView;

    private int requestNum = 0;
    public static final List<ProjectData> myProjects = new ArrayList<>();
    public static final List<ProjectData> myApplications = new ArrayList<>();
    public static final List<ProjectData> otherApplications = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person, container, false);

        addTestData();
        initView();
        initListener();
        requestData();

        return view;
    }

    private void addTestData() {
        ProjectData test1 = new ProjectData();
        test1.setCreator("Poria");
        myProjects.add(test1);

        ProjectData test2 = new ProjectData();
        test2.setCreator("YOASOBI");
        myApplications.add(test2);

        ProjectData test3 = new ProjectData();
        test3.setCreator("たぶん");
        otherApplications.add(test3);
    }

    @SuppressLint("SetTextI18n")
    private void showData() {
        //问候语
        int hour = LocalDateTime.now().getHour();
        String greet = "您好";
        if (hour < 5) greet = "凌晨了";
        else if (hour < 11) greet = "早上好";
        else if (hour < 13) greet = "中午好";
        else if (hour < 18) greet = "下午好";
        else if (hour < 24) greet = "晚上好";
        greetView.setText(greet);

        //名字，以及头像
        String name = UserPresenter.getInstance(this).getUserName(UserPresenter.getContext());
        name = name == null ? "null" : name;
        nameView.setText(name);
        int color = Color.parseColor(ColorHelper.createColorHex(name));
        colorView.setText(name.substring(0, 1));
        colorView.setBackgroundTintList(ColorStateList.valueOf(color));
        if (!ColorHelper.isBrightColor(color))
            colorView.setTextColor(Color.WHITE);

        //显示申请数
        mineNumView.setText("共" + (myProjects.size() + myApplications.size()) + "条");
        otherNumView.setText("共" + otherApplications.size() + "条");
    }

    private void requestData() {
        UserPresenter.getInstance(this).fetchMyApplyingProject();//我发布或更改的记录
        UserPresenter.getInstance(this).fetchApplyingMonitorProject();//我申请监视他人项目的记录
        UserPresenter.getInstance(this).fetchApplication();//他人申请监视我的项目的记录
    }

    private void initListener() {
        logoutButton.setOnClickListener(v -> {
            SPPresenter.accordLoggedStatus(UserPresenter.getContext(), false);
            startActivity(new Intent(UserPresenter.getContext(), LogActivity.class));
            int userId = UserPresenter.getInstance(this).getUserId();//关闭WebSocket
            WebSocketPresenter.getInstance(UserPresenter.getContext()).getWebSocketClient(userId).close();
            requireActivity().finish();
        });
        toMineButton.setOnClickListener(v -> startActivity(new Intent(UserPresenter.getContext(), MyApplyActivity.class)));
        toOtherButton.setOnClickListener(v -> startActivity(new Intent(UserPresenter.getContext(), OtherApplyActivity.class)));
    }

    private void initView() {
        greetView = view.findViewById(R.id.greet);
        colorView = view.findViewById(R.id.person_color);
        nameView = view.findViewById(R.id.person_name);
        logoutButton = view.findViewById(R.id.person_logout_button);
        toMineButton = view.findViewById(R.id.person_my_apply_layout);
        mineNumView = view.findViewById(R.id.person_my_apply_num);
        toOtherButton = view.findViewById(R.id.person_other_apply_layout);
        otherNumView = view.findViewById(R.id.person_other_apply_num);
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
        if (STATUS == UserPresenter.STATUS_SUCCESS) {
            myApplications.addAll(UserPresenter.getInstance(this).getProjectList());
            ProjectListSortHelper.sortWithCreator(myApplications);
        }

        if (++requestNum == 3) {
            requestNum = 0;
            showData();
        }
    }

    @Override
    public void applyingProjectList(int STATUS) {
        if (STATUS == UserPresenter.STATUS_SUCCESS) {
            myProjects.addAll(UserPresenter.getInstance(this).getProjectList());
            ProjectListSortHelper.sortWithCreator(myProjects);
        }

        if (++requestNum == 3) {
            requestNum = 0;
            showData();
        }
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
        if (STATUS == UserPresenter.STATUS_SUCCESS) {
            otherApplications.addAll(UserPresenter.getInstance(this).getProjectList());
            ProjectListSortHelper.sortWithCreator(otherApplications);
        }

        if (++requestNum == 3) {
            requestNum = 0;
            showData();
        }
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
}