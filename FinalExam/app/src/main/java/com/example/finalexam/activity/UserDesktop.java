package com.example.finalexam.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.finalexam.adapter.ProjectAdapter;
import com.example.finalexam.baseappcompatactivity.BaseActivity;
import com.example.finalexam.fragment.PersonFragment;
import com.example.finalexam.fragment.ProjectFragment;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.overrideview.any.AnyView;
import com.example.finalexam.presenter.UserPresenter;
import com.example.finalexam.presenter.WebSocketPresenter;

public class UserDesktop extends BaseActivity implements UserDataShowInterface, View.OnClickListener {

    private FrameLayout projectContainer;
    private FrameLayout personContainer;
    private int pagePosition = 0;

    private AnyView addButton;

    private ConstraintLayout projectPageButton;
    private AnyView projectIcon;
    private AnyView projectIcon2;
    private TextView projectTip;

    private ConstraintLayout personPageButton;
    private AnyView personIcon;
    private AnyView personIcon2;
    private TextView personTip;

    private static ConstraintLayout applyBackground;
    private static ConstraintLayout applyLayout;
    private static TextView applyProject;
    private static TextView yesButton;
    private static TextView noButton;

    private static ConstraintLayout errorBackground;
    private static ConstraintLayout errorLayout;
    private static TextView errorMessageView;
    private TextView errorOKButton;
    private static int errorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_desktop);
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
                if (applyBackground.getVisibility() == View.VISIBLE)
                    applyBackground.setVisibility(View.INVISIBLE);
                else {
                    int id = UserPresenter.getInstance(UserDesktop.this).getUserId();
                    if (id != 0) {
                        WebSocketPresenter.stopHeart();
                        WebSocketPresenter.getInstance(getApplicationContext()).getWebSocketClient(id).close();
                    }
                    finish();
                }
            }
        };
        dispatcher.addCallback(callback);
    }

    @SuppressLint("SetTextI18n")
    public static void callErrorLayout(int id, String name) {
        errorBackground.setVisibility(View.VISIBLE);
        errorMessageView.setText("项目“" + name + "”的报错数量已经超过设定阈值，请注意");
        errorId = id;
    }

    public static void callApplyLayout() {
        applyProject.setText(ProjectAdapter.clickName);
        applyBackground.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.add_button)
            startActivity(new Intent(this, AddProjectActivity.class));
        else if (id == R.id.user_project_page_button)
            changePageTo(0);
        else if (id == R.id.user_person_page_button)
            changePageTo(1);
        else if (id == R.id.project_apply_background)
            applyBackground.setVisibility(View.INVISIBLE);
        else if (id == R.id.project_apply_yes_button) {
            UserPresenter.getInstance(this).applyMonitorPermission(ProjectAdapter.clickId);
            ProjectData add = new ProjectData();
            add.setCreator(UserPresenter.getUserName(this));
            add.setProjectName(ProjectAdapter.clickName);
            PersonFragment.addMyApplication(add);
            applyBackground.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "已发送申请", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.project_apply_no_button)
            applyBackground.setVisibility(View.INVISIBLE);
        else if (id == R.id.error_toast_ok) {
            WebSocketPresenter.getInstance(this).receiveWarning(errorId);
            errorBackground.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("CommitTransaction")
    private void initPage() {
        ProjectFragment projectFragment = new ProjectFragment();
        PersonFragment personFragment = new PersonFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.user_project_container, projectFragment);
        transaction.add(R.id.user_person_container, personFragment);
        transaction.commit();
    }

    private void initListener() {
        addButton.setOnClickListener(this);
        projectPageButton.setOnClickListener(this);
        personPageButton.setOnClickListener(this);
        applyBackground.setOnClickListener(this);
        applyLayout.setOnClickListener(this);
        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);
        errorBackground.setOnClickListener(this);
        errorLayout.setOnClickListener(this);
        errorOKButton.setOnClickListener(this);
    }

    private void initView() {
        projectContainer = findViewById(R.id.user_project_container);
        personContainer = findViewById(R.id.user_person_container);

        addButton = findViewById(R.id.add_button);
        addButton.setIconByName("addButton");

        projectPageButton = findViewById(R.id.user_project_page_button);
        projectIcon = findViewById(R.id.user_project_icon);
        projectIcon.setIconByName("projectIconStroke");
        projectIcon2 = findViewById(R.id.user_project_icon2);
        projectIcon2.setIconByName("projectIconPink");
        projectTip = findViewById(R.id.user_project_tip);

        personPageButton = findViewById(R.id.user_person_page_button);
        personIcon = findViewById(R.id.user_person_icon);
        personIcon.setIconByName("personIconStroke");
        personIcon2 = findViewById(R.id.user_person_icon2);
        personIcon2.setIconByName("personIconPink");
        personTip = findViewById(R.id.user_person_tip);

        applyBackground = findViewById(R.id.project_apply_background);
        applyLayout = findViewById(R.id.project_apply_layout);
        applyProject = findViewById(R.id.project_apply_project);
        yesButton = findViewById(R.id.project_apply_yes_button);
        noButton = findViewById(R.id.project_apply_no_button);

        errorBackground = findViewById(R.id.error_toast_background);
        errorLayout = findViewById(R.id.error_toast_layout);
        errorMessageView = findViewById(R.id.error_toast_message);
        errorOKButton = findViewById(R.id.error_toast_ok);
    }

    private void changePageTo(int pagePosition) {
        //防止浪费算力或丢失已经绘制的画面
        if (this.pagePosition == pagePosition) return;
        else this.pagePosition = pagePosition;

        if (pagePosition == 0) {
            projectContainer.setVisibility(View.VISIBLE);
            personContainer.setVisibility(View.INVISIBLE);

            projectTip.setTextColor(getColor(R.color.blue_purple));
            projectIcon2.setVisibility(View.VISIBLE);
            personTip.setTextColor(Color.BLACK);
            personIcon2.setVisibility(View.INVISIBLE);
        } else {
            projectContainer.setVisibility(View.INVISIBLE);
            personContainer.setVisibility(View.VISIBLE);

            projectTip.setTextColor(Color.BLACK);
            projectIcon2.setVisibility(View.INVISIBLE);
            personTip.setTextColor(getColor(R.color.blue_purple));
            personIcon2.setVisibility(View.VISIBLE);
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
}