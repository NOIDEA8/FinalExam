package com.example.finalexam.helper;

import com.example.finalexam.model.UserData;
import com.example.finalexam.info.InfoUser;
import com.example.finalexam.info.InfoProjectList;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    @FormUrlEncoded
    @POST("login")
    Call<InfoUser> log(@Field("username") String username,
                       @Field("password") String password);


    @POST("user/register")
    Call<InfoUser> register(@Body JSONObject jsonObject);

    @FormUrlEncoded
    @GET("user/showSelfProjects")
    Call<InfoProjectList> getSelfProjects(@Query("userId") int useId);

    @FormUrlEncoded
    @GET("user/showHaveMonitorPermissionProjects")
    Call<InfoProjectList> getHaveMonitorProjects(@Query("userId") int useId);

    @FormUrlEncoded
    @GET("user/myApplicationOnMonitorProject")
    Call<InfoProjectList> getOnMonitorProject(@Query("userId") int useId);

    @GET("user/myApplicationProject")
    Call<InfoProjectList> myApplicationProject(@Query("userId") int UserId);
}
