package com.example.finalexam.activity;

import android.os.Bundle;
import android.util.Log;
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
import com.example.finalexam.fragment.ManagerPageFragment;
import com.example.finalexam.fragment.ProjectOverviewFragment;
import com.example.finalexam.fragment.UserOverviewFragment;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.UserData;
import com.example.finalexam.presenter.WebSocketPresenter;

import java.util.List;

public class ManagerDesktop extends BaseActivity implements UserDataShowInterface {

    private static final String TAG = "ManagerDesktop";

    private FragmentManager fragmentManager;
    private FrameLayout container;
    private UserOverviewFragment userOverviewFragment;
    private ManagerPageFragment managerPageFragment;
    private ProjectOverviewFragment projectFragment;
    private int pagePosition = 0;

    private ConstraintLayout userOverviewButton;
    private ConstraintLayout applicationPageButton;
    private ConstraintLayout personPageButton;

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
    private void initPage() {
        userOverviewFragment=new UserOverviewFragment();
        managerPageFragment=new ManagerPageFragment();
        projectFragment=new ProjectOverviewFragment();


        fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.user_pagerContainer, userOverviewFragment);
        transaction.commit();
    }

    private void initListener() {
        userOverviewButton.setOnClickListener(v->{
            changePageTo(0);
        });
        applicationPageButton.setOnClickListener(v->{
            changePageTo(1);
        });
        personPageButton.setOnClickListener(v->{
            changePageTo(2);
        });
    }

    private void initView() {
        container = findViewById(R.id.user_pagerContainer);
        userOverviewButton = findViewById(R.id.user_overview_button);
        applicationPageButton = findViewById(R.id.application_page_button);
        personPageButton = findViewById(R.id.person_page_button);
    }

    private void changePageTo(int pagePosition){
        //防止浪费算力或丢失已经绘制的画面
        if (this.pagePosition == pagePosition) return;
        else this.pagePosition = pagePosition;

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (pagePosition == 0){
            transaction.replace(R.id.user_pagerContainer, userOverviewFragment);
            Log.d(TAG, "ManagerDeskTop change page to userOverviewFragment");
        } else if (pagePosition == 1) {
            transaction.replace(R.id.user_pagerContainer, projectFragment);
            Log.d(TAG, "ManagerDeskTop change page to ProjectFragment");
        } else{
            transaction.replace(R.id.user_pagerContainer, managerPageFragment);
            Log.d(TAG, "ManagerDeskTop change page to managerPageFragment");
        }
        transaction.commit();//
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
    public void UserOnlineOrNotList(List<UserData> userData) {

    }
}