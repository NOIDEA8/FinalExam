package com.example.finalexam.Helper;

import com.example.finalexam.Model.DataModel;
import com.example.finalexam.Model.InfoUser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApi {

    @POST("user/log")
    Call<InfoUser> log(@Body DataModel dataModel);


    @POST("user/register")
    Call<InfoUser> register(@Body DataModel dataModel);

}
