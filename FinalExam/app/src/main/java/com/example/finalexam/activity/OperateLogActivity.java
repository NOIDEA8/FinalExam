package com.example.finalexam.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalexam.R;
import com.example.finalexam.adapter.LogAdapter;
import com.example.finalexam.adapter.OperateLogAdapter;
import com.example.finalexam.baseappcompatactivity.BaseActivity;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.AllLog;
import com.example.finalexam.model.LogData;
import com.example.finalexam.presenter.UserPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OperateLogActivity extends BaseActivity implements UserDataShowInterface {
    private RecyclerView LogRV;
    private TextView tips;
    private ImageView switchButton;
    private int page=0;
    private static List<LogData> serverData=new ArrayList<>();
    private static List<LogData> userData=new ArrayList<>();
    private static List<LogData> data=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_operate_log);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        initListener();
        initRV();
        requestData();
    }


    private void initView(){
        LogRV=findViewById(R.id.OperateLog_list);
        tips= findViewById(R.id.OperateLog_text);
        switchButton=findViewById(R.id.switch_log);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initListener(){
        switchButton.setOnClickListener(v->{
            if(page==0){
                page=1;
                if(serverData.isEmpty()){
                    UserPresenter.getInstance(this).fetchAttackServerLog(1,30);
                }
                 tips.setText("服务器攻击日志");
                data.clear();
                data.addAll(serverData);
                LogRV.getAdapter().notifyDataSetChanged();
            } else if (page==1) {
                page=0;
                if(userData.isEmpty()){
                    UserPresenter.getInstance(this).fetchAllUserOperationLog(1,30);
                }
                tips.setText("用户操作日志");
                data.clear();
                data.addAll(userData);
                LogRV.getAdapter().notifyDataSetChanged();

            }
        });
    }

    private void requestData(){
       // UserPresenter.getInstance(this).fetchAttackServerLog(1,30);
        UserPresenter.getInstance(this).fetchAllUserOperationLog(1,30);
    }
    private void initRV() {
        LogRV.setLayoutManager(new LinearLayoutManager(UserPresenter.getContext()));
        LogRV.setAdapter(new OperateLogAdapter(this, data));
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

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void attackServerLogList(int STATUS) {
        if(STATUS==UserPresenter.STATUS_SUCCESS){
            AllLog rawData=UserPresenter.getInstance(this).getLogDataListByGroup();
            serverData =rawData.getData();
            data.clear();
            data.addAll(userData);
            LogRV.getAdapter().notifyDataSetChanged();
        }else if(STATUS==UserPresenter.STATUS_NO_INTERNET){
            Toast.makeText(this,"无网络",Toast.LENGTH_SHORT).show();
        }else if(STATUS==UserPresenter.STATUS_NO_DATA){
            Toast.makeText(this,"无数据",Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void allUserOperationLogList(int STATUS) {
        if(STATUS==UserPresenter.STATUS_SUCCESS){
            AllLog rawData=UserPresenter.getInstance(this).getLogDataListByGroup();
            userData=rawData.getData();
            data.clear();
            data.addAll(userData);
            LogRV.getAdapter().notifyDataSetChanged();
        }else if(STATUS==UserPresenter.STATUS_NO_INTERNET){
            Toast.makeText(this,"无网络",Toast.LENGTH_SHORT).show();
        }else if(STATUS==UserPresenter.STATUS_NO_DATA){
            Toast.makeText(this,"无数据",Toast.LENGTH_SHORT).show();
        }
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