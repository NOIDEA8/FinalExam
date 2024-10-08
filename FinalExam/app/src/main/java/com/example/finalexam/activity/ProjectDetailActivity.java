package com.example.finalexam.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.R;
import com.example.finalexam.adapter.LogAdapter;
import com.example.finalexam.adapter.MonitorUserAdapter;
import com.example.finalexam.adapter.ProjectAdapter;
import com.example.finalexam.baseappcompatactivity.BaseActivity;
import com.example.finalexam.fragment.MonitorUserFragment;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.AllLog;
import com.example.finalexam.model.LogData;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.overrideview.DoubleGraphView;
import com.example.finalexam.overrideview.any.AnyView;
import com.example.finalexam.presenter.UserPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ProjectDetailActivity extends BaseActivity implements UserDataShowInterface {
    private static final String TAG = "ProjectDetailActivity";
    private ProjectData data = new ProjectData();
    private int requestNum = 0;
    private TextView projectName;
    private TextView projectUrl;
    private TextView projectId;

    private AnyView freezeButton;
    private AnyView editButton;

    private TextView descriptionView;

    private TextView readWeekView;
    private TextView errorWeekView;

    private ConstraintLayout optionsLayout;//测量用
    private TextView frontOption;
    private TextView mobileOption;
    private TextView serverOption;

    private RecyclerView logRV;
    private TextView logNumView;
    private TextView lastButton;
    private TextView nextButton;

    private static ConstraintLayout logBackground;
    private static ConstraintLayout logLayout;
    private static TextView logId;
    private static TextView logInfo;

    private ConstraintLayout editBackground;
    private ConstraintLayout editLayout;
    private EditText editNameView;
    private EditText editDescriptionView;
    private EditText editErrorView;
    private EditText editUrlView;
    private EditText editPasswordView;
    private TextView editSaveButton;
    private TextView AIExplain;

    private ConstraintLayout freezeBackground;
    private ConstraintLayout freezeLayout;
    private NumberPicker freezeDayView;
    private NumberPicker freezeHourView;
    private TextView freezeSaveButton;

    private static final List<LogData> weekList = new ArrayList<>();
    public static final List<Integer> readDay = new ArrayList<>();
    public static final List<Integer> errorDay = new ArrayList<>();
    private static AllLog log;
    private static final List<LogData> logList = new ArrayList<>();
    private int MorF = UserPresenter.FRONT_LOG;
    private int page = 1;
    private int pages = 1;
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
        getWindow().setNavigationBarColor(getColor(R.color.grey));
        initView();
        initListener();
        initRV();
        initMonitorFragment();
        requestData();
    }

    @SuppressLint("CommitTransaction")
    private void initMonitorFragment() {
        MonitorUserFragment fragment = new MonitorUserFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.detail_project_monitor_container, fragment);
        transaction.commit();
    }

    public static void callLogLayout() {
        LogData logData = LogAdapter.data;
        logBackground.setVisibility(View.VISIBLE);
        String info=logData.getLogInfo();
        int id=logData.getLogId();

        logInfo.setText(info);
        logId.setText(String.valueOf(id));

    }

    private void initRV() {
        logRV.setLayoutManager(new LinearLayoutManager(this));
        logRV.setAdapter(new LogAdapter(this, logList));
    }

    private void initListener() {
        //选择查看前端后台
        frontOption.setOnClickListener(v -> {
            if(MorF == UserPresenter.MOBILE_LOG) mobileOption.setTextColor(getColor(R.color.text_grey));
            else if (MorF == UserPresenter.SERVER_LOG) serverOption.setTextColor(getColor(R.color.text_grey));
            if (MorF == UserPresenter.FRONT_LOG) return;
            MorF = UserPresenter.FRONT_LOG;
            page = 1;
            UserPresenter.getInstance(this).fetchLogForGroup(MorF, 30, page, ProjectAdapter.clickId, 2);
            UserPresenter.getInstance(this).fetchExplainLogs(MorF,ProjectAdapter.clickId);
            frontOption.setTextColor(getColor(R.color.black));
            /*int fW = frontOption.getWidth();
            int mW = mobileOption.getWidth();
            frontOption.setWidth(mW);
            mobileOption.setWidth(fW);
            frontOption.setTextColor(getColor(R.color.black));
            mobileOption.setTextColor(getColor(R.color.text_grey));*/
        });
        mobileOption.setOnClickListener(v -> {
            if(MorF == UserPresenter.FRONT_LOG) frontOption.setTextColor(getColor(R.color.text_grey));
            else if (MorF == UserPresenter.SERVER_LOG) serverOption.setTextColor(getColor(R.color.text_grey));
            if (MorF == UserPresenter.MOBILE_LOG) return;
            MorF = UserPresenter.MOBILE_LOG;
            page = 1;
            UserPresenter.getInstance(this).fetchLogForGroup(MorF, 30, page, ProjectAdapter.clickId, 2);
            UserPresenter.getInstance(this).fetchExplainLogs(MorF,ProjectAdapter.clickId);
            mobileOption.setTextColor(getColor(R.color.black));
           /* int fW = frontOption.getWidth();
            int mW = mobileOption.getWidth();
            frontOption.setWidth(mW);
            mobileOption.setWidth(fW);
            frontOption.setTextColor(getColor(R.color.text_grey));
            mobileOption.setTextColor(getColor(R.color.black));*/
        });
        serverOption.setOnClickListener(v -> {
            if(MorF == UserPresenter.MOBILE_LOG) mobileOption.setTextColor(getColor(R.color.text_grey));
            else if (MorF == UserPresenter.FRONT_LOG) frontOption.setTextColor(getColor(R.color.text_grey));
            if (MorF == UserPresenter.SERVER_LOG) return;
            MorF = UserPresenter.SERVER_LOG;
            page = 1;
            UserPresenter.getInstance(this).fetchLogForGroup(MorF, 30, page, ProjectAdapter.clickId, 2);
            UserPresenter.getInstance(this).fetchExplainLogs(MorF,ProjectAdapter.clickId);
            serverOption.setTextColor(getColor(R.color.black));
           /* int fW = frontOption.getWidth();
            int mW = mobileOption.getWidth();
            frontOption.setWidth(mW);
            mobileOption.setWidth(fW);
            frontOption.setTextColor(getColor(R.color.text_grey));
            mobileOption.setTextColor(getColor(R.color.black));*/
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
            else if (page == pages) return;
            page++;
            UserPresenter.getInstance(this).fetchLogForGroup(MorF, 30, page, ProjectAdapter.clickId, 2);
            canChangePage = false;
        });

        //LogInfo弹窗
        logBackground.setOnClickListener(v -> logBackground.setVisibility(View.INVISIBLE));
        logLayout.setOnClickListener(v -> {
            //你没看错，就是用来占位而已
        });

        //编辑弹窗
        editButton.setOnClickListener(v -> {
            editNameView.setText(data.getProjectName());
            editDescriptionView.setText(data.getDescription());
            editUrlView.setText(data.getProjectUrl());
            editBackground.setVisibility(View.VISIBLE);
        });
        editBackground.setOnClickListener(v -> editBackground.setVisibility(View.INVISIBLE));
        editSaveButton.setOnClickListener(v -> {
            String password = editPasswordView.getText().toString();
            Log.d("Password", "input = " + password + ", truePwd = " + data.getProjectPassword());

            String description = editDescriptionView.getText().toString();
            String url = editUrlView.getText().toString();
            String errorString = editErrorView.getText().toString();
            double errorValue = 0;
            boolean canBeValueOf = true;
            try {
                errorValue = Double.parseDouble(errorString);
            } catch (Throwable e) {
                canBeValueOf = false;
            }
            if (!canBeValueOf) {
                Toast.makeText(this, "请正确输入阈值", Toast.LENGTH_SHORT).show();
                return;
            }
            if (errorValue > 100) errorValue = 1;
            else if (errorValue < 0) errorValue = 0;
            else errorValue /= 100;

            UserPresenter.getInstance(this).updateProject(url, data.getProjectId(), description, password);
            UserPresenter.getInstance(this).setErrorRate(data.getProjectId(), errorValue);

            editBackground.setVisibility(View.INVISIBLE);
        });
        editLayout.setOnClickListener(v -> {
            //占位
        });

        //冻结弹窗
        freezeButton.setOnClickListener(v -> freezeBackground.setVisibility(View.VISIBLE));
        freezeBackground.setOnClickListener(v -> freezeBackground.setVisibility(View.INVISIBLE));
        freezeSaveButton.setOnClickListener(v -> {
            int day = freezeDayView.getValue();
            int hour = freezeHourView.getValue();
            UserPresenter.getInstance(this).freezeProject(data.getProjectId(), day * 24 + hour);
            freezeBackground.setVisibility(View.INVISIBLE);
        });
        freezeLayout.setOnClickListener(v -> {
            //占位
        });
    }

    @SuppressLint("SetTextI18n")
    private void showData() {
        projectName.setText(data.getProjectName());
        projectUrl.setText("url: " + data.getProjectUrl());
        projectId.setText("id: " + ProjectAdapter.clickId);
        descriptionView.setText(data.getDescription());

        int readWeek = 0;
        int errorWeek = 0;
        readDay.clear();
        errorDay.clear();
        for (LogData logData : weekList) {
            readWeek += logData.getVisits();
            readDay.add(logData.getVisits());
            //errorWeek += logData.getErrorNumber();
            errorDay.add(logData.getErrorNumber());
        }
        errorWeek=weekList.get(0).getErrorNumber();
        /*Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 7; i++)
            readDay.add(random.nextInt(30));
        for (int i = 0; i < 7; i++)
            errorDay.add(random.nextInt(30));*/

        readWeekView.setText(String.valueOf(readWeek));
        errorWeekView.setText(String.valueOf(errorWeek));

        //此处为曲线图
        DoubleGraphView.animator.start();
        Log.d("DoubleGraph", "showData: animator start");
    }

    private void requestData() {
        Log.d(TAG, "requestData");
        UserPresenter.getInstance(this).increaseVisits(ProjectAdapter.clickId);
        UserPresenter.getInstance(this).fetchProjectDetail(ProjectAdapter.clickId);
        UserPresenter.getInstance(this).fetchProjectPresentationDateOneWeek(ProjectAdapter.clickId);//一周数据
        UserPresenter.getInstance(this).fetchLogForGroup(MorF, 30, page, ProjectAdapter.clickId, 2);//日志
        UserPresenter.getInstance(this).fetchExplainLogs(MorF,ProjectAdapter.clickId);//辅助分析
    }

    private void initView() {
        projectName = findViewById(R.id.detail_project_name);
        projectUrl = findViewById(R.id.detail_project_url);
        projectId = findViewById(R.id.detail_project_id);

        freezeButton = findViewById(R.id.detail_project_freeze_button);
        freezeButton.setIconByName("freezeIcon");

        editButton = findViewById(R.id.detail_project_edit_button);
        editButton.setIconByName("editIcon");

        if (!ProjectAdapter.canBeEdited) {
            freezeButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.INVISIBLE);
        }

        descriptionView = findViewById(R.id.detail_project_description);
        AIExplain=findViewById(R.id.AI_explain);

        readWeekView = findViewById(R.id.detail_read_week);
        errorWeekView = findViewById(R.id.detail_error_week);

        optionsLayout = findViewById(R.id.detail_options_layout);
        frontOption = findViewById(R.id.detail_option_front);
        mobileOption = findViewById(R.id.detail_option_mobile);
        serverOption=findViewById(R.id.detail_option_server);

        logRV = findViewById(R.id.detail_log);
        logNumView = findViewById(R.id.detail_log_num);
        lastButton = findViewById(R.id.detail_log_last);
        nextButton = findViewById(R.id.detail_log_next);

        logBackground = findViewById(R.id.detail_log_background);
        logLayout = findViewById(R.id.detail_log_layout);
        logId = findViewById(R.id.detail_log_id);
        logInfo = findViewById(R.id.detail_log_info);

        editBackground = findViewById(R.id.detail_edit_background);
        editLayout = findViewById(R.id.detail_edit_layout);
        editNameView = findViewById(R.id.edit_name);
        editDescriptionView = findViewById(R.id.edit_description);
        editUrlView = findViewById(R.id.edit_url);
        editErrorView = findViewById(R.id.edit_error);
        editPasswordView = findViewById(R.id.edit_password);
        editSaveButton = findViewById(R.id.edit_save_button);

        freezeBackground = findViewById(R.id.freeze_background);
        freezeLayout = findViewById(R.id.freeze_layout);
        freezeDayView = findViewById(R.id.freeze_day);
        freezeHourView = findViewById(R.id.freeze_hour);
        freezeSaveButton = findViewById(R.id.freeze_button);
        freezeDayView.setMinValue(0);
        freezeDayView.setMaxValue(3650);
        freezeHourView.setMinValue(0);
        freezeHourView.setMaxValue(24);
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

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    public void logDataListByGroup(int STATUS) {
        if (STATUS == UserPresenter.STATUS_SUCCESS) {
            log = UserPresenter.getInstance(this).getLogDataListByGroup();
            logList.clear();
            logList.addAll(log.getData());
            Objects.requireNonNull(logRV.getAdapter()).notifyDataSetChanged();

            int total = log.getTotal();
            pages = total % 30 == 0 ? total / 30 : total / 30 + 1;
            logNumView.setText(page + " / " + pages);
        }else {
            logList.clear();
            Objects.requireNonNull(logRV.getAdapter()).notifyDataSetChanged();
        }

        if (++requestNum == 3) {
            requestNum = 0;
            showData();
        }
    }

    @Override
    public void projectPresentationDateOneWeek(int STATUS) {
        if (STATUS == UserPresenter.STATUS_SUCCESS) {
            weekList.clear();
            weekList.addAll(UserPresenter.getInstance(this).getLogDataList());
        }

        if (++requestNum == 3) {
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
    public void explainLogs(int STATUS) {
        if (STATUS==UserPresenter.STATUS_SUCCESS){
            AIExplain.setText(UserPresenter.getInstance(this).getExplainLogs());
        }else{
            Toast.makeText(this,"网络不佳，AI分析失败",Toast.LENGTH_SHORT).show();
        }
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

        if (++requestNum == 3) {
            requestNum = 0;
            showData();
        }
    }

    @Override
    public void updateProject(int STATUS) {
        if (STATUS == UserPresenter.STATUS_NO_INTERNET) {
            Toast.makeText(this, "无网络", Toast.LENGTH_SHORT).show();
        }  else if (STATUS == UserPresenter.STATUS_PROJECT_PASSWORD_INCORRECT) {
            Toast.makeText(this, "口令错误", Toast.LENGTH_SHORT).show();
        } else if (STATUS == UserPresenter.STATUS_SUCCESS||STATUS == UserPresenter.STATUS_APPLICATION_EXITED) {
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
            requestData();
        }
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