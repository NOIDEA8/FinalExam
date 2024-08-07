package com.example.finalexam.helper;

import com.example.finalexam.model.ProjectData;
import com.example.finalexam.info.InfoData;
import com.example.finalexam.info.InfoProject;
import com.example.finalexam.info.InfoShowAllProject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProjectApi {

    @GET("project/showAllProjectForUser")
    Call<InfoShowAllProject> getAllProject() ;

    //这里InfoData任意，因为data不返回数据。数据体包括  项目名、项目描述、发布者id、项目url、项目口令
    @POST("project/publishProject")
    Call<InfoData> publishProject(@Body ProjectData projectData);

    @GET("project/DetaliedInfo")
    Call<InfoProject> getProjectDetail(@Query("projectId") int projectId);

    //这里InfoData任意，因为data不返回数据。数据体包括  用户id、项目id
    @POST("project/applyMonitorPermission")
    Call<InfoData> applyMonitorPermission(@Body ProjectData projectData);

}
