package com.example.finalexam.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalexam.R;
import com.example.finalexam.baseappcompatactivity.BaseActivity;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.UserData;
import com.example.finalexam.presenter.UserPresenter;

import java.util.List;

public class AddProjectActivity extends BaseActivity implements UserDataShowInterface {
    private boolean pushOver = true;
    private EditText nameView;
    private EditText descriptionView;
    private EditText urlView;
    private EditText passwordView;
    private EditText passwordAgainView;
    private TextView pushButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_project);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setNavigationBarColor(getColor(R.color.grey));

        overrideBackMethod();
        initView();
        initListener();
    }

    private void overrideBackMethod() {
        //重写回退方法
        OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (pushOver) finish();
                else Toast.makeText(AddProjectActivity.this, "上传中，请稍等", Toast.LENGTH_SHORT).show();
            }
        };
        dispatcher.addCallback(callback);
    }

    private boolean isSatisfiable(String name, String description, String url, String password, String passwordAgain) {
        if (name == null || description == null || url == null || password == null || passwordAgain == null) {
            Toast.makeText(this, "请正确填写", Toast.LENGTH_SHORT).show();
            return false;
        } else if (name.isEmpty() || description.isEmpty() || url.isEmpty() || password.isEmpty() || passwordAgain.isEmpty()) {
            Toast.makeText(this, "请正确填写", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(passwordAgain)) {
            Toast.makeText(this, "请确保两次输入密码一致", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initListener() {
        pushButton.setOnClickListener(v -> {
            String name = nameView.getText().toString();
            String description = descriptionView.getText().toString();
            String url = urlView.getText().toString();
            String password = passwordView.getText().toString();
            String passwordAgain = passwordAgainView.getText().toString();
            if (!isSatisfiable(name, description, url, password, passwordAgain))
                return;
            UserPresenter.getInstance(this).pushProject(
                    name,
                    description,
                    url,
                    password
            );
            pushOver = false;
        });
    }

    private void initView() {
        nameView = findViewById(R.id.add_name);
        descriptionView = findViewById(R.id.add_description);
        urlView = findViewById(R.id.add_url);
        passwordView = findViewById(R.id.add_password);
        passwordAgainView = findViewById(R.id.add_password_again);
        pushButton = findViewById(R.id.add_push_button);
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
        pushOver = true;
        if (STATUS == UserPresenter.STATUS_NO_INTERNET) {
            Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
        } else if (STATUS == UserPresenter.STATUS_FAILED) {
            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
        } else if (STATUS == UserPresenter.STATUS_SUCCESS) {
            Toast.makeText(this, "发布成功，待审核", Toast.LENGTH_SHORT).show();
        }
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

}