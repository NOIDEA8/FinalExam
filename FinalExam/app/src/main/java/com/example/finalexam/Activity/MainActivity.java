package com.example.finalexam.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalexam.Helper.ManagerDataShowInterface;
import com.example.finalexam.Helper.UserDataShowInterface;
import com.example.finalexam.Presenter.ManagerPresenter;
import com.example.finalexam.Presenter.SPPresenter;
import com.example.finalexam.Presenter.UserPresenter;
import com.example.finalexam.R;

public class MainActivity extends AppCompatActivity  implements UserDataShowInterface, ManagerDataShowInterface {
    private static final String TAG = "MainActivity";
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

        UserPresenter userPresenter = UserPresenter.getInstance(this);
        ManagerPresenter managerPresenter = ManagerPresenter.getInstance(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!SPPresenter.isLogged(MainActivity.this)) {//SPPresenter为工具类保存原有打开shareprefrence的取操作
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            } finally {
                                startActivity(new Intent(MainActivity.this, LogActivity.class));
                                finish();
                            }
                        }
                    }).start();
                } else {
                    String account = SPPresenter.getAccount(MainActivity.this);
                    String password = SPPresenter.getPassword(MainActivity.this);

                    if(account.equals("admin")){
                        managerPresenter.log(MainActivity.this,account,password);
                    }else{
                        userPresenter.log(MainActivity.this,account,password);
                    }
                }
            }
        }).start();
    }

    @Override
    public void userLog(int STATUS) {
        if (STATUS == UserPresenter.STATUS_NO_INTERNET) {
            Toast.makeText(this, "网络异常，请检查网络后重试", Toast.LENGTH_SHORT).show();
            SPPresenter.accordLoggedStatus(this, false);
            startActivity(new Intent(MainActivity.this, LogActivity.class));
            finish();
        } else if (STATUS == UserPresenter.STATUS_SUCCESS) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        /*    if (UserPresenter.getInstance(this).isNextDay(this)) {
                UserPresenter.getInstance(this).getWaterToday().clear();
                UserPresenter.getInstance(this).setWaterDrink(0);
            }*/
            UserPresenter.getInstance(this).updateUserData(this);
        }
    }

    @Override
    public void userRegister(int STATUS) {

    }

    @Override
    public void updateUserData(int STATUS) {
        if(STATUS == UserPresenter.STATUS_SUCCESS)
            Toast.makeText(this, "数据同步成功", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "数据同步失败", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, UserDesktop.class));
        finish();
    }

    @Override
    public void updateUserImage(int STATUS) {

    }

    @Override
    public void managerLog(int STATUS) {
        if (STATUS == ManagerPresenter.STATUS_NO_INTERNET) {
            Toast.makeText(this, "网络异常，请检查网络后重试", Toast.LENGTH_SHORT).show();
            SPPresenter.accordLoggedStatus(this, false);
            startActivity(new Intent(MainActivity.this, LogActivity.class));
            finish();
        } else if (STATUS == ManagerPresenter.STATUS_SUCCESS) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        /*    if (UserPresenter.getInstance(this).isNextDay(this)) {
                UserPresenter.getInstance(this).getWaterToday().clear();
                UserPresenter.getInstance(this).setWaterDrink(0);
            }*/
            ManagerPresenter.getInstance(this).updateManagerData(this);
        }
    }

    @Override
    public void updateManagerData(int STATUS) {
        if(STATUS == ManagerPresenter.STATUS_SUCCESS)
            Toast.makeText(this, "数据同步成功", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "数据同步失败", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, ManagerDesktop.class));
        finish();
    }

}