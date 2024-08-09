package com.example.finalexam.helper;

import com.example.finalexam.info.InfoApplyingProjectList;
import com.example.finalexam.info.InfoMonitorProjectList;
import com.example.finalexam.info.InfoMonitorUserList;
import com.example.finalexam.info.InfoProjectDetail;
import com.example.finalexam.info.InfoPublishApplication;
import com.example.finalexam.info.InfoSelfProjectList;
import com.example.finalexam.info.InfoBriefProjectList;
import com.example.finalexam.info.InfoString;
import com.example.finalexam.info.InfoUserDetail;
import com.example.finalexam.info.InfoMonitorApplication;
import com.example.finalexam.model.sendmodel.FreezeProjectSend;
import com.example.finalexam.model.sendmodel.FreezeUserSend;
import com.example.finalexam.model.sendmodel.MonitorSend;
import com.example.finalexam.model.sendmodel.PublishSend;
import com.example.finalexam.model.sendmodel.RegisterSend;
import com.example.finalexam.model.sendmodel.UpdataProjectSend;
import com.example.finalexam.model.sendmodel.VerifyApplicationSend;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<InfoUserDetail> log(@Field("username") String username,
                             @Field("password") String password);

    @POST("user/register")
    Call<InfoUserDetail> register(@Body RegisterSend user);

    @FormUrlEncoded
    @GET("user/showSelfProjects")
    Call<InfoSelfProjectList> getSelfProjects(@Query("userId") int useId);

    @FormUrlEncoded
    @GET("user/showHaveMonitorPermissionProjects")
    Call<InfoMonitorProjectList> getHaveMonitorProjects(@Query("userId") int useId);

    //获取我申请过监控的项目
    @FormUrlEncoded
    @GET("user/myApplicationOnMonitorProject")
    Call<InfoMonitorApplication> getApplyingMonitorProject(@Query("userId") int useId);

    //获取我发布或更改的项目
    @GET("user/myApplicationProject")
    Call<InfoPublishApplication> getMyApplicationProject(@Query("userId") int useId);

    @GET("project/showAllProjectForUser")
    Call<InfoBriefProjectList> getAllProjectForUser(@Query("page") int page, @Query("pagesize") int pageSize, @Query("projectName")String projectName) ;//page为零拿所有

    //这里InfoUser任意，因为data不返回数据。数据体包括  项目名、项目描述、发布者id、项目url、项目口令
    @POST("project/publishProject")
    Call<InfoUserDetail> publishProject(@Body PublishSend jsonObject);

    @GET("project/detailedInfo")
    Call<InfoProjectDetail> getProjectDetail(@Query("projectId") int projectId);

    //这里InfoUser任意，因为data不返回数据。数据体包括  用户id、项目id
    @POST("project/applyMonitorPermission")
    Call<InfoUserDetail> applyMonitorPermission(@Body MonitorSend jsonObject);

    //这里InfoUser任意，因为data不返回数据。数据体包括  项目URL、项目id、项目描述、项目口令,用户id
    @POST("project/updateProject")
    Call<InfoUserDetail> updateProject(@Body UpdataProjectSend jsonObject);

    @GET("project/queryOwnMonitorUser")
    Call<InfoMonitorUserList> queryMonitorUser(@Query("projectId") int projectId);

    //这里InfoUser任意，因为data不返回数据。数据体包括  用户id、项目id
    @POST("project/cancelUserMonitorPermission")
    Call<InfoUserDetail> cancelUserMoitorPermission(@Body MonitorSend jsonObject);

    //这里InfoUser任意，因为data不返回数据。数据体包括  项目id、项目口令
    @DELETE("project/deleteProject")
    Call<InfoUserDetail> deleteProject(@Query("projectId") int projectId, @Query("projectPassword") String projectPassword);

    //查看用户是否有项目的监测权限
    @POST("project/checkMonitorAuth")//用户id、项目id
    Call<InfoString> checkMonitorAuth(@Body MonitorSend jsonObject);

    //获取不同冻结状态的项目
    @GET("admin/pagedQueryPublishedProject")
    Call<InfoBriefProjectList> getFrezonOrNotProject(@Query("projectStatus") int projectStatus);//0冻结，1未冻结

    //获取不同审核状态的项目
    @GET("admin/pagedQueryProjectApplication")
    Call<InfoApplyingProjectList> getReviewOrNotProject(@Query("applicationStatus") int applicationStatus);//0待审核，2被拒绝

    //这里InfoUser任意，因为data不返回数据。数据体包括  applicationId、status(1通过2拒绝）（字符串形式）、rejectResason
    @POST("admin/verifyApplication")
    Call<InfoUserDetail> verifyApplication(@Body VerifyApplicationSend jsonObject);

    //获得单个用户的信息，InfoUser不是任意的
    @POST("admin/showUserDetailedInfo")
    Call<InfoUserDetail> userDetail(@Query("userId")int userId);

    //冻结用户，InfoUser是任意的
    //参数是userId、freezeHour
    @POST("admin/freezeUser")
    Call<InfoUserDetail> freezeUser(@Body FreezeUserSend jsonObject);

    //冻结项目，InfoUser是任意的
    //参数是projectId、freezeHour
    @POST("admin/freezeProject")
    Call<InfoUserDetail> freezeProject(@Body FreezeProjectSend jsonObject);

}
