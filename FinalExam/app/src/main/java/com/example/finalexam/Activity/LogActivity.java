package com.example.finalexam.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalexam.Helper.UserDataShowInterface;
import com.example.finalexam.Presenter.UserPresenter;
import com.example.finalexam.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LogActivity extends AppCompatActivity implements UserDataShowInterface {

    private static final String TAG = "LogActivity";
    private TextInputEditText account;
    private TextInputEditText password;
    private TextView toRegister;
    private Button logButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        initListener();
    }

    private void initView() {
        account = findViewById(R.id.log_account);
        password = findViewById(R.id.log_password);
        logButton = findViewById(R.id.log_button);
        toRegister = findViewById(R.id.toRegister);
    }

    private void initListener() {
        logButton.setOnClickListener(v -> {
            String accountStr = Objects.requireNonNull(account.getText()).toString();
            String passwordStr = Objects.requireNonNull(password.getText()).toString();
            if (accountStr.isEmpty() || passwordStr.isEmpty()) {
                Toast.makeText(this, "请输入", Toast.LENGTH_SHORT).show();
            }else{
                UserPresenter.getInstance(this).log(accountStr,passwordStr);//这里进去是从后台拿数据，拿到后调用自己的log方法
            }
        });
        toRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }















//非本地调用的方法
    @Override
    public void log(int STATUS) {
        if(STATUS==UserPresenter.STATUS_NO_INTERNET){
            Toast.makeText(this,"无网络，稍后重试",Toast.LENGTH_SHORT).show();
        } else if (STATUS==UserPresenter.STATUS_ACCOUNT_NOT_EXIST) {
            Toast.makeText(this,"账号不存在，请检查所输入的账号",Toast.LENGTH_SHORT).show();
        } else if (STATUS==UserPresenter.STATUS_ACCOUNT_FROZEN) {
            Toast.makeText(this,"账号被冻结，请联系管理员",Toast.LENGTH_SHORT).show();
        } else if (STATUS==UserPresenter.STATUS_PASSWORD_INCORRECT) {
            Toast.makeText(this,"密码错误，请检查后再登录",Toast.LENGTH_SHORT).show();
        } else if (STATUS==UserPresenter.STATUS_SUCCESS) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,UserDesktop.class));
        }
    }

    @Override
    public void register(int STATUS) {

    }

    @Override
    public void updateUserData(int STATUS) {

    }

    @Override
    public void updateUserImage(int STATUS) {

    }
}