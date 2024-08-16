package com.example.finalexam.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.finalexam.helper.Api;
import com.example.finalexam.helper.UserDataShowInterface;
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
import com.example.finalexam.info.data.UserLoginData;
import com.example.finalexam.model.AllLog;
import com.example.finalexam.model.LogData;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.model.sendmodel.FreezeProjectSend;
import com.example.finalexam.model.sendmodel.FreezeUserSend;
import com.example.finalexam.model.sendmodel.IncreaseVisits;
import com.example.finalexam.model.sendmodel.MonitorSend;
import com.example.finalexam.model.sendmodel.PublishSend;
import com.example.finalexam.model.sendmodel.RegisterSend;
import com.example.finalexam.model.UserData;
import com.example.finalexam.model.sendmodel.SetErrorRateSend;
import com.example.finalexam.model.sendmodel.ShowDetailedLogSend;
import com.example.finalexam.model.sendmodel.UpdataProjectSend;
import com.example.finalexam.model.sendmodel.VerifyApplicationSend;
import com.example.finalexam.model.sendmodel.VertifyMonitorSend;
import com.example.finalexam.model.sendmodel.ViewLogForGroupSend;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserPresenter {
    private static final String TAG = "UserPresenter";
    private static final String baseUrl="http://47.113.224.195:31101/api/";
    public UserDataShowInterface activity;
    private static String token ;
    private static UserPresenter presenter=new UserPresenter();
    private static UserData user=new UserData();//一个用户一个presenter
    private List<ProjectData> projectList;
    private List<UserData> userList;
    private List<LogData> logDataList;//正常日志列

    private AllLog logDataListByGroup;//只在  根据不同组查询一周的日志数量  和  查看日志（页面、服务器、移动app）  这两个接口使用

    private UserData userDetail;
    private ProjectData projectDetail;
    private LogData logDetail;
    private String checkResult;
    private static Context context;//接cookie用

    private Retrofit Retrofit;
    //管理员获取项目列表时的两种状态码

    public static final int PROJECT_FROZEN=0;
    public static final int PROJECT_ACTIVE=1;
    //管理员获取审核时的状态码

    public static final int PROJECT_PROCESSING=0;
    public static final int PROJECT_REJECTED=2;

    //过审或不过审

    public static final int PASS_REVIEW=1;
    public static final int REJECT_REVIEW=2;

    //拿取哪个组的log
    public static final int SERVER_LOG=0;
    public static final int FRONT_LOG=1;
    public static final int MOBILE_LOG=2;
    //获取哪种类型的日志
    public static final int EXCEPTION_LOG=0;
    public static final int NORMAL_LOG=1;//其他包括性能，正常日志
    public static final int SERVER_DEFINE_LOG=2;

    public static final int STATUS_SUCCESS = 100;
    public static final int STATUS_FAILED = 101;//发布项目他返回的code可能是不成功的那个，暂且写着
    public static final int STATUS_NO_INTERNET = 102;

    public static final int STATUS_ACCOUNT_NOT_EXIST = 11;
    public static final int STATUS_ACCOUNT_FROZEN= 12;
    public static final int STATUS_ACCOUNT_ALREADY_EXIST = 13;
    public static final int STATUS_PASSWORD_INCORRECT = 21;
    public static final int STATUS_PASSWORDS_INCONSISTENT = 22;
    public static final int STATUS_NO_DATA= 32;
    public static final int STATUS_PROJECT_PASSWORD_INCORRECT= 41;
    public static final int STATUS_APPLICATION_EXITED= 42;


    //获取唯一present
    public static UserPresenter getInstance(UserDataShowInterface activity) {
        presenter.activity = activity;
        Log.d(TAG, "getInstance: "+presenter);
        return presenter;
    }

    //登录请求
    public void userLog(Context context, String account, String password){
        Log.d(TAG, "requestLog: 登录请求");
        Log.d(TAG, "account = " + account + ", password = " + password);
       /* this.context=context;*/

        Api userApi = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);
        resetPresenter();

        Call<InfoUserLogin> dataCall = userApi.log(account,password);
        dataCall.enqueue(new Callback<InfoUserLogin>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(@NonNull Call<InfoUserLogin> call, @NonNull Response<InfoUserLogin> response) {
                InfoUserLogin info = response.body();
                if(info==null){
                    activity.userLog(STATUS_NO_INTERNET);
                } else if (info.getMsg().equals("账号不存在")) {
                    activity.userLog(STATUS_ACCOUNT_NOT_EXIST);
                } else if (info.getMsg().equals("账号被冻结,有疑问联系管理员")) {
                    activity.userLog(STATUS_ACCOUNT_FROZEN);
                } else if (info.getMsg().equals("密码错误")) {
                    activity.userLog(STATUS_PASSWORD_INCORRECT);
                }else{
                    UserLoginData tempData=info.getData();
                    SPPresenter.accordUsername(context, account);
                    SPPresenter.accordPassword(context, password);
                    SPPresenter.accordLoggedStatus(context, true);

                    user.setUsername(account);
                    user.setPassword(password);
                    int id=tempData.getUserId();
                    user.setUserId(id);
                    token=tempData.getToken();
                    Log.d(TAG, "onResponse: "+token);
                    Log.d(TAG,"useData已在log方法中赋值");
                    activity.userLog(STATUS_SUCCESS);
                }

            }
            @Override
            public void onFailure(@NonNull Call<InfoUserLogin> call, @NonNull Throwable throwable) {
                Log.d(TAG, throwable.toString());
                activity.userLog(STATUS_NO_INTERNET);
            }
        });
    }
    public void register(String account, String password, String passwordAgain) {
        if (!password.equals(passwordAgain)) {
            activity.userRegister(STATUS_PASSWORDS_INCONSISTENT);
            return;
        }

        Api userApi = getRetrofit().create(Api.class);


        RegisterSend registerSend=new RegisterSend();
        registerSend.setUsername(account);
        registerSend.setPassword(password);

        Call<InfoUser> dataCall = userApi.register(registerSend);

        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(@NonNull Call<InfoUser> call, @NonNull Response<InfoUser> response) {
                InfoUser info = response.body();
                Log.d(TAG, "onResponse: ");
                if (info == null) activity.userRegister(STATUS_NO_INTERNET);
                else if(info.getData()==null) activity.userRegister(STATUS_ACCOUNT_ALREADY_EXIST);
                else {
                    activity.userRegister(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(@NonNull Call<InfoUser> call, @NonNull Throwable throwable) {
                Log.d(TAG, throwable.toString());
                activity.userRegister(STATUS_NO_INTERNET);
            }
        });
    }

    //做了无数据返回时给AllBriefProject赋一个new出来的值
    public void fetchAllBriefProject(){

        Api api = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);
        Log.d(TAG, "token = " + token);

        Call<InfoShowAllProject> dataCall = api.getAllProjectForUser(token,1,0,"") ;//拿取所有数据

        dataCall.enqueue(new Callback<InfoShowAllProject>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoShowAllProject> call, Response<InfoShowAllProject> response) {
                InfoShowAllProject info=response.body();
                if(info==null){
                    projectList =new ArrayList<>();
                    activity.briefProjectList(STATUS_NO_INTERNET);
                } else if (info.getData()==null) {
                    projectList =new ArrayList<>();
                    activity.briefProjectList(STATUS_NO_DATA);
                } else{
                    if (projectList!=null) {
                        projectList.clear();
                    }
                    projectList =info.getData().getData();
                    activity.briefProjectList(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoShowAllProject> call, Throwable t) {
                presenter.projectList =new ArrayList<>();
                activity.briefProjectList(STATUS_NO_INTERNET);
            }
        });
    }

   //做了无数据返回时给ProjectDetail赋一个new出来的值
    public void fetchProjectDetail(int projectId){
        Api api = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<InfoProject> dataCall = api.getProjectDetail(token,projectId) ;

        dataCall.enqueue(new Callback<InfoProject>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(@NonNull Call<InfoProject> call, @NonNull Response<InfoProject> response) {
                InfoProject info=response.body();
                if(info==null){
                    projectDetail=new ProjectData();
                    activity.projectDetail(STATUS_NO_INTERNET);
                }else if(info.getCode()!=1){
                    projectDetail=new ProjectData();
                    activity.projectDetail(STATUS_FAILED);
                } else{
                    if (projectList!=null) {
                        projectList.clear();
                    }
                    projectDetail=info.getData();
                    activity.projectDetail(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(@NonNull Call<InfoProject> call, @NonNull Throwable throwable) {
                projectDetail=new ProjectData();
                activity.projectDetail(STATUS_NO_INTERNET);
            }
        });
    }
    //获取自己的项目（自己为发布者）
    public void fetchSelfProjects(){

        Api api = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<InfoShowAllProject> dataCall = api.getSelfProjects(token,getUserId(),1,0);
        dataCall.enqueue(new Callback<InfoShowAllProject>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoShowAllProject> call, Response<InfoShowAllProject> response) {
                InfoShowAllProject info= response.body();
                if(info==null){
                    projectList=new ArrayList<>();
                    activity.selfProjectList(STATUS_NO_INTERNET);
                }else if (info.getData()==null) {
                    projectList =new ArrayList<>();
                    activity.selfProjectList(STATUS_NO_DATA);
                } else{
                    if (projectList!=null) {
                        projectList.clear();
                    }
                    projectList=info.getData().getData();
                    activity.selfProjectList(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoShowAllProject> call, Throwable t) {
                projectList=new ArrayList<>();
                activity.selfProjectList(STATUS_NO_INTERNET);
            }
        });
    }
    //获取发给自己的申请
    public void fetchApplication(){

        Api api = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<InfoShowAllProject> dataCall = api.getApplication(token,getUserId(),1,0);
        dataCall.enqueue(new Callback<InfoShowAllProject>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoShowAllProject> call, Response<InfoShowAllProject> response) {
                InfoShowAllProject info= response.body();
                if(info==null){
                    projectList=new ArrayList<>();
                    activity.application(STATUS_NO_INTERNET);
                }else if (info.getData()==null) {
                    projectList =new ArrayList<>();
                    activity.application(STATUS_NO_DATA);
                } else{
                    if (projectList!=null) {
                        projectList.clear();
                    }
                    List<ProjectData> templist =info.getData().getData();
                    if (templist!=null) {
                        if(templist.isEmpty()){
                            projectList=new ArrayList<>();
                        }else{
                            projectList=new ArrayList<>();
                            for (int i = 0; i < templist.size(); i++) {
                                if(templist.get(i).getApplicationStatus().equals("待办")){
                                    projectList.add(templist.get(i));
                                }
                            }
                        }
                    }else{
                        projectList=new ArrayList<>();
                    }
                    activity.application(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoShowAllProject> call, Throwable t) {
                projectList=new ArrayList<>();
                activity.application(STATUS_NO_INTERNET);
            }
        });
    }
    //用户同意或拒绝别人的监控申请,status中1为通过，2为拒绝，rejectReason在同意时传空
    public void verifyMonitorApplication(int applicationId,int status,String rejectReason){
        Api api=getRetrofit().create(Api.class);
        VertifyMonitorSend send=new VertifyMonitorSend(applicationId,status,rejectReason);
        Call<InfoUser> dataCall=api.verifyMonitorApplication(token,send);
        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity=UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                InfoUser info=response.body();
                if(info==null){
                    activity.verifyMonitorApplication(STATUS_NO_INTERNET);
                } else if (info.getCode() != 1) {
                    activity.verifyMonitorApplication(STATUS_FAILED);
                }else{
                    activity.verifyMonitorApplication(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoUser> call, Throwable t) {
                activity.verifyMonitorApplication(STATUS_NO_INTERNET);
            }
        });

    }
    //获取自己监控的项目
    public void fetchMonitorProjects(){

        Api api = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<InfoShowAllProject> dataCall = api.getHaveMonitorProjects(token,getUserId(),1,0);
        dataCall.enqueue(new Callback<InfoShowAllProject>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoShowAllProject> call, Response<InfoShowAllProject> response) {
                InfoShowAllProject info= response.body();
                if(info==null){
                    projectList=new ArrayList<>();
                    activity.monitorProjectList(STATUS_NO_INTERNET);
                }else if (info.getData()==null) {
                    projectList =new ArrayList<>();
                    activity.monitorProjectList(STATUS_NO_DATA);
                } else{
                    if (projectList!=null) {
                        projectList.clear();
                    }
                    projectList=info.getData().getData();
                    activity.monitorProjectList(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoShowAllProject> call, Throwable t) {
                projectList=new ArrayList<>();
                activity.monitorProjectList(STATUS_NO_INTERNET);
            }
        });
    }
    //获取正在申请监控权限的记录
    public void fetchApplyingMonitorProject(){
        Api api = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<InfoShowAllProject> dataCall = api.getApplyingMonitorProject(token,getUserId(),1,0);
        dataCall.enqueue(new Callback<InfoShowAllProject>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoShowAllProject> call, Response<InfoShowAllProject> response) {
                InfoShowAllProject info= response.body();
                if(info==null){
                    projectList=new ArrayList<>();
                    activity.applyingMonitorProjectList(STATUS_NO_INTERNET);
                } else if (info.getData()==null) {
                    projectList =new ArrayList<>();
                    activity.applyingMonitorProjectList(STATUS_NO_DATA);
                } else{
                    if (projectList!=null) {
                        projectList.clear();
                    }
                    List<ProjectData> templist =info.getData().getData();
                    if (templist!=null) {
                        if(templist.isEmpty()){
                            projectList=new ArrayList<>();
                        } else{
                            projectList=new ArrayList<>();
                            for (int i = 0; i < templist.size(); i++) {
                                if(templist.get(i).getApplicationStatus().equals("待办")){
                                    projectList.add(templist.get(i));
                                }
                            }
                        }
                    }else{
                        projectList=new ArrayList<>();
                    }
                    activity.applyingMonitorProjectList(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoShowAllProject> call, Throwable t) {
                projectList=new ArrayList<>();
                activity.applyingMonitorProjectList(STATUS_NO_INTERNET);
            }
        });
    }
    //获取正在发布或更新的项目的记录
    public void fetchMyApplyingProject(){
        Api api = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<InfoShowAllProject> dataCall = api.getMyApplicationProject(token,getUserId(),1,0);
        dataCall.enqueue(new Callback<InfoShowAllProject>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoShowAllProject> call, Response<InfoShowAllProject> response) {
                InfoShowAllProject info= response.body();
                if(info==null){
                    projectList=new ArrayList<>();
                    activity.applyingProjectList(STATUS_NO_INTERNET);
                }else if (info.getData() == null){
                    projectList =new ArrayList<>();
                    activity.applyingProjectList(STATUS_NO_DATA);
                }
                else if (info.getData().getData()==null) {
                    projectList =new ArrayList<>();
                    activity.applyingProjectList(STATUS_NO_DATA);
                } else{
                    if (projectList!=null) {
                        projectList.clear();
                    }
                    List<ProjectData> templist =info.getData().getData();
                    if (templist!=null) {
                        if(templist.isEmpty()){
                            projectList=new ArrayList<>();
                        }else{
                            projectList=new ArrayList<>();
                            for (int i = 0; i < templist.size(); i++) {
                                if(templist.get(i).getApplicationStatus().equals("待办")){
                                    projectList.add(templist.get(i));
                                }
                            }
                        }
                    }else{
                        projectList=new ArrayList<>();
                    }
                    activity.applyingProjectList(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoShowAllProject> call, Throwable t) {
                projectList=new ArrayList<>();
                activity.applyingProjectList(STATUS_NO_INTERNET);
            }
        });
    }
    //自己发布项目
    public void pushProject(String projectName,String description,String projectUrl,String projectPassword){
        Api api = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);


        PublishSend publishData=new PublishSend();
        publishData.setDescription(description);
        publishData.setProjectName(projectName);
        int id=getUserId();
        publishData.setUserId(id);
        publishData.setProjectUrl(projectUrl);
        publishData.setProjectPassword(projectPassword);

        Call<InfoUser> dataCall=api.publishProject(token,publishData);
        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                InfoUser infoUser=response.body();
                if(infoUser==null){
                    activity.projectPublishResult(STATUS_NO_INTERNET);
                } else if (infoUser.getCode()!=1) {
                    activity.projectPublishResult(STATUS_FAILED);//发布项目他返回的code可能是不成功的那个，暂且写着
                } else {
                    activity.projectPublishResult(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoUser> call, Throwable t) {
                activity.projectPublishResult(STATUS_NO_INTERNET);
            }
        });
    }
    //请求项目的监控权限
    public void applyMonitorPermission(int projectId){
        Api api = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        MonitorSend applyMonitorSend=new MonitorSend();
        applyMonitorSend.setProjectId(projectId);
        applyMonitorSend.setUserId(presenter.getUserId());

        Call<InfoUser> dataCall=api.applyMonitorPermission(token,applyMonitorSend);

        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                InfoUser info=response.body();
                if(info==null){
                    activity.applyMonitorPermission(STATUS_NO_INTERNET);
                }else if(info.getCode()!=1){
                    activity.applyMonitorPermission(STATUS_FAILED);
                }else{
                    activity.applyMonitorPermission(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoUser> call, Throwable t) {
                activity.applyMonitorPermission(STATUS_NO_INTERNET);
            }
        });
    }
    //发布者进行项目数据更新
    public void updateProject(String projectUrl,int projectId,String description,String projectPassword){
        Api api = getRetrofit().create(Api.class);
        //项目URL、项目id、项目描述
        UpdataProjectSend updataProjectSend=new UpdataProjectSend();
        updataProjectSend.setProjectUrl(projectUrl);
        updataProjectSend.setProjectId(projectId);
        updataProjectSend.setDescription(description);
        updataProjectSend.setProjectPassword(projectPassword);
        updataProjectSend.setUserId(presenter.getUserId());
        Call<InfoUser> dataCall=api.updateProject(token,updataProjectSend);

        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                InfoUser info=response.body();
                if(info==null){
                    activity.updateProject(STATUS_NO_INTERNET);
                } else if (info.getCode()!=1) {
                    if(info.getMsg().equals("该申请已存在")){
                        activity.updateProject(STATUS_APPLICATION_EXITED);
                    } else if (info.getMsg().equals("项目口令错误")) {
                        activity.updateProject(STATUS_PROJECT_PASSWORD_INCORRECT);
                    }
                }else {
                    activity.updateProject(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoUser> call, Throwable t) {
                activity.updateProject(STATUS_NO_INTERNET);
            }
        });
    }
    //查看的不同组的日志（页面、服务器、移动app）jsonObject包含groupType（后台0、前端1、移动2）、pageSize、page、projectId、logType日志类型(0异常/1其他包括性能，正常日志/2后台自定义日志)
    public void fetchLogForGroup(int groupType,int pageSize,int page,int projectId,int logType){
        Api api=getRetrofit().create(Api.class);
        Call<InfoShowAllLog> dataCall=api.viewLogForGroup(token,groupType,pageSize,page,projectId,logType);
        dataCall.enqueue(new Callback<InfoShowAllLog>() {
            UserDataShowInterface activity = UserPresenter.this.activity;

            @Override
            public void onResponse(Call<InfoShowAllLog> call, Response<InfoShowAllLog> response) {
                InfoShowAllLog info= response.body();
                if(info==null){
                    logDataListByGroup=new AllLog();
                    activity.logDataListByGroup(STATUS_NO_INTERNET);
                } else if (info.getData()==null||info.getData().getData().isEmpty()) {
                    logDataListByGroup=new AllLog();
                    activity.logDataListByGroup(STATUS_NO_DATA);
                }else{
                    logDataListByGroup=info.getData();
                    activity.logDataListByGroup(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoShowAllLog> call, Throwable t) {
                logDataListByGroup=new AllLog();
                activity.logDataListByGroup(STATUS_NO_INTERNET);
            }
        });
    }
    //最近一周内的项目的访问数据和报错统计
    public void fetchProjectPresentationDateOneWeek(int projectId){
        Api api=getRetrofit().create(Api.class);
        Call<InfoLogList> dataCall=api.projectPresentationDateOneWeek(token,projectId);
        dataCall.enqueue(new Callback<InfoLogList>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoLogList> call, Response<InfoLogList> response) {
                InfoLogList info=response.body();
                if (info==null){
                    logDataList=new ArrayList<>();
                    activity.projectPresentationDateOneWeek(STATUS_NO_INTERNET);

                } else if (info.getData()==null) {
                    logDataList=new ArrayList<>();
                    activity.projectPresentationDateOneWeek(STATUS_NO_DATA);
                }else {
                    if(logDataList!=null){
                        logDataList.clear();
                    }
                    logDataList=info.getData();
                    activity.projectPresentationDateOneWeek(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoLogList> call, Throwable t) {
                logDataList=new ArrayList<>();
                activity.projectPresentationDateOneWeek(STATUS_NO_INTERNET);
            }
        });
    }
    //查看项目操作日志(包括项目发布，更新日志)
    public void fetchViewProjectOperateLog(int projectId){
        Api api=getRetrofit().create(Api.class);
        Call<InfoLogList> dataCall=api.viewProjectOperateLog(token,projectId);
        dataCall.enqueue(new Callback<InfoLogList>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoLogList> call, Response<InfoLogList> response) {
                InfoLogList info=response.body();
                if (info==null){
                    logDataList=new ArrayList<>();
                    activity.ViewProjectOperateLog(STATUS_NO_INTERNET);

                } else if (info.getData()==null) {
                    logDataList=new ArrayList<>();
                    activity.ViewProjectOperateLog(STATUS_NO_DATA);
                }else {
                    if(logDataList!=null){
                        logDataList.clear();
                    }
                    logDataList=info.getData();
                    activity.ViewProjectOperateLog(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoLogList> call, Throwable t) {
                logDataList=new ArrayList<>();
                activity.ViewProjectOperateLog(STATUS_NO_INTERNET);
            }
        });
    }
    //获取一个项目的所有监管者
    public void fetchMonitorUser(int projectId){
        Api api = getRetrofit().create(Api.class);
        Call<InfoUserList> dataCall=api.queryMonitorUser(token,projectId);

        dataCall.enqueue(new Callback<InfoUserList>() {
            @Override
            public void onResponse(Call<InfoUserList> call, Response<InfoUserList> response) {
                UserDataShowInterface activity = UserPresenter.this.activity;
                InfoUserList info=response.body();
                if(info==null){
                    userList=new ArrayList<>();
                    activity.monitorUserListResult(STATUS_NO_INTERNET);
                }else if(info.getData()==null){
                    userList=new ArrayList<>();
                    activity.monitorUserListResult(STATUS_NO_DATA);
                }else{
                    if(userList!=null){
                        userList.clear();
                    }
                    userList=info.getData();
                    activity.monitorUserListResult(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoUserList> call, Throwable t) {
                userList=new ArrayList<>();
                activity.monitorUserListResult(STATUS_NO_INTERNET);
            }
        });
    }
    //取消一个用户为一个项目的监管者
    public void cancelMonitorPermission(int userId,int projectId){//用户id、项目id

        Api api = getRetrofit().create(Api.class);

        MonitorSend cancelMontorSend=new MonitorSend();
        cancelMontorSend.setProjectId(projectId);
        cancelMontorSend.setUserId(userId);

        Call<InfoUser> dataCall=api.cancelUserMoitorPermission(token,cancelMontorSend);
        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                InfoUser info=response.body();
                if(info==null){
                    activity.cancelMonitor(STATUS_NO_INTERNET);
                } else if (info.getCode()!=1) {
                    activity.cancelMonitor(STATUS_FAILED);
                }else{
                    activity.cancelMonitor(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoUser> call, Throwable t) {
                activity.cancelMonitor(STATUS_NO_INTERNET);
            }
        });
    }
    //删除项目
    public void deleteProject(int projectId,String projectPassword){//项目id、项目口令

        Api api = getRetrofit().create(Api.class);

        Call<InfoUser> dataCall=api.deleteProject(token,projectId,projectPassword);

        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                InfoUser info=response.body();
                if(info==null){
                    activity.deleteProject(STATUS_NO_INTERNET);
                } else if (info.getCode()!=1) {
                    activity.deleteProject(STATUS_FAILED);
                }else{
                    activity.monitorUserListResult(STATUS_SUCCESS);
                }
            }
            @Override
            public void onFailure(Call<InfoUser> call, Throwable t) {
                activity.deleteProject(STATUS_NO_INTERNET);
            }
        });
    }
    //检测某用户是否有一个项目的管理权限
    public void fetchMonitorPermissionResult(int userId,int projectId){

        Api api = getRetrofit().create(Api.class);
        MonitorSend checkMonitorSend=new MonitorSend();
        checkMonitorSend.setProjectId(projectId);
        checkMonitorSend.setUserId(userId);
        Call<InfoString> dataCall=api.checkMonitorAuth(token,checkMonitorSend);
        dataCall.enqueue(new Callback<InfoString>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoString> call, Response<InfoString> response) {
                InfoString info=response.body();
                if(info==null){
                    checkResult=new String();
                    activity.checkMonitorResult(STATUS_NO_INTERNET);
                }else if(info.getCode()!=1){
                    checkResult=new String();
                    activity.checkMonitorResult(STATUS_FAILED);
                }else{
                    checkResult=info.getData();
                    activity.checkMonitorResult(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoString> call, Throwable t) {
                checkResult=new String();
                activity.checkMonitorResult(STATUS_NO_INTERNET);
            }
        });
    }

    //获取冻结或未被冻结的项目
    public void fetchFreezeOrNotProject(int projectType){

        Api api = getRetrofit().create(Api.class);

        Call<InfoShowAllProject> dataCall=api.getFrezonOrNotProject(token,projectType,1,0,"");

        dataCall.enqueue(new Callback<InfoShowAllProject>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoShowAllProject> call, Response<InfoShowAllProject> response) {
                InfoShowAllProject info=response.body();
                if(info==null){
                    projectList=new ArrayList<>();
                    activity.freezeOrNotProjectList(STATUS_NO_INTERNET);
                }else if(info.getData()==null){
                    projectList=new ArrayList<>();
                    activity.freezeOrNotProjectList(STATUS_NO_DATA);
                }else{
                    if(projectList!=null){
                        projectList.clear();
                    }
                    projectList=info.getData().getData();
                    activity.freezeOrNotProjectList(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoShowAllProject> call, Throwable t) {
                projectList=new ArrayList<>();
                activity.freezeOrNotProjectList(STATUS_NO_INTERNET);
            }
        });
    }
    //获取不同审核状态的项目
    public void fetchReviewOrNotProject(int projectType){

        Api api = getRetrofit().create(Api.class);

        Call<InfoShowAllProject> dataCall=api.getReviewOrNotProject(token,projectType,1,0,"");

        dataCall.enqueue(new Callback<InfoShowAllProject>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoShowAllProject> call, Response<InfoShowAllProject> response) {
                InfoShowAllProject info=response.body();
                if(info==null){
                    projectList=new ArrayList<>();
                    activity.applyOrNotProjectList(STATUS_NO_INTERNET);
                }else if(info.getData()==null){
                    projectList=new ArrayList<>();
                    activity.applyOrNotProjectList(STATUS_NO_DATA);
                }else{
                    if(projectList!=null){
                        projectList.clear();
                    }
                    projectList=info.getData().getData();
                    activity.applyOrNotProjectList(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoShowAllProject> call, Throwable t) {
                projectList=new ArrayList<>();
                activity.applyOrNotProjectList(STATUS_NO_INTERNET);
            }
        });
    }
    //对项目进行审核
    public void verifyApplication(int applicationId, int reviewResult, String rejectResason){//applicationId、status(1通过2拒绝）、rejectResason

        Api api = getRetrofit().create(Api.class);
        VerifyApplicationSend verifyApplicationSend=new VerifyApplicationSend(applicationId,reviewResult,rejectResason);
        Log.d(TAG, "verifyApplication: "+token);
        Call<InfoUser> dataCall=api.verifyApplication(token,verifyApplicationSend);

        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                InfoUser info=response.body();
                if(info==null){
                    activity.verify(STATUS_NO_INTERNET);
                } else if (info.getCode()!=1) {
                    activity.verify(STATUS_FAILED);
                }else{
                    activity.verify(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoUser> call, Throwable t) {
                activity.verify(STATUS_NO_INTERNET);
            }
        });
    }
    //获取用户详情（指的是自己去看对方的信息）
    public void fetchUserDetail(int userId){

        Api api = getRetrofit().create(Api.class);
        Call<InfoUser> dataCall=api.userDetail(token,userId);
        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                InfoUser info=response.body();
                if(info==null){
                    userDetail=new UserData();
                    activity.userDetail(STATUS_NO_INTERNET);
                } else if (info.getData()!=null) {
                    userDetail=info.getData();
                    activity.userDetail(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoUser> call, Throwable t) {
                userDetail=new UserData();
                activity.userDetail(STATUS_NO_INTERNET);
            }
        });
    }
    //冻结用户
    //参数是userId、freezeHour单位为小时
    public void freezeUser(int userId,int freezeHour){

        Api api = getRetrofit().create(Api.class);
        JSONObject jsonObject=new JSONObject();
        FreezeUserSend freezeUserSend=new FreezeUserSend();
        freezeUserSend.setUserId(userId);
        freezeUserSend.setFreezeHour(freezeHour);
        Call<InfoUser> dataCall=api.freezeUser(token,freezeUserSend);
        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                InfoUser info=response.body();
                if(info==null){
                    activity.freeze(STATUS_NO_INTERNET);
                } else if (info.getCode()!=1) {
                    activity.freeze(STATUS_FAILED);
                }else {
                    activity.freeze(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoUser> call, Throwable t) {
                activity.freeze(STATUS_NO_INTERNET);
            }
        });
    }
    //冻结项目
    //参数是projectId、freezeHour单位为小时
    public void freezeProject(int projectId,int freezeHour){

        Api api = getRetrofit().create(Api.class);
        FreezeProjectSend freezeProjectSend=new FreezeProjectSend();
        freezeProjectSend.setProjectId(projectId);
        freezeProjectSend.setFreezeHour(freezeHour);
        Call<InfoUser> dataCall=api.freezeProject(token,freezeProjectSend);
        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                InfoUser info=response.body();
                if(info==null){
                    activity.freeze(STATUS_NO_INTERNET);
                } else if (info.getCode()!=1) {
                    activity.freeze(STATUS_FAILED);
                }else {
                    activity.freeze(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoUser> call, Throwable t) {
                activity.freeze(STATUS_NO_INTERNET);
            }
        });
    }
    //查看服务器攻击日志
    public void fetchAttackServerLog(int page,int pageSize){//得到记录条数是page*pageSize
        Api api=getRetrofit().create(Api.class);
        Call<InfoLogList> dataCall=api.queryAttackServerLog(token,page,pageSize);
        dataCall.enqueue(new Callback<InfoLogList>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoLogList> call, Response<InfoLogList> response) {
                InfoLogList info=response.body();
                if (info==null){
                    logDataList=new ArrayList<>();
                    activity.attackServerLogList(STATUS_NO_INTERNET);
                    
                } else if (info.getData()==null) {
                    logDataList=new ArrayList<>();
                    activity.attackServerLogList(STATUS_NO_DATA);
                }else {
                    if(logDataList!=null){
                        logDataList.clear();
                    }
                    logDataList=info.getData();
                    activity.attackServerLogList(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoLogList> call, Throwable t) {
                logDataList=new ArrayList<>();
                activity.attackServerLogList(STATUS_NO_INTERNET);
            }
        });
    }
    //获取所有用户操作的日志
    public void fetchAllUserOperationLog(int page,int pageSize){//得到记录条数是page*pageSize
        Api api=getRetrofit().create(Api.class);
        Call<InfoLogList> dataCall=api.queryAttackServerLog(token,page,pageSize);
        dataCall.enqueue(new Callback<InfoLogList>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoLogList> call, Response<InfoLogList> response) {
                InfoLogList info=response.body();
                if (info==null){
                    logDataList=new ArrayList<>();
                    activity.allUserOperationLogList(STATUS_NO_INTERNET);

                } else if (info.getData()==null) {
                    logDataList=new ArrayList<>();
                    activity.allUserOperationLogList(STATUS_NO_DATA);
                }else {
                    if(logDataList!=null){
                        logDataList.clear();
                    }
                    logDataList=info.getData();
                    activity.allUserOperationLogList(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoLogList> call, Throwable t) {
                logDataList=new ArrayList<>();
                activity.allUserOperationLogList(STATUS_NO_INTERNET);
            }
        });
    }
    //用户查看项目发送请求，项目访问量加一,InfoUser任意,projectData只包含id
    public void increaseVisits(int projectId){
       /* Api api=getRetrofit().create(Api.class);
        Call<InfoUser> dataCall=api.increaseVisits(token,new IncreaseVisits(projectId));
        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                InfoUser info= response.body();
                if(info==null){
                    activity.increaseView(STATUS_NO_INTERNET);

                } else if (info.getCode()!=1) {
                    activity.increaseView(STATUS_FAILED);
                }else{
                    activity.increaseView(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoUser> call, Throwable t) {
                activity.increaseView(STATUS_NO_INTERNET);

            }
        });*/
    }

    public void setErrorRate(int projectId,double errorRate){
        Api api=getRetrofit().create(Api.class);
        Call<InfoUser> dataCall=api.setErrorRate(token,new SetErrorRateSend(projectId,errorRate));

        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity=UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                InfoUser info=response.body();
                if(info==null){
                    activity.setErrorRate(STATUS_NO_INTERNET);
                } else if (info.getCode()!=1) {
                    activity.setErrorRate(STATUS_FAILED);
                }else{
                    activity.setErrorRate(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoUser> call, Throwable t) {
                activity.setErrorRate(STATUS_NO_INTERNET);
            }
        });
    }
    public void fetchLogDetail(int groupType,int logId,int logType){
        Api api=getRetrofit().create(Api.class);
        Call<InfoLog> dataCall=api.showDetaliedLog(token,new ShowDetailedLogSend(groupType,logId,logType));

        dataCall.enqueue(new Callback<InfoLog>() {
            UserDataShowInterface activity=UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoLog> call, Response<InfoLog> response) {
                InfoLog info=response.body();
                if(info==null){
                    logDetail=new LogData();
                    activity.logDetail(STATUS_NO_INTERNET);
                } else if (info.getCode()!=1) {
                    logDetail=new LogData();
                    activity.logDetail(STATUS_FAILED);
                }else{

                    logDetail=info.getData();
                    activity.logDetail(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoLog> call, Throwable t) {
                logDetail=new LogData();
                activity.logDetail(STATUS_NO_INTERNET);
            }
        });
    }
    //根据不同组查询一周的日志数量
    public void fetchWeekLogNum(int groupType,int logType,int projectId){
        Api api=getRetrofit().create(Api.class);
        Call<InfoShowAllLog> dataCall=api.showLogNumberOneWeekForGroup(token,new ViewLogForGroupSend(groupType,logType,projectId));

        dataCall.enqueue(new Callback<InfoShowAllLog>() {
            UserDataShowInterface activity=UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoShowAllLog> call, Response<InfoShowAllLog> response) {
                InfoShowAllLog info=response.body();
                if(info==null){
                    logDataListByGroup=new AllLog();
                    activity.weekLogNum(STATUS_NO_INTERNET);
                } else if (info.getCode()!=1) {
                    logDataListByGroup=new AllLog();
                    activity.weekLogNum(STATUS_FAILED);
                }else{
                    logDataListByGroup=info.getData();
                    activity.weekLogNum(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoShowAllLog> call, Throwable t) {
                logDataListByGroup=new AllLog();
                activity.weekLogNum(STATUS_NO_INTERNET);
            }
        });
    }



    //获取登录状态，已登录则返回true
    public boolean isLogged(Context context) {
        try {
            SharedPreferences sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
            return sp.getBoolean("isLogged", false);
        } catch (Exception e) {

        }
        return false;
    }


    //获取账号名
    public static String getUserName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        return sp.getString("username", null);
    }
    //获取密码
    public String getPassword(Context context) {
        SharedPreferences sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        return sp.getString("password", "null");
    }
    //获取user的id
    public int getUserId(){return user.getUserId();}
    //获取user的token
    public static String getToken(){return token;}


    //先调用相应接口再调用这个方法
    public ProjectData getProjectDetail(){return projectDetail;}
    ////获取用户详情（指的是自己去看对方的信息）
    public UserData getUserDetail(){return userDetail;}

    //先调用相应接口再调用这个方法
    public List<ProjectData> getProjectList(){return projectList;}
    //获取一系列用户数据
    public List<UserData> getUserList(){return userList;}
    public String getCheckResult(){return checkResult;}
    public List<LogData> getLogDataList() {return logDataList;}

    public LogData getLogDetail() {
        return logDetail;
    }

    //获取一个项目的一个组（前后移动端）的所有日志，fetchLogForGroup的时候会被赋值，其他全部赋值到logDataList
    public AllLog getLogDataListByGroup() {return logDataListByGroup;}

    public static void setContext(Context context){UserPresenter.context=context;}

    public static Context getContext() {return context;}
    //重置userPresent，在退出时用

    public static void resetPresenter() {
        presenter=new UserPresenter();
    }

    public Retrofit getRetrofit() {
        Retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return Retrofit;
    }
}


