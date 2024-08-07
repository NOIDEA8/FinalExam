package com.example.finalexam.Helper;

import android.service.autofill.UserData;

import com.example.finalexam.Model.DataModel;
import com.example.finalexam.Model.InfoData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApi {

    @FormUrlEncoded
    @POST("login")
    Call<InfoData> log(@Field("username") String username,
                       @Field("password") String password);


    @POST("user/register")
    Call<InfoData> register(@Body DataModel dataModel);

}
