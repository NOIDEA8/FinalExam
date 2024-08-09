package com.example.finalexam.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalexam.R;
import com.example.finalexam.adapter.ProjectAdapter;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.overrideview.DoubleGraphView;
import com.example.finalexam.presenter.UserPresenter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class ProjectDetailActivity extends AppCompatActivity implements UserDataShowInterface {
    private static final String TAG = "ProjectDetailActivity";

    private ProjectData data = new ProjectData();
    private TextView projectName;
    private TextView projectId;
    private TextView descriptionView;
    private DoubleGraphView graphView;
    private List<Integer> FPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_project_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        initListener();
        requestData();
        initGraph();
    }

    private void initGraph() {
        FPS = graphView.targetFPS;
        addTestFPS();
    }

    private void addTestFPS() {
        Random random = new Random(System.currentTimeMillis());
        int min = 50;
        for (int i = 0; i < 10; i++) {
            FPS.add(min + random.nextInt(51));
        }
    }

    private void initListener() {

    }

    @SuppressLint("SetTextI18n")
    private void showData() {
        projectName.setText(data.getProjectName());
        projectId.setText("id: " + ProjectAdapter.clickId);
        descriptionView.setText(data.getDescription());
    }

    private void requestData() {
        UserPresenter.getInstance(this).fetchProjectDetail(ProjectAdapter.clickId);
    }

    private void initView() {
        projectName = findViewById(R.id.detail_project_name);
        projectId =  findViewById(R.id.detail_project_id);
        descriptionView = findViewById(R.id.detail_project_description);
        graphView = findViewById(R.id.detail_project_graph);
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
        if (STATUS == UserPresenter.STATUS_NO_INTERNET) {
            //Toast.makeText(this, "无网络，请稍后再试", Toast.LENGTH_SHORT).show();
        } else if (STATUS == UserPresenter.STATUS_SUCCESS) {
            data  = UserPresenter.getInstance(this).getProjectDetail();
            showData();
        }
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