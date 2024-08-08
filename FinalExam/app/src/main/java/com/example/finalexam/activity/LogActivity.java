package com.example.finalexam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalexam.fragment.ManagerLogFragment;
import com.example.finalexam.helper.ManagerDataShowInterface;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.presenter.UserPresenter;
import com.example.finalexam.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class LogActivity extends AppCompatActivity implements UserDataShowInterface, ManagerDataShowInterface {

    private static final String TAG = "LogActivity";
    private TextInputEditText account;
    private TextInputEditText password;
    private ManagerLogFragment managerLogFragment;
    private TextView toRegister;
    private Button logButton;
    private ImageView logo;
    private int clickToManager=0;
    private int totalClick=5;//5次进入管理员口令界面
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
        logo=findViewById(R.id.log_logo);

        managerLogFragment=new ManagerLogFragment(this);
    }

    private void initListener() {
        logButton.setOnClickListener(v -> {
            String accountStr = Objects.requireNonNull(account.getText()).toString();
            String passwordStr = Objects.requireNonNull(password.getText()).toString();
            if (accountStr.isEmpty() || passwordStr.isEmpty()) {
                Toast.makeText(this, "请输入", Toast.LENGTH_SHORT).show();
            }else{
                UserPresenter.getInstance(this).userLog(this,accountStr,passwordStr);//这里进去是从后台拿数据，拿到后调用自己的log方法
            }
        });
        toRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
        logo.setOnClickListener(v->{
            clickToManager++;

            if(clickToManager==totalClick){
                managerLogFragment.show(getSupportFragmentManager(),"managerLog");
                clickToManager=0;
            }
        });
    }


    //用在管理员登陆碎片，当碎片获得所输入的密码后将会来到这里
    public void setManagerPassword(String password){
        //后台说复用接口，管理员登陆的时候就传一个admin进去
        UserPresenter.getInstance(this).userLog(this,"admin",password);
    }


    @Override
    public void applyMonitorPermission(int STATUS) {

    }

    //非本地调用的方法
    @Override
    public void userLog(int STATUS) {
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
            Log.d(TAG,"in userLog:finish()活动");
            finish();//这一套下来要是能到达这一步的话应该Presenter的Userdata应该是有值的
            //这里的finish是为了自动结束活动到主页面
        }
    }

    @Override
    public void userRegister(int STATUS) {

    }

    @Override
    public void userListResult(int STATUS) {

    }

    @Override
    public void projectPublishResult(int STATUS) {

    }

    @Override
    public void projectListResult(int STATUS) {

    }

    @Override
    public void projectDetail(int STATUS) {

    }

    @Override
    public void updata(int STATUS) {

    }

    @Override
    public void managerLog(int STATUS) {

    }

    @Override
    public void updateManagerData(int STATUS) {

    }


}