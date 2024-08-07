package com.example.finalexam.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalexam.helper.ManagerDataShowInterface;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.presenter.SPPresenter;
import com.example.finalexam.presenter.UserPresenter;
import com.example.finalexam.R;

public class MainActivity extends AppCompatActivity implements UserDataShowInterface, ManagerDataShowInterface {
    private static final String TAG = "MainActivity";
    private UserPresenter userPresenter = UserPresenter.getInstance(this);

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


        new Thread(() -> {
            if (!SPPresenter.isLogged(MainActivity.this)) {//SPPresenter为工具类保存原有打开shareprefrence的读取操作
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        startActivity(new Intent(MainActivity.this, LogActivity.class));
                        finish();
                    }
                }).start();
            } else {
                String account = SPPresenter.getUserName(MainActivity.this);
                String password = SPPresenter.getPassword(MainActivity.this);

                userPresenter.updataData();//预计是有网更新，无网toast

                // userPresenter.userLog(MainActivity.this,account,password);
                if ("admin".equals(account)) {
                    startActivity(new Intent(MainActivity.this, ManagerDesktop.class));
                } else {
                    startActivity(new Intent(MainActivity.this, UserDesktop.class));
                }
                finish();
            }
        }).start();

    }

    @Override
    public void userLog(int STATUS) {

    }

    @Override
    public void userRegister(int STATUS) {

    }

    @Override
    public void updateUserData(int STATUS) {

    }

    @Override
    public void updateUserImage(int STATUS) {

    }

    @Override
    public void managerLog(int STATUS) {

    }

    @Override
    public void updateManagerData(int STATUS) {

    }
}