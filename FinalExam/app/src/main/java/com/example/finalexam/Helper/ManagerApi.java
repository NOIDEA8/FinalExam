package com.example.finalexam.Helper;

import com.example.finalexam.Model.ManagerData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ManagerApi {

    @FormUrlEncoded
    @POST("manager/password")
    Call<ManagerData> log(@Field("password") String password);



}
