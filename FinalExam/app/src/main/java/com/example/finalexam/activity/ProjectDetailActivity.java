package com.example.finalexam.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalexam.R;
import com.example.finalexam.adapter.ProjectAdapter;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.presenter.UserPresenter;

public class ProjectDetailActivity extends AppCompatActivity implements UserDataShowInterface {
    private static final String TAG = "ProjectDetailActivity";

    private TextView projectName;
    private TextView projectId;

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
        showData();
    }

    private void initListener() {

    }

    @SuppressLint("SetTextI18n")
    private void showData() {
        projectId.setText("id: " + ProjectAdapter.clickId);
    }

    private void requestData() {
        UserPresenter.getInstance(this).fetchProjectDetail(ProjectAdapter.clickId);
    }

    private void initView() {
        projectName = findViewById(R.id.detail_project_name);
        projectId =  findViewById(R.id.detail_project_id);
    }

    @Override
    public void applyMonitorPermission(int STATUS) {

    }

    @Override
    public void userLog(int STATUS) {

    }

    @Override
    public void userRegister(int STATUS) {

    }

    @Override
    public void projectPublishResult(int STATUS) {

    }

    @Override
    public void projectListResult(int STATUS) {

    }

    @Override
    public void projectDetail(int STATUS) {
        if (STATUS == UserPresenter.STATUS_NO_INTERNET) {
            //Toast.makeText(this, "无网络，请稍后再试", Toast.LENGTH_SHORT).show();
        } else if (STATUS == UserPresenter.STATUS_SUCCESS) {

        }
    }
}