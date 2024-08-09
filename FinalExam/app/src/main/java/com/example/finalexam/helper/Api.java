package com.example.finalexam.helper;

import com.example.finalexam.info.InfoProject;
import com.example.finalexam.info.InfoShowAllProject;
import com.example.finalexam.info.InfoString;
import com.example.finalexam.info.InfoUserList;
import com.example.finalexam.info.InfoUser;
import com.example.finalexam.info.InfoProjectList;
import com.example.finalexam.sendmodel.FreezeProjectSend;
import com.example.finalexam.sendmodel.FreezeUserSend;
import com.example.finalexam.sendmodel.MonitorSend;
import com.example.finalexam.sendmodel.PublishSend;
import com.example.finalexam.sendmodel.RegisterSend;
import com.example.finalexam.sendmodel.UpdataProjectSend;
import com.example.finalexam.sendmodel.VerifyApplicationSend;

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
    Call<InfoUser> log(@Field("username") String username,
                       @Field("password") String password);

    @POST("user/register")
    Call<InfoUser> register(@Body RegisterSend user);

    @FormUrlEncoded
    @GET("user/showSelfProjects")
    Call<InfoProjectList> getSelfProjects(@Query("userId") int useId);

    @FormUrlEncoded
    @GET("user/showHaveMonitorPermissionProjects")
    Call<InfoProjectList> getHaveMonitorProjects(@Query("userId") int useId);

    //获取我申请过监控的项目
    @FormUrlEncoded
    @GET("user/myApplicationOnMonitorProject")
    Call<InfoProjectList> getApplyingMonitorProject(@Query("userId") int useId);

    //获取我发布或更改的项目
    @GET("user/myApplicationProject")
    Call<InfoProjectList> getMyApplicationProject(@Query("userId") int useId);

    @GET("project/showAllProjectForUser")
    Call<InfoShowAllProject> getAllProjectForUser(@Query("page") int page, @Query("pagesize") int pageSize,@Query("projectName")String projectName) ;//page为零拿所有

    //这里InfoUser任意，因为data不返回数据。数据体包括  项目名、项目描述、发布者id、项目url、项目口令
    @POST("project/publishProject")
    Call<InfoUser> publishProject(@Body PublishSend jsonObject);

    @GET("project/detailedInfo")
    Call<InfoProject> getProjectDetail(@Query("projectId") int projectId);

    //这里InfoUser任意，因为data不返回数据。数据体包括  用户id、项目id
    @POST("project/applyMonitorPermission")
    Call<InfoUser> applyMonitorPermission(@Body MonitorSend jsonObject);

    //这里InfoUser任意，因为data不返回数据。数据体包括  项目URL、项目id、项目描述、项目口令,用户id
    @POST("project/updateProject")
    Call<InfoUser> updateProject(@Body UpdataProjectSend jsonObject);

    @GET("project/queryOwnMonitorUser")
    Call<InfoUserList> queryMonitorUser(@Query("projectId") int projectId);

    //这里InfoUser任意，因为data不返回数据。数据体包括  用户id、项目id
    @POST("project/cancelUserMonitorPermission")
    Call<InfoUser> cancelUserMoitorPermission(@Body MonitorSend jsonObject);

    //这里InfoUser任意，因为data不返回数据。数据体包括  项目id、项目口令
    @DELETE("project/deleteProject")
    Call<InfoUser> deleteProject(@Query("projectId") int projectId,@Query("projectPassword") String projectPassword);

    //查看用户是否有项目的监测权限
    @POST("project/checkMonitorAuth")//用户id、项目id
    Call<InfoString> checkMonitorAuth(@Body MonitorSend jsonObject);

    //获取不同冻结状态的项目
    @GET("admin/pagedQueryPublishedProject")
    Call<InfoShowAllProject> getFrezonOrNotProject(@Query("projectStatus") int projectStatus);//0冻结，1未冻结

    //获取不同审核状态的项目
    @GET("admin/pagedQueryProjectApplication")
    Call<InfoShowAllProject> getReviewOrNotProject(@Query("applicationStatus") int applicationStatus);//0待审核，2被拒绝

    //这里InfoUser任意，因为data不返回数据。数据体包括  applicationId、status(1通过2拒绝）（字符串形式）、rejectResason
    @POST("admin/verifyApplication")
    Call<InfoUser> verifyApplication(@Body VerifyApplicationSend jsonObject);

    //获得单个用户的信息，InfoUser不是任意的
    @POST("admin/showUserDetailedInfo")
    Call<InfoUser> userDetail(@Query("userId")int userId);

    //冻结用户，InfoUser是任意的
    //参数是userId、freezeHour
    @POST("admin/freezeUser")
    Call<InfoUser> freezeUser(@Body FreezeUserSend jsonObject);

    //冻结项目，InfoUser是任意的
    //参数是projectId、freezeHour
    @POST("admin/freezeProject")
    Call<InfoUser> freezeProject(@Body FreezeProjectSend jsonObject);

}
