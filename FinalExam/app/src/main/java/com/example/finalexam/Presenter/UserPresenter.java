package com.example.finalexam.Presenter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.finalexam.Helper.UserApi;
import com.example.finalexam.Helper.UserDataShowInterface;
import com.example.finalexam.Model.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserPresenter {
    private static final String TAG = "UserPresenter";
    private String baseUrl="http://47.113.224.195:31112/";
    public UserDataShowInterface activity;
    private static UserPresenter presenter=new UserPresenter();
    private UserData userData;
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
    public void log(String account,String password){
        Log.d(TAG, "requestLog: 登录请求");
        Log.d(TAG, "account = " + account + ", password = " + password);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = retrofit.create(UserApi.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<UserData> dataCall = userApi.log(account, password);

        dataCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(@NonNull Call<UserData> call, @NonNull Response<UserData> response) {
                UserData tempData = response.body();
                if (tempData == null) {
                    activity.userLog(STATUS_ACCOUNT_NOT_EXIST);
                } else if ("accountNotExist".equals(tempData.getMsg())){
                    activity.userLog(STATUS_ACCOUNT_NOT_EXIST);
                } else if ("passwordIncorrect".equals(tempData.getMsg())){
                    activity.userLog(STATUS_PASSWORD_INCORRECT);
                } else if ("accountFrozen".equals(tempData.getMsg())) {
                    activity.userLog(STATUS_ACCOUNT_FROZEN);
                } else {
                    userData = tempData;
                    Log.d(TAG,"useData已在log方法中赋值");
                    activity.userLog(STATUS_SUCCESS);
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserData> call, @NonNull Throwable throwable) {
                Log.d(TAG, throwable.toString());
                activity.userLog(STATUS_NO_INTERNET);
            }
        });
    }

    public void register(Context context, String account, String password, String passwordAgain) {
        if (account.length() < 8 || password.length() < 8 || passwordAgain.length() < 8) {
            activity.userRegister(STATUS_ACCOUNT_OR_PASSWORD_NOT_SATISFIABLE);
            return;
        } else if (!password.equals(passwordAgain)) {
            activity.userRegister(STATUS_PASSWORDS_INCONSISTENT);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = retrofit.create(UserApi.class);

        Call<UserData> dataCall = userApi.register(account, password);

        dataCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(@NonNull Call<UserData> call, @NonNull Response<UserData> response) {
                UserData tempData = response.body();
                if (tempData == null) activity.userRegister(STATUS_NO_INTERNET);
                else if ("accountAlreadyExist".equals(tempData.getMsg()))
                    activity.userRegister(STATUS_ACCOUNT_ALREADY_EXIST);
                else {
                    userData = tempData;

                    userData.setAccount(account);//？
                    userData.setPasswords(password);//？？
                    Log.d(TAG,"useData已在request方法中赋值，虽然我觉得没必要，但是先保留");
                    activity.userRegister(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserData> call, @NonNull Throwable throwable) {
                activity.userRegister(STATUS_NO_INTERNET);
            }
        });
    }






    public String getUserName(){
        String name=null;
        if(userData==null){
            Log.d(TAG,"Presenter返回的userData为null，此处新建一个空的返回");
            name="";
        }else{
            name=userData.getName();
            Log.d(TAG,"返回的是userData中的数据");
        }
        return name;
    }
}
