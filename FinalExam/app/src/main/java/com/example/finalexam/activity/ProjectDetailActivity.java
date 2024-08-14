package com.example.finalexam.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.R;
import com.example.finalexam.adapter.LogAdapter;
import com.example.finalexam.adapter.ProjectAdapter;
import com.example.finalexam.baseappcompatactivity.BaseActivity;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.AllLog;
import com.example.finalexam.model.LogData;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.presenter.UserPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectDetailActivity extends BaseActivity implements UserDataShowInterface {
    private ProjectData data = new ProjectData();
    private int requestNum = 0;
    private TextView projectName;
    private TextView projectId;
    private TextView descriptionView;

    private TextView readWeekView;
    private TextView errorWeekView;

    private ConstraintLayout optionsLayout;//测量用
    private TextView frontOption;
    private TextView mobileOption;

    private RecyclerView logRV;
    private TextView logNumView;
    private TextView lastButton;
    private TextView nextButton;

    private static ConstraintLayout logBackground;
    private static ConstraintLayout logLayout;
    private static TextView logId;
    private static TextView logInfo;

    private static final List<LogData> weekList = new ArrayList<>();
    public static final List<Integer> readDay = new ArrayList<>();
    public static final List<Integer> errorDay = new ArrayList<>();
    private static AllLog log;
    private static final List<LogData> logList = new ArrayList<>();
    private int MorF = UserPresenter.FRONT_LOG;
    private int page = 1;
    private boolean canChangePage = true;

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
        initRV();
        //requestData();
    }

    public static void callLogLayout() {
        LogData logData = LogAdapter.data;
    }

    private void initRV() {
        logRV.setLayoutManager(new LinearLayoutManager(this));
        logRV.setAdapter(new LogAdapter(this, logList));
    }

    private void initListener() {
        //选择查看前端后台
        frontOption.setOnClickListener(v -> {
            MorF = UserPresenter.FRONT_LOG;
            page = 1;
            UserPresenter.getInstance(this).fetchLogForGroup(MorF, 30, page, ProjectAdapter.clickId, 2);
        });
        mobileOption.setOnClickListener(v -> {
            MorF = UserPresenter.MOBILE_LOG;
            page = 1;
            UserPresenter.getInstance(this).fetchLogForGroup(MorF, 30, page, ProjectAdapter.clickId, 2);
        });

        //翻页按钮
        lastButton.setOnClickListener(v -> {
            if (!canChangePage) return;
            else if (page == 1) return;
            page--;
            UserPresenter.getInstance(this).fetchLogForGroup(MorF, 30, page, ProjectAdapter.clickId, 2);
            canChangePage = false;
        });
        nextButton.setOnClickListener(v -> {
            if (!canChangePage) return;
            page++;
            UserPresenter.getInstance(this).fetchLogForGroup(MorF, 30, page, ProjectAdapter.clickId, 2);
            canChangePage = false;
        });

        //LogInfo弹窗
        logBackground.setOnClickListener(v -> logBackground.setVisibility(View.INVISIBLE));
        logLayout.setOnClickListener(v -> {
            //你没看错，就是用来占位而已
        });
    }

    @SuppressLint("SetTextI18n")
    private void showData() {
        projectName.setText(data.getProjectName());
        projectId.setText("id: " + ProjectAdapter.clickId);
        descriptionView.setText(data.getDescription());

        int readWeek = 0;
        int errorWeek = 0;
        for (LogData logData : weekList) {
            readWeek += logData.getVisits();
            readDay.add(logData.getVisits());
            errorWeek += logData.getErrorNumber();
            errorDay.add(logData.getErrorNumber());
        }
        readWeekView.setText(readWeek);
        errorWeekView.setText(errorWeek);

        //此处为曲线图

        logNumView.setText(log.getTotal());
    }

    private void requestData() {
        UserPresenter.getInstance(this).fetchProjectDetail(ProjectAdapter.clickId);
        UserPresenter.getInstance(this).fetchProjectPresentationDateOneWeek(ProjectAdapter.clickId);//一周数据
        UserPresenter.getInstance(this).fetchLogForGroup(MorF, 30, page, ProjectAdapter.clickId, 2);//日志
    }

    private void initView() {
        projectName = findViewById(R.id.detail_project_name);
        projectId = findViewById(R.id.detail_project_id);
        descriptionView = findViewById(R.id.detail_project_description);

        readWeekView = findViewById(R.id.detail_read_week);
        errorWeekView = findViewById(R.id.detail_error_week);

        optionsLayout = findViewById(R.id.detail_options_layout);
        frontOption = findViewById(R.id.detail_option_front);
        mobileOption = findViewById(R.id.detail_option_mobile);

        logRV = findViewById(R.id.detail_log);
        logNumView = findViewById(R.id.detail_log_num);
        lastButton = findViewById(R.id.detail_log_last);
        nextButton = findViewById(R.id.detail_log_next);

        logBackground = findViewById(R.id.detail_log_background);
        logLayout = findViewById(R.id.detail_log_layout);
        logId = findViewById(R.id.detail_log_id);
        logInfo = findViewById(R.id.detail_log_info);
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
    public void application(int STATUS) {

    }

    @Override
    public void attackServerLogList(int STATUS) {

    }

    @Override
    public void allUserOperationLogList(int STATUS) {

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void logDataListByGroup(int STATUS) {
        if (STATUS == UserPresenter.STATUS_SUCCESS) {
            log = UserPresenter.getInstance(this).getLogDataListByGroup();
            logList.clear();
            logList.addAll(log.getData());
            Objects.requireNonNull(logRV.getAdapter()).notifyDataSetChanged();
        }
    }

    @Override
    public void projectPresentationDateOneWeek(int STATUS) {
        if (STATUS == UserPresenter.STATUS_SUCCESS) {
            weekList.clear();
            weekList.addAll(UserPresenter.getInstance(this).getLogDataList());
        }

        if (++requestNum == 2) {
            requestNum = 0;
            showData();
        }
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
        if (STATUS == UserPresenter.STATUS_SUCCESS) {
            data = UserPresenter.getInstance(this).getProjectDetail();
        }

        if (++requestNum == 2) {
            requestNum = 0;
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