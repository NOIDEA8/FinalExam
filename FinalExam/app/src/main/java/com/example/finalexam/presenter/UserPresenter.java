package com.example.finalexam.presenter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.finalexam.helper.UserApi;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.info.InfoData;
import com.example.finalexam.model.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserPresenter {
    private static final String TAG = "UserPresenter";
    private String baseUrl="http://47.113.224.195:31110/";
    public UserDataShowInterface activity;
    private static UserPresenter presenter=new UserPresenter();
    private UserData user =new UserData();//一个用户一个presenter

    public static final int STATUS_SUCCESS = 100;
    public static final int STATUS_NO_INTERNET = 0;
    public static final int STATUS_ACCOUNT_NOT_EXIST = 11;
    public static final int STATUS_ACCOUNT_FROZEN= 12;
    public static final int STATUS_ACCOUNT_ALREADY_EXIST = 13;
    public static final int STATUS_PASSWORD_INCORRECT = 21;
    public static final int STATUS_PASSWORDS_INCONSISTENT = 22;
    public static final int STATUS_ACCOUNT_OR_PASSWORD_NOT_SATISFIABLE = 23;
    public static final int STATUS_UPDATE_ERROR = 31;


   //获取唯一present
    public static UserPresenter getInstance(UserDataShowInterface activity) {
        presenter.activity = activity;
        return presenter;
    }

    //登录请求
    public void userLog(Context context, String account, String password){
        Log.d(TAG, "requestLog: 登录请求");
        Log.d(TAG, "account = " + account + ", password = " + password);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = retrofit.create(UserApi.class);
        Log.d(TAG, "baseUrl = " + baseUrl);


        Call<InfoData> dataCall = userApi.log(account,password);


        dataCall.enqueue(new Callback<InfoData>() {
            @Override
            public void onResponse(@NonNull Call<InfoData> call, @NonNull Response<InfoData> response) {
                InfoData info = response.body();
                if(info==null){
                    activity.userLog(STATUS_NO_INTERNET);
                } else if (info.getMsg().equals("账号不存在")) {
                    activity.userLog(STATUS_ACCOUNT_NOT_EXIST);
                } else if (info.getMsg().equals("账号被冻结")) {
                    activity.userLog(STATUS_ACCOUNT_FROZEN);
                } else if (info.getMsg().equals("密码错误")) {
                    activity.userLog(STATUS_PASSWORD_INCORRECT);
                }else {
                    UserData tempData=info.getData();
                    SPPresenter.accordUsername(context, account);
                    SPPresenter.accordPassword(context, password);
                    SPPresenter.accordLoggedStatus(context, true);
                    user.setUsername(account);
                    user.setPassword(password);
                    user.setUserId(tempData.getUserId());
                    Log.d(TAG,"useData已在log方法中赋值");
                    activity.userLog(STATUS_SUCCESS);
                }

            }
            @Override
            public void onFailure(@NonNull Call<InfoData> call, @NonNull Throwable throwable) {
                Log.d(TAG, throwable.toString());
                activity.userLog(STATUS_NO_INTERNET);
            }
        });
    }

    public void register(Context context, String account, String password, String passwordAgain) {
        if (!password.equals(passwordAgain)) {
            activity.userRegister(STATUS_PASSWORDS_INCONSISTENT);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = retrofit.create(UserApi.class);

        UserData transmitData=new UserData();
        transmitData.setUsername(account);
        transmitData.setPassword(password);

        Call<InfoData> dataCall = userApi.register(transmitData);
        transmitData=null;

        dataCall.enqueue(new Callback<InfoData>() {
            @Override
            public void onResponse(@NonNull Call<InfoData> call, @NonNull Response<InfoData> response) {
                InfoData info = response.body();
                if (info == null) activity.userRegister(STATUS_NO_INTERNET);
                else if(info.getData()==null) activity.userRegister(STATUS_ACCOUNT_ALREADY_EXIST);
                else {
                  /*  SPPresenter.accordAccount(context, account);
                    SPPresenter.accordPassword(context, password);
                    SPPresenter.accordLoggedStatus(context, true);
                    user.setUsername(account);
                    user.setPassword(password);*/
                    activity.userRegister(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(@NonNull Call<InfoData> call, @NonNull Throwable throwable) {
                activity.userRegister(STATUS_NO_INTERNET);
            }
        });
    }

    public void updataData(){

    }
}
