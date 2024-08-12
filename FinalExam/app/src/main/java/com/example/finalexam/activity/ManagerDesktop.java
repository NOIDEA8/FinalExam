package com.example.finalexam.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalexam.R;
import com.example.finalexam.baseappcompatactivity.BaseActivity;
import com.example.finalexam.fragment.ApplyOverviewFragment;
import com.example.finalexam.fragment.ProjectOverviewFragment;
import com.example.finalexam.fragment.UserOverviewFragment;
import com.example.finalexam.helper.UserDataShowInterface;

public class ManagerDesktop extends BaseActivity implements UserDataShowInterface, View.OnClickListener {

    private static final String TAG = "ManagerDesktop";

    private FrameLayout projectContainer;
    private FrameLayout applyContainer;
    private FrameLayout userContainer;
    private int pagePosition = 0;

    private ConstraintLayout projectButton;
    private ConstraintLayout applyButton;
    private ConstraintLayout userButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manager_desktop);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setNavigationBarColor(getColor(R.color.white));

        initView();
        initPage();
        initListener();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == 0) {

        } else if (id == R.id.manager_project_page_button)
            changePageTo(0);
        else if (id == R.id.manager_apply_page_button)
            changePageTo(1);
        else if (id == R.id.manager_user_page_button)
            changePageTo(2);
    }

    private void initPage() {
        ProjectOverviewFragment projectFragment = new ProjectOverviewFragment();
        ApplyOverviewFragment applyFragment = new ApplyOverviewFragment();
        UserOverviewFragment userFragment = new UserOverviewFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.manager_project_container, projectFragment);
        transaction.add(R.id.manager_apply_container, applyFragment);
        transaction.add(R.id.manager_user_container, userFragment);
        transaction.commit();
    }

    private void initListener() {
        projectButton.setOnClickListener(this);
        applyButton.setOnClickListener(this);
        userButton.setOnClickListener(this);
    }

    private void initView() {
        projectContainer = findViewById(R.id.manager_project_container);
        applyContainer = findViewById(R.id.manager_apply_container);
        userContainer = findViewById(R.id.manager_user_container);
        projectButton = findViewById(R.id.manager_project_page_button);
        applyButton = findViewById(R.id.manager_apply_page_button);
        userButton = findViewById(R.id.manager_user_page_button);
    }

    private void changePageTo(int pagePosition) {
        //防止浪费算力或丢失已经绘制的画面
        if (this.pagePosition == pagePosition) return;
        else this.pagePosition = pagePosition;

        if (pagePosition == 0) {
            projectContainer.setVisibility(View.VISIBLE);
            applyContainer.setVisibility(View.INVISIBLE);
            userContainer.setVisibility(View.INVISIBLE);
        } else if (pagePosition == 1) {
            projectContainer.setVisibility(View.INVISIBLE);
            applyContainer.setVisibility(View.VISIBLE);
            userContainer.setVisibility(View.INVISIBLE);
        } else if (pagePosition == 2) {
            projectContainer.setVisibility(View.INVISIBLE);
            applyContainer.setVisibility(View.INVISIBLE);
            userContainer.setVisibility(View.VISIBLE);
        }
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
}