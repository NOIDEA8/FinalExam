package com.example.finalexam.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalexam.baseappcompatactivity.BaseActivity;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.UserData;
import com.example.finalexam.presenter.UserPresenter;
import com.example.finalexam.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;

public class RegisterActivity extends BaseActivity implements UserDataShowInterface {
    private static final String TAG = "RegisterActivity";
    private TextInputEditText account;
    private TextInputEditText password;
    private TextInputEditText passwordAgain;
    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        initListener();
    }

    private void initView() {
        account = findViewById(R.id.register_account);
        password = findViewById(R.id.register_password);
        passwordAgain = findViewById(R.id.register_password_again);
        registerButton = findViewById(R.id.register_button);
    }

    private void initListener() {
        registerButton.setOnClickListener(v -> {
            String accountStr = Objects.requireNonNull(account.getText()).toString();
            String passwordStr = Objects.requireNonNull(password.getText()).toString();
            String passwordAgainStr = Objects.requireNonNull(passwordAgain.getText()).toString();
            UserPresenter.getInstance(this).register(this, accountStr, passwordStr, passwordAgainStr);
        });
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

    //非本地调用的方法
    @Override
    public void userLog(int STATUS) {

    }

    @Override
    public void userRegister(int STATUS) {
        if (STATUS==UserPresenter.STATUS_PASSWORDS_INCONSISTENT) {
            Toast.makeText(this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
        } else if (STATUS==UserPresenter.STATUS_NO_INTERNET){
            Toast.makeText(this,"无网络，稍后重试",Toast.LENGTH_SHORT).show();
        } else if (STATUS==UserPresenter.STATUS_ACCOUNT_ALREADY_EXIST) {
            Toast.makeText(this,"账号已存在，换一个试试",Toast.LENGTH_SHORT).show();
        } else if(STATUS==UserPresenter.STATUS_SUCCESS){
            Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
            Log.d(TAG,"in register:finish()活动");
            finish();//这里的finish是为了自动结束活动到登录页面
        }
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


}