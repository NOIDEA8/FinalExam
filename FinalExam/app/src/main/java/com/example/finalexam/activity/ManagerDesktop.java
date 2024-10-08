package com.example.finalexam.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.finalexam.R;
import com.example.finalexam.adapter.UsersAdapter;
import com.example.finalexam.baseappcompatactivity.BaseActivity;
import com.example.finalexam.fragment.ApplyOverviewFragment;
import com.example.finalexam.fragment.ProjectOverviewFragment;
import com.example.finalexam.fragment.UserOverviewFragment;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.UserData;
import com.example.finalexam.overrideview.any.AnyView;
import com.example.finalexam.presenter.UserPresenter;
import com.example.finalexam.presenter.WebSocketPresenter;

public class ManagerDesktop extends BaseActivity implements UserDataShowInterface, View.OnClickListener {

    private static final String TAG = "ManagerDesktop";

    private FrameLayout projectContainer;
    private FrameLayout applyContainer;
    private FrameLayout userContainer;
    private int pagePosition = 0;

    private ConstraintLayout projectButton;
    private AnyView projectIcon;
    private AnyView projectIcon2;
    private TextView projectTip;

    private ConstraintLayout applyButton;
    private AnyView applyIcon;
    private AnyView applyIcon2;
    private TextView applyTip;

    private ConstraintLayout userButton;
    private AnyView userIcon;
    private AnyView userIcon2;
    private TextView userTip;

