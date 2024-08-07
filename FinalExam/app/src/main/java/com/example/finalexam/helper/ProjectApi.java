package com.example.finalexam.helper;

import com.example.finalexam.info.InfoUserList;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.info.InfoUser;
import com.example.finalexam.info.InfoProject;
import com.example.finalexam.info.InfoShowAllProject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProjectApi {

    @GET("project/showAllProjectForUser")
    Call<InfoShowAllProject> getAllProjectForUser(@Query("page") int page,@Query("pagesize") int pageSize) ;//page为零拿所有

    //这里InfoUser任意，因为data不返回数据。数据体包括  项目名、项目描述、发布者id、项目url、项目口令
    @POST("project/publishProject")
    Call<InfoUser> publishProject(@Body JSONObject jsonObject);

    @GET("project/DetaliedInfo")
    Call<InfoProject> getProjectDetail(@Query("projectId") int projectId);

    //这里InfoUser任意，因为data不返回数据。数据体包括  用户id、项目id
    @POST("project/applyMonitorPermission")
    Call<InfoUser> applyMonitorPermission(@Body JSONObject jsonObject);

    //这里InfoUser任意，因为data不返回数据。数据体包括  项目URL、项目id、项目描述
    @POST("project/updateProject")
    Call<InfoUser> updateProject(@Body JSONObject jsonObject);

    @GET("project/queryOwnMonitorUser")
    Call<InfoUserList> queryMonitorUser(@Query("projectId") int projectId);

    //这里InfoUser任意，因为data不返回数据。数据体包括  用户id、项目id
    @POST("project/cancelUserMoitorPermission")
    Call<InfoUser> cancelUserMoitorPermission(@Body JSONObject jsonObject);

    //这里InfoUser任意，因为data不返回数据。数据体包括  项目id、项目口令
    @DELETE("project/deleteProject")
    Call<InfoUser> deleteProject(@Body JSONObject jsonObject);



}
