package com.example.finalexam.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalexam.R;
import com.example.finalexam.adapter.ApplyAdapter;
import com.example.finalexam.adapter.ProjectAdapter;
import com.example.finalexam.fragment.ApplyOverviewFragment;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.model.UserData;
import com.example.finalexam.presenter.UserPresenter;

public class VerifyActivity extends AppCompatActivity implements UserDataShowInterface {
    ProjectData data = ApplyAdapter.clickData;
    private TextView nameView;
    private TextView descriptionView;
    private TextView urlView;
    private TextView yesButton;
    private TextView noButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setNavigationBarColor(getColor(R.color.grey));
        initView();
        initListener();
        showData();
    }

    private void showData() {
        nameView.setText(data.getProjectName());
        descriptionView.setText(data.getDescription());
        urlView.setText(data.getProjectUrl());
    }

    private void initListener() {
        yesButton.setOnClickListener(v -> {
            UserPresenter.getInstance(this).verifyApplication(data.getProjectId(), UserPresenter.PASS_REVIEW, null);
            ApplyOverviewFragment.list.remove(data);
            ApplyOverviewFragment.notifyChange();
            finish();
        });
        noButton.setOnClickListener(v -> {
            UserPresenter.getInstance(this).verifyApplication(data.getProjectId(), UserPresenter.REJECT_REVIEW, "哒咩");
            ApplyOverviewFragment.list.remove(data);
            ApplyOverviewFragment.notifyChange();
            finish();
        });
    }

    private void initView() {
        nameView = findViewById(R.id.verify_name);
        descriptionView = findViewById(R.id.verify_description);
        urlView = findViewById(R.id.verify_url);
        yesButton = findViewById(R.id.verify_yes_button);
        noButton = findViewById(R.id.verify_no_button);
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