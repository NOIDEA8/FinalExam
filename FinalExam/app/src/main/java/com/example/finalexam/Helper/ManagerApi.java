package com.example.finalexam.Helper;

import com.example.finalexam.Model.ManagerData;
import com.example.finalexam.Model.UserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ManagerApi {

    @FormUrlEncoded
    @POST("user/log")//与后台协商后说复用这个接口
    Call<ManagerData> log(@Field("account") String account,@Field("password") String password);

    @POST("manager/updateData")
    Call<ManagerData> updateData(@Header("token") String token,
                              @Body ManagerData body);


}