    private static ConstraintLayout userBackground;
    private static ConstraintLayout userLayout;
    private static TextView nameView;
    private static TextView createTimeView;
    private static TextView enabledView;
    private static TextView onlineView;
    private static TextView toEnabledButton;
    private static TextView onlineButton;
    private ConstraintLayout enabledBackground;
    private ConstraintLayout enabledLayout;
    private NumberPicker enabledDayView;
    private NumberPicker enabledHourView;
    private TextView enabledButton;
    private static UserData data;

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
        overrideBackMethod();
        initView();
        initPage();
        initListener();
    }

    private void overrideBackMethod() {
        //重写回退方法
        OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (enabledBackground.getVisibility() == View.VISIBLE)
                    enabledBackground.setVisibility(View.INVISIBLE);
                else if (userBackground.getVisibility() == View.VISIBLE)
                    userBackground.setVisibility(View.INVISIBLE);
                else toLog();
            }
        };
        dispatcher.addCallback(callback);
    }

    private void toLog() {
        WebSocketPresenter.stopHeart();
        WebSocketPresenter.getInstance(this).getWebSocketClient(-1).close();
        startActivity(new Intent(this, LogActivity.class));
        finish();
    }

    public static void callUserLayout() {
        data = UsersAdapter.data;
        userBackground.setVisibility(View.VISIBLE);
        nameView.setText(data.getUsername());
        createTimeView.setText(data.getCreateTime());
        enabledView.setText(data.getEnabled());
        onlineView.setText(data.getIsOnline());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.manager_project_page_button)
            changePageTo(0);
        else if (id == R.id.manager_apply_page_button)
            changePageTo(1);
        else if (id == R.id.manager_user_page_button)
            changePageTo(2);
        else if (id == R.id.user_background)
            userBackground.setVisibility(View.INVISIBLE);
        else if (id == R.id.user_enabled_button)
            enabledBackground.setVisibility(View.VISIBLE);
        else if (id == R.id.user_online_button) {
            WebSocketPresenter.getInstance(this).adminSendOffset(data.getUserId());
            data.setIsOnline("offline");

        } else if (id == R.id.enabled_background)
            enabledBackground.setVisibility(View.INVISIBLE);
        else if (id == R.id.enabled_button) {
            int day = enabledDayView.getValue();
            int hour = enabledHourView.getValue();
            UserPresenter.getInstance(this).freezeUser(data.getUserId(), day * 24 + hour);
            enabledBackground.setVisibility(View.INVISIBLE);
        }
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
        userBackground.setOnClickListener(this);
        userLayout.setOnClickListener(this);
        toEnabledButton.setOnClickListener(this);
        onlineButton.setOnClickListener(this);
        enabledBackground.setOnClickListener(this);
        enabledLayout.setOnClickListener(this);
        enabledButton.setOnClickListener(this);
    }

    private void initView() {
        projectContainer = findViewById(R.id.manager_project_container);
        applyContainer = findViewById(R.id.manager_apply_container);
        userContainer = findViewById(R.id.manager_user_container);

        projectButton = findViewById(R.id.manager_project_page_button);
        projectIcon = findViewById(R.id.manager_project_icon);
        projectIcon.setIconByName("projectIconStroke");
        projectIcon2 = findViewById(R.id.manager_project_icon2);
        projectIcon2.setIconByName("projectIconPink");
        projectTip = findViewById(R.id.manager_project_tip);

        applyButton = findViewById(R.id.manager_apply_page_button);
        applyIcon = findViewById(R.id.manager_apply_icon);
        applyIcon.setIconByName("verifyIconStroke");
        applyIcon2 = findViewById(R.id.manager_apply_icon2);
        applyIcon2.setIconByName("verifyIconPink");
        applyTip = findViewById(R.id.manager_apply_tip);

        userButton = findViewById(R.id.manager_user_page_button);
        userIcon = findViewById(R.id.manager_user_icon);
        userIcon.setIconByName("personIconStroke");
        userIcon2 = findViewById(R.id.manager_user_icon2);
        userIcon2.setIconByName("personIconPink");
        userTip = findViewById(R.id.manager_user_tip);

        userBackground = findViewById(R.id.user_background);
        userLayout = findViewById(R.id.user_layout);
        nameView = findViewById(R.id.user_name);
        createTimeView = findViewById(R.id.user_create_time);
        enabledView = findViewById(R.id.user_enabled);
        onlineView = findViewById(R.id.user_online);
        toEnabledButton = findViewById(R.id.user_enabled_button);
        onlineButton = findViewById(R.id.user_online_button);

        enabledBackground = findViewById(R.id.enabled_background);
        enabledLayout = findViewById(R.id.enabled_layout);
        enabledButton = findViewById(R.id.enabled_button);
        enabledDayView = findViewById(R.id.enabled_day);
        enabledHourView = findViewById(R.id.enabled_hour);
        enabledDayView.setMinValue(0);
        enabledDayView.setMaxValue(3650);//上限十年
        enabledHourView.setMinValue(0);
        enabledHourView.setMaxValue(24);
    }

    private void changePageTo(int pagePosition) {
        //防止浪费算力或丢失已经绘制的画面
        if (this.pagePosition == pagePosition) return;
        else this.pagePosition = pagePosition;

        if (pagePosition == 0) {
            projectContainer.setVisibility(View.VISIBLE);
            applyContainer.setVisibility(View.INVISIBLE);
            userContainer.setVisibility(View.INVISIBLE);

            projectIcon2.setVisibility(View.VISIBLE);
            projectTip.setTextColor(getColor(R.color.blue_purple));
            applyIcon2.setVisibility(View.INVISIBLE);
            applyTip.setTextColor(Color.BLACK);
            userIcon2.setVisibility(View.INVISIBLE);
            userTip.setTextColor(Color.BLACK);
        } else if (pagePosition == 1) {
            projectContainer.setVisibility(View.INVISIBLE);
            applyContainer.setVisibility(View.VISIBLE);
            userContainer.setVisibility(View.INVISIBLE);

            projectIcon2.setVisibility(View.INVISIBLE);
            projectTip.setTextColor(Color.BLACK);
            applyIcon2.setVisibility(View.VISIBLE);
            applyTip.setTextColor(getColor(R.color.blue_purple));
            userIcon2.setVisibility(View.INVISIBLE);
            userTip.setTextColor(Color.BLACK);
        } else if (pagePosition == 2) {
            projectContainer.setVisibility(View.INVISIBLE);
            applyContainer.setVisibility(View.INVISIBLE);
            userContainer.setVisibility(View.VISIBLE);

            projectIcon2.setVisibility(View.INVISIBLE);
            projectTip.setTextColor(Color.BLACK);
            applyIcon2.setVisibility(View.INVISIBLE);
            applyTip.setTextColor(Color.BLACK);
            userIcon2.setVisibility(View.VISIBLE);
            userTip.setTextColor(getColor(R.color.blue_purple));
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
    public void explainLogs(int STATUS) {

    }
}