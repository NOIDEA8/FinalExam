package com.example.finalexam.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalexam.baseappcompatactivity.BaseActivity;
import com.example.finalexam.helper.ManagerDataShowInterface;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.model.UserData;
import com.example.finalexam.presenter.UserPresenter;
import com.example.finalexam.R;
import com.example.finalexam.presenter.WebSocketPresenter;

import org.java_websocket.client.WebSocketClient;

import java.util.List;

public class MainActivity extends BaseActivity implements UserDataShowInterface, ManagerDataShowInterface {
    private static final String TAG = "MainActivity";
    private UserPresenter userPresenter = UserPresenter.getInstance(this);
    private String userName;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        UserPresenter.setContext(getApplicationContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (userPresenter.isLogged(MainActivity.this)==false) {//SPPresenter为工具类保存原有打开shareprefrence的读取操作
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            } finally {
                                //startActivity(new Intent(MainActivity.this, UserDesktop.class));
                                startActivity(new Intent(MainActivity.this, UserDesktop.class));
                                finish();
                            }
                        }
                    }).start();
                } else {
                    userName = userPresenter.getUserName(MainActivity.this);
                    password = userPresenter.getPassword(MainActivity.this);

                    userPresenter.userLog(MainActivity.this,userName,password);
                }
            }
        }).start();
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
        if(STATUS==UserPresenter.STATUS_NO_INTERNET){
            Toast.makeText(this,"无网络，稍后重试",Toast.LENGTH_SHORT).show();
            if (userName.equals("admin")) {
                startActivity(new Intent(MainActivity.this,ManagerDesktop.class));
            }else{
                startActivity(new Intent(MainActivity.this,UserDesktop.class));
            }
            finish();
        } else if (STATUS==UserPresenter.STATUS_ACCOUNT_FROZEN) {
            Toast.makeText(this,"账号被冻结，请联系管理员",Toast.LENGTH_SHORT).show();
        } else if (STATUS==UserPresenter.STATUS_SUCCESS) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

           WebSocketClient client= WebSocketPresenter.getInstance(getApplicationContext())
                    .getWebSocketClient(UserPresenter.getInstance(this).getUserId());
           if(client!=null&&client.isClosed()){
               client.connect();
               Log.d("MainActivity","UserLog中websocket连接");
           }
            if (userName.equals("admin")) {
                startActivity(new Intent(MainActivity.this,ManagerDesktop.class));
            }else{
                startActivity(new Intent(MainActivity.this,UserDesktop.class));
            }
            finish();
        }
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
    public void managerLog(int STATUS) {

    }

    @Override
    public void updateManagerData(int STATUS) {

    }
}