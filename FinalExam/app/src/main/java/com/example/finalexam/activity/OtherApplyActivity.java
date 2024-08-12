package com.example.finalexam.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.R;
import com.example.finalexam.adapter.DealAdapter;
import com.example.finalexam.fragment.PersonFragment;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.presenter.UserPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OtherApplyActivity extends AppCompatActivity implements UserDataShowInterface, View.OnClickListener {

    private RecyclerView otherRV;
    private final List<ProjectData> list = new ArrayList<>();

    private static ConstraintLayout dealBackground;
    private static ConstraintLayout dealLayout;
    private static TextView dealName;
    private static TextView dealProject;
    private static TextView yesButton;
    private static TextView noButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_other_apply);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setNavigationBarColor(getColor(R.color.grey));
        overrideBackMethod();
        initView();
        initListener();
        initRV();
    }

    private void overrideBackMethod() {
        //重写回退方法
        OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (dealBackground.getVisibility() == View.VISIBLE)
                    dealBackground.setVisibility(View.INVISIBLE);
                else finish();
            }
        };
        dispatcher.addCallback(callback);
    }

    public static void callDealLayout() {
        dealName.setText(DealAdapter.clickName);
        dealProject.setText(DealAdapter.clickProject);
        dealBackground.setVisibility(View.VISIBLE);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.deal_other_apply_background)
            dealBackground.setVisibility(View.INVISIBLE);
        else if (id == R.id.deal_other_apply_yes_button) {
            list.remove(DealAdapter.tempData);
            Objects.requireNonNull(otherRV.getAdapter()).notifyDataSetChanged();
            UserPresenter.getInstance(this).verifyMonitorApplication(DealAdapter.applicationId, 1, null);
            dealBackground.setVisibility(View.INVISIBLE);
        } else if (id == R.id.deal_other_apply_no_button) {
            list.remove(DealAdapter.tempData);
            Objects.requireNonNull(otherRV.getAdapter()).notifyDataSetChanged();
            UserPresenter.getInstance(this).verifyMonitorApplication(DealAdapter.applicationId, 2, "哒咩");
            dealBackground.setVisibility(View.INVISIBLE);
        }
    }

    private void initRV() {
        otherRV.setLayoutManager(new LinearLayoutManager(this));
        list.clear();
        list.addAll(PersonFragment.otherApplications);
        otherRV.setAdapter(new DealAdapter(this, list, true));
    }

    private void initListener() {
        dealBackground.setOnClickListener(this);
        dealLayout.setOnClickListener(this);
        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);
    }

    private void initView() {
        otherRV = findViewById(R.id.other_rv);
        dealBackground = findViewById(R.id.deal_other_apply_background);
        dealLayout = findViewById(R.id.deal_other_apply_layout);
        dealName = findViewById(R.id.deal_other_apply_name);
        dealProject = findViewById(R.id.deal_other_apply_project);
        yesButton = findViewById(R.id.deal_other_apply_yes_button);
        noButton = findViewById(R.id.deal_other_apply_no_button);
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