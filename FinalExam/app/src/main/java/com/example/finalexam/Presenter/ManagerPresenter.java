package com.example.finalexam.Presenter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.finalexam.Helper.ManagerApi;
import com.example.finalexam.Helper.ManagerDataShowInterface;
import com.example.finalexam.Model.ManagerData;

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
    public static final int STATUS_UPDATE_ERROR = 31;


    public static ManagerPresenter getInstance(ManagerDataShowInterface activity) {
        presenter.activity=activity;
        return presenter;
    }

    public void log(Context context,String account,String password){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ManagerApi managerApi=retrofit.create(ManagerApi.class);

        Log.d(TAG, "baseUrl = " + baseUrl);

        //后台说复用接口，管理员登陆的时候就传一个admin进去
        Call<ManagerData> dataCall = managerApi.log(account,password);

        dataCall.enqueue(new Callback<ManagerData>() {
            @Override
            public void onResponse(Call<ManagerData> call, Response<ManagerData> response) {
                ManagerData tempData=response.body();
                if (tempData == null) {
                    activity.managerLog(STATUS_NO_INTERNET);
                } else if ("passwordIncorrect".equals(tempData.getMsg())){
                    activity.managerLog(STATUS_PASSWORD_INCORRECT);
                } else {
                    SPPresenter.accordAccount(context, account);
                    SPPresenter.accordPassword(context, password);
                    SPPresenter.accordLoggedStatus(context, true);
                    managerData = tempData;
                    Log.d(TAG,"ManagerData已在log方法中赋值");
                    activity.managerLog(STATUS_SUCCESS);
                }
            }
            @Override
            public void onFailure(Call<ManagerData> call, Throwable t) {
                 activity.managerLog(STATUS_NO_INTERNET);
            }
        });
    }

    public void updateManagerData(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ManagerApi managerApi = retrofit.create(ManagerApi.class);

        Log.d(TAG, "update msg = " + managerData.getMsg());
        Call<ManagerData> dataCall = managerApi.updateData(managerData.getMsg(), managerData);

        dataCall.enqueue(new Callback<ManagerData>() {
            @Override
            public void onResponse(@NonNull Call<ManagerData> call, @NonNull Response<ManagerData> response) {
                ManagerData tempData = response.body();
                if (tempData == null) activity.updateManagerData(STATUS_UPDATE_ERROR);
                else if ("updateError".equals(tempData.getMsg()))
                    activity.updateManagerData(STATUS_UPDATE_ERROR);
                else {
                    activity.updateManagerData(STATUS_SUCCESS);
                    Log.d(TAG, "update success");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ManagerData> call, @NonNull Throwable throwable) {
                activity.updateManagerData(STATUS_NO_INTERNET);
            }
        });
    }

}
