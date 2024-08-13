package com.example.finalexam.helper;

import com.example.finalexam.info.InfoLog;
import com.example.finalexam.info.InfoLogList;
import com.example.finalexam.info.InfoProject;
import com.example.finalexam.info.InfoProjectList;
import com.example.finalexam.info.InfoShowAllLog;
import com.example.finalexam.info.InfoShowAllProject;
import com.example.finalexam.info.InfoString;
import com.example.finalexam.info.InfoUser;
import com.example.finalexam.info.InfoUserList;
import com.example.finalexam.info.InfoUserLogin;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.model.sendmodel.FreezeProjectSend;
import com.example.finalexam.model.sendmodel.FreezeUserSend;
import com.example.finalexam.model.sendmodel.MonitorSend;
import com.example.finalexam.model.sendmodel.PublishSend;
import com.example.finalexam.model.sendmodel.RegisterSend;
import com.example.finalexam.model.sendmodel.SetErrorRateSend;
import com.example.finalexam.model.sendmodel.ShowDetailedLogSend;
import com.example.finalexam.model.sendmodel.UpdataProjectSend;
import com.example.finalexam.model.sendmodel.VerifyApplicationSend;
import com.example.finalexam.model.sendmodel.VertifyMonitorSend;
import com.example.finalexam.model.sendmodel.ViewLogForGroupSend;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<InfoUserLogin> log(@Field("username") String username,
                            @Field("password") String password);

    @POST("user/register")
    Call<InfoUser> register(@Body RegisterSend user);

    @GET("user/showSelfProjects")//page为分几页，pageSize是一页多少个
    Call<InfoProjectList> getSelfProjects(@Header ("token") String token,@Query("userId") int useId,@Query("page")int page,@Query("pageSize")int pageSize);


    @GET("user/showHaveMonitorPermissionProjects")
    Call<InfoProjectList> getHaveMonitorProjects(@Header ("token") String token,@Query("userId") int useId,@Query("page")int page,@Query("pageSize")int pageSize);

    //获取我申请过监控的项目

    @GET("user/myApplicationOnMonitorProject")
    Call<InfoProjectList> getApplyingMonitorProject(@Header ("token") String token,@Query("userId") int useId,@Query("page")int page,@Query("pageSize")int pageSize);

    //获取我发布或更改的项目
    @GET("user/myApplicationProject")
    Call<InfoProjectList> getMyApplicationProject(@Header ("token") String token,@Query("userId") int useId,@Query("page")int page,@Query("pageSize")int pageSize);
    //收到的申请
    @GET("user/receivedMonitorApplication")
    Call<InfoProjectList> getApplication(@Header ("token") String token,@Query("userId") int useId,@Query("page")int page,@Query("pageSize")int pageSize);
    //用户同意别人的申请
    @POST("user/verifyMonitorApplication")
    Call<InfoUser> verifyMonitorApplication(@Header ("token") String token, @Body VertifyMonitorSend jsonObject);

    @GET("project/showAllProjectForUser")
    Call<InfoShowAllProject> getAllProjectForUser(@Header ("token") String token,@Query("page") int page, @Query("pagesize") int pageSize, @Query("projectName")String projectName) ;//page为零拿所有

    //这里InfoUser任意，因为data不返回数据。数据体包括  项目名、项目描述、发布者id、项目url、项目口令
    @POST("project/publishProject")
    Call<InfoUser> publishProject(@Header ("token") String token,@Body PublishSend jsonObject);

    @GET("project/detailedInfo")
    Call<InfoProject> getProjectDetail(@Header ("token") String token,@Query("projectId") int projectId);

    //这里InfoUser任意，因为data不返回数据。数据体包括  用户id、项目id
    @POST("project/applyMonitorPermission")
    Call<InfoUser> applyMonitorPermission(@Header ("token") String token,@Body MonitorSend jsonObject);
    //获取摸个项目的监控名单
    @GET("project/queryOwnMonitorUser")
    Call<InfoUserList> queryMonitorUser(@Header ("token") String token,@Query("projectId") int projectId);

    //这里InfoUser任意，因为data不返回数据。数据体包括  项目URL、项目id、项目描述、项目口令,用户id
    @POST("project/updateProject")
    Call<InfoUser> updateProject(@Header ("token") String token,@Body UpdataProjectSend jsonObject);

    //这里InfoUser任意，因为data不返回数据。数据体包括  用户id、项目id
    @POST("project/cancelUserMonitorPermission")
    Call<InfoUser> cancelUserMoitorPermission(@Header ("token") String token,@Body MonitorSend jsonObject);

    //这里InfoUser任意，因为data不返回数据。数据体包括  项目id、项目口令
    @DELETE("project/deleteProject")
    Call<InfoUser> deleteProject(@Header ("token") String token,@Query("projectId") int projectId, @Query("projectPassword") String projectPassword);

    //查看用户是否有项目的监测权限
    @POST("project/checkMonitorAuth")//用户id、项目id
    Call<InfoString> checkMonitorAuth(@Header ("token") String token,@Body MonitorSend jsonObject);
    //设置报警阈值,InfoUser任意
    @POST("project/setErrorRate")
    Call<InfoUser> setErrorRate(@Header("token") String token, @Body SetErrorRateSend setErrorRateSend);

    //获取不同冻结状态的项目
    @GET("admin/pagedQueryPublishedProject")
    Call<InfoShowAllProject> getFrezonOrNotProject(@Header ("token") String token,@Query("projectStatus") int projectStatus,@Query("page") int page, @Query("pagesize") int pageSize);//0冻结，1未冻结

    //获取不同审核状态的项目
    @GET("admin/pagedQueryProjectApplication")
    Call<InfoShowAllProject> getReviewOrNotProject(@Header ("token") String token,@Query("applicationStatus") int applicationStatus,@Query("page") int page,@Query("pageSize") int pageSize,@Query("keyWord")String keyWord);//0待审核，2被拒绝

    //这里InfoUser任意，因为data不返回数据。数据体包括  applicationId、status(1通过2拒绝）（字符串形式）、rejectResason
    @POST("admin/verifyApplication")
    Call<InfoUser> verifyApplication(@Header ("token") String token,@Body VerifyApplicationSend jsonObject);

    //获得单个用户的信息，InfoUser不是任意的
    @POST("admin/showUserDetailedInfo")
    Call<InfoUser> userDetail(@Header ("token") String token,@Query("userId")int userId);

    //冻结用户，InfoUser是任意的
    //参数是userId、freezeHour
    @POST("admin/freezeUser")
    Call<InfoUser> freezeUser(@Header ("token") String token,@Body FreezeUserSend jsonObject);

    //冻结项目，InfoUser是任意的
    //参数是projectId、freezeHour
    @POST("admin/freezeProject")
    Call<InfoUser> freezeProject(@Header ("token") String token,@Body FreezeProjectSend jsonObject);

    //获取攻击服务器日志
    @GET("log/queryAttackServerLog")
    Call<InfoLogList> queryAttackServerLog(@Header ("token") String token,@Query("page") int page,@Query("pageSize") int pageSize);

    //获取所有用户操作的日志
    @GET("log/queryAllUserOperationLog")
    Call<InfoLogList> queryAllUserOperationLog(@Header ("token") String token,@Query("page") int page,@Query("pageSize") int pageSize);

    //查看的不同组的日志（页面、服务器、移动app）jsonObject包含groupType（后台0、前端1、移动2）、pageSize、page、projectId、logType日志类型(0异常/1其他包括性能，正常日志/2后台自定义日志)
    @GET("log/viewLogForGroup")
    Call<InfoShowAllLog> viewLogForGroup(@Header ("token") String token,@Body ViewLogForGroupSend jsonObject);

    //最近一周内的项目的访问数据和报错统计
    @GET("log/projectPresentationDateOneWeek")
    Call<InfoLogList> projectPresentationDateOneWeek(@Header ("token") String token,@Query("projectId") int projectId);

    //查看项目操作日志(包括项目发布，更新日志)
    @GET("log/ViewProjectOperateLog")
    Call<InfoLogList> viewProjectOperateLog(@Header ("token") String token,@Query("projectId") int projectId);

    //用户查看项目发送请求，项目访问量加一,InfoUser任意,projectData只包含id
    @POST("log/increaseVisits")
    Call<InfoUser> increaseVisits(@Header ("token") String token,@Body ProjectData projectData);

    //根据日志id查看详细信息
    @POST("log/showDetaliedLog")
    Call<InfoLog> showDetaliedLog(@Header("token") String token, @Body ShowDetailedLogSend showDetailedLogSend);

    //根据不同组查询一周的日志数量
    @POST("log/showLogNumberOneWeekForGroup")
    Call<InfoShowAllLog> showLogNumberOneWeekForGroup(@Header("token") String token,@Body ViewLogForGroupSend viewLogForGroupSend);
}
