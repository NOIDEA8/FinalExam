package com.example.finalexam.helper;

import com.example.finalexam.info.InfoProjectList;
import com.example.finalexam.info.InfoShowAllProject;
import com.example.finalexam.info.InfoUser;
import com.example.finalexam.info.InfoUserList;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface adminApi {

    //获取不同冻结状态的项目
    @GET("admin/pagedQueryPublishedProject")//这里网址问题,他们那边有拼写错误
    Call<InfoShowAllProject> getFrezonOrNotProject(@Query("projectStatus") int projectStatus);//0冻结，1未冻结

    //获取不同审核状态的项目
    @GET("admin/pagedQueryProjectApplication")
    Call<InfoShowAllProject> getReviewOrNotProject(@Query("applicationStatus") int applicationStatus);//0待审核，2被拒绝

    //这里InfoUser任意，因为data不返回数据。数据体包括  applicationId、status(1通过2拒绝）（字符串形式）、rejectResason
    @POST("admin/verifyApplication")
    Call<InfoUser> verifyApplication(@Body JSONObject jsonObject);



}
