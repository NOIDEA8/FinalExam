package com.example.finalexam.Presenter;

import android.app.Activity;
import android.util.Log;

import com.example.finalexam.Activity.LogActivity;
import com.example.finalexam.Helper.ManagerApi;
import com.example.finalexam.Helper.ManagerDataShowInterface;
import com.example.finalexam.Helper.UserDataShowInterface;
import com.example.finalexam.Model.ManagerData;
import com.example.finalexam.Model.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManagerPresenter {
    private static final String TAG = "ManagerPresenter";
    private String baseUrl="http://47.113.224.195:31112/";
    private ManagerDataShowInterface activity;
    private ManagerData managerData;
    private static ManagerPresenter presenter=new ManagerPresenter();

    public static final int STATUS_SUCCESS = 100;
    public static final int STATUS_NO_INTERNET = 0;
    public static final int STATUS_PASSWORD_INCORRECT = 1;


    public static ManagerPresenter getInstance(ManagerDataShowInterface activity) {
        presenter.activity=activity;
        return presenter;
    }

    public void log(String password){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ManagerApi managerApi=retrofit.create(ManagerApi.class);

        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<ManagerData> dataCall = managerApi.log(password);

        dataCall.enqueue(new Callback<ManagerData>() {
            @Override
            public void onResponse(Call<ManagerData> call, Response<ManagerData> response) {
                ManagerData tempData=response.body();
                if (tempData == null) {
                    activity.managerLog(STATUS_NO_INTERNET);
                } else if ("passwordIncorrect".equals(tempData.getMsg())){
                    activity.managerLog(STATUS_PASSWORD_INCORRECT);
                } else {
                    activity.managerLog(STATUS_SUCCESS);
                }
            }
            @Override
            public void onFailure(Call<ManagerData> call, Throwable t) {
                 activity.managerLog(STATUS_NO_INTERNET);
            }
        });
    }



}
