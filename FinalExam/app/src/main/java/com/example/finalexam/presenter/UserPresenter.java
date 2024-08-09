package com.example.finalexam.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.finalexam.helper.Api;
import com.example.finalexam.helper.UserDataShowInterface;
import com.example.finalexam.info.InfoProject;
import com.example.finalexam.info.InfoProjectList;
import com.example.finalexam.info.InfoShowAllProject;
import com.example.finalexam.info.InfoString;
import com.example.finalexam.info.InfoUser;
import com.example.finalexam.info.InfoUserList;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.model.sendmodel.FreezeProjectSend;
import com.example.finalexam.model.sendmodel.FreezeUserSend;
import com.example.finalexam.model.sendmodel.MonitorSend;
import com.example.finalexam.model.sendmodel.PublishSend;
import com.example.finalexam.model.sendmodel.RegisterSend;
import com.example.finalexam.model.UserData;
import com.example.finalexam.model.sendmodel.UpdataProjectSend;
import com.example.finalexam.model.sendmodel.VerifyApplicationSend;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserPresenter {
    private static final String TAG = "UserPresenter";
    private static final String baseUrl="http://47.113.224.195:31111/";
    public UserDataShowInterface activity;
    private static UserPresenter presenter=new UserPresenter();
    private UserData user =new UserData();//一个用户一个presenter
    private List<ProjectData> projectList;
    private List<UserData> userList;
    private UserData userDetail;
    private ProjectData projectDetail;
    private String checkResult;
    private Context context;//接cookie用

    private Retrofit Retrofit;

    private static final int PROJECT_FROZEN=0;
    private static final int PROJECT_ACTIVE=1;
    private static final int PROJECT_PROCESSING=0;
    private static final int PROJECT_ACCEPTED=1;

    private static final int PASS_REVIEW=1;
    private static final int REJECT_REVIEW=2;
    public static final int STATUS_SUCCESS = 100;
    public static final int STATUS_FAILED = 101;//发布项目他返回的code可能是不成功的那个，暂且写着
    public static final int STATUS_NO_INTERNET = 102;

    public static final int STATUS_ACCOUNT_NOT_EXIST = 11;
    public static final int STATUS_ACCOUNT_FROZEN= 12;
    public static final int STATUS_ACCOUNT_ALREADY_EXIST = 13;
    public static final int STATUS_PASSWORD_INCORRECT = 21;
    public static final int STATUS_PASSWORDS_INCONSISTENT = 22;
    public static final int STATUS_NO_DATA= 32;




    //获取唯一present
    public static UserPresenter getInstance(UserDataShowInterface activity) {
        presenter.activity = activity;
        return presenter;
    }

    //登录请求
    public void userLog(Context context, String account, String password){
        Log.d(TAG, "requestLog: 登录请求");
        Log.d(TAG, "account = " + account + ", password = " + password);
       /* this.context=context;*/

        Api userApi = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<InfoUser> dataCall = userApi.log(account,password);
        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(@NonNull Call<InfoUser> call, @NonNull Response<InfoUser> response) {
                InfoUser info = response.body();
                if(info==null){
                    activity.userLog(STATUS_NO_INTERNET);
                } else if (info.getMsg().equals("账号不存在")) {
                    activity.userLog(STATUS_ACCOUNT_NOT_EXIST);
                } else if (info.getMsg().equals("账号被冻结")) {
                    activity.userLog(STATUS_ACCOUNT_FROZEN);
                } else if (info.getMsg().equals("密码错误")) {
                    activity.userLog(STATUS_PASSWORD_INCORRECT);
                }else {
                    UserData tempData=info.getData();
                    SPPresenter.accordUsername(context, account);
                    SPPresenter.accordPassword(context, password);
                    SPPresenter.accordLoggedStatus(context, true);
                    user.setUsername(account);
                    user.setPassword(password);
                    user.setUserId(tempData.getUserId());
                    Log.d(TAG,"useData已在log方法中赋值");
                    activity.userLog(STATUS_SUCCESS);
                }

            }
            @Override
            public void onFailure(@NonNull Call<InfoUser> call, @NonNull Throwable throwable) {
                Log.d(TAG, throwable.toString());
                activity.userLog(STATUS_NO_INTERNET);
            }
        });
    }

    public void register(Context context, String account, String password, String passwordAgain) {
        if (!password.equals(passwordAgain)) {
            activity.userRegister(STATUS_PASSWORDS_INCONSISTENT);
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api userApi = retrofit.create(Api.class);


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

        Call<InfoShowAllProject> dataCall = api.getAllProjectForUser(1,0,null) ;//拿取所有数据

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
                    projectList =info.getData().getList();
                    activity.briefProjectList(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoShowAllProject> call, Throwable t) {
                projectList =new ArrayList<>();
                activity.briefProjectList(STATUS_NO_INTERNET);
            }
        });
    }

   //做了无数据返回时给ProjectDetail赋一个new出来的值
    public void fetchProjectDetail(int projectId){
        Api api = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<InfoProject> dataCall = api.getProjectDetail(projectId) ;

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

        Call<InfoProjectList> dataCall = api.getSelfProjects(user.getUserId());
        dataCall.enqueue(new Callback<InfoProjectList>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoProjectList> call, Response<InfoProjectList> response) {
                InfoProjectList info= response.body();
                if(info==null){
                    projectList=new ArrayList<>();
                    activity.selfProjectList(STATUS_NO_INTERNET);
                }else if (info.getData()==null) {
                    projectList =new ArrayList<>();
                    activity.selfProjectList(STATUS_NO_DATA);
                } else{
                    projectList=info.getData();
                    activity.selfProjectList(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoProjectList> call, Throwable t) {
                projectList=new ArrayList<>();
                activity.selfProjectList(STATUS_NO_INTERNET);
            }
        });
    }
    //获取自己监控的项目
    public void fetchMonitorProjects(){

        Api api = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<InfoProjectList> dataCall = api.getHaveMonitorProjects(user.getUserId());
        dataCall.enqueue(new Callback<InfoProjectList>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoProjectList> call, Response<InfoProjectList> response) {
                InfoProjectList info= response.body();
                if(info==null){
                    projectList=new ArrayList<>();
                    activity.monitorProjectList(STATUS_NO_INTERNET);
                }else if (info.getData()==null) {
                    projectList =new ArrayList<>();
                    activity.monitorProjectList(STATUS_NO_DATA);
                } else{
                    projectList=info.getData();
                    activity.monitorProjectList(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoProjectList> call, Throwable t) {
                projectList=new ArrayList<>();
                activity.monitorProjectList(STATUS_NO_INTERNET);
            }
        });
    }
    //获取正在申请监控权限的记录
    public void fetchApplyingMonitorProject(){
        Api api = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<InfoProjectList> dataCall = api.getApplyingMonitorProject(user.getUserId());
        dataCall.enqueue(new Callback<InfoProjectList>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoProjectList> call, Response<InfoProjectList> response) {
                InfoProjectList info= response.body();
                if(info==null){
                    projectList=new ArrayList<>();
                    activity.applyingMonitorProjectList(STATUS_NO_INTERNET);
                } else if (info.getData()==null) {
                    projectList =new ArrayList<>();
                    activity.applyingMonitorProjectList(STATUS_NO_DATA);
                } else{
                    projectList=info.getData();
                    activity.applyingMonitorProjectList(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoProjectList> call, Throwable t) {
                projectList=new ArrayList<>();
                activity.applyingMonitorProjectList(STATUS_NO_INTERNET);
            }
        });
    }
    //获取正在发布或更新的项目的记录
    public void fetchMyApplyingProject(){
        Api api = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<InfoProjectList> dataCall = api.getMyApplicationProject(user.getUserId());
        dataCall.enqueue(new Callback<InfoProjectList>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoProjectList> call, Response<InfoProjectList> response) {
                InfoProjectList info= response.body();
                if(info==null){
                    projectList=new ArrayList<>();
                    activity.applyingProjectList(STATUS_NO_INTERNET);
                } else if (info.getData()==null) {
                    projectList =new ArrayList<>();
                    activity.applyingProjectList(STATUS_NO_DATA);
                } else{
                    projectList=info.getData();
                    activity.applyingProjectList(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoProjectList> call, Throwable t) {
                projectList=new ArrayList<>();
                activity.applyingProjectList(STATUS_NO_INTERNET);
            }
        });
    }

    //自己发布项目
    public void pushProject(String projectName,String description,int userId,String projectUrl,String projectPassword){
        Api api = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);


        PublishSend publishData=new PublishSend();
        publishData.setDescription(description);
        publishData.setProjectName(projectName);
        publishData.setUserId(userId);
        publishData.setProjectUrl(projectUrl);
        publishData.setProjectPassword(projectPassword);

        Call<InfoUser> dataCall=api.publishProject(publishData);
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
    public void applyMonitorPermission(int userId,int projectId){
        Api api = getRetrofit().create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        MonitorSend applyMonitorSend=new MonitorSend();
        applyMonitorSend.setProjectId(projectId);
        applyMonitorSend.setUserId(userId);

        Call<InfoUser> dataCall=api.applyMonitorPermission(applyMonitorSend);

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
    public void updateProject(String projectUrl,int projectId,String description,String projectPassword,int userId){
        Api api = getRetrofit().create(Api.class);
        //项目URL、项目id、项目描述
        UpdataProjectSend updataProjectSend=new UpdataProjectSend();
        updataProjectSend.setProjectUrl(projectUrl);
        updataProjectSend.setProjectId(projectId);
        updataProjectSend.setDescription(description);
        updataProjectSend.setProjectPassword(projectPassword);
        updataProjectSend.setUserId(userId);
        Call<InfoUser> dataCall=api.updateProject(updataProjectSend);

        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                InfoUser info=response.body();
                if(info==null){
                    activity.updateProject(STATUS_NO_INTERNET);
                } else if (info.getCode()!=1) {
                    activity.updateProject(STATUS_FAILED);
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
    //获取一个项目的所有监管者
    public void fetchMonitorUser(int projectId){
        Api api = getRetrofit().create(Api.class);
        Call<InfoUserList> dataCall=api.queryMonitorUser(projectId);

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

        Call<InfoUser> dataCall=api.cancelUserMoitorPermission(cancelMontorSend);
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

        Call<InfoUser> dataCall=api.deleteProject(projectId,projectPassword);

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
        Call<InfoString> dataCall=api.checkMonitorAuth(checkMonitorSend);
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

        Call<InfoShowAllProject> dataCall=api.getFrezonOrNotProject(projectType);

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
                    projectList=info.getData().getList();
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

        Call<InfoShowAllProject> dataCall=api.getReviewOrNotProject(projectType);

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
                    projectList=info.getData().getList();
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
    public void verifyApplication(int projectId, int reviewResult, String rejectResason){//applicationId、status(1通过2拒绝）、rejectResason

        Api api = getRetrofit().create(Api.class);
        VerifyApplicationSend verifyApplicationSend=new VerifyApplicationSend(projectId,reviewResult,rejectResason);
        Call<InfoUser> dataCall=api.verifyApplication(verifyApplicationSend);

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
        Call<InfoUser> dataCall=api.userDetail(userId);
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
        Call<InfoUser> dataCall=api.freezeUser(freezeUserSend);
        dataCall.enqueue(new Callback<InfoUser>() {
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
        JSONObject jsonObject=new JSONObject();
        FreezeProjectSend freezeProjectSend=new FreezeProjectSend();
        freezeProjectSend.setProjectId(projectId);
        freezeProjectSend.setFreezeHour(freezeHour);
        Call<InfoUser> dataCall=api.freezeProject(freezeProjectSend);
        dataCall.enqueue(new Callback<InfoUser>() {
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
    public String getUserName(Context context) {
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

    //先调用相应接口再调用这个方法
    public ProjectData getProjectDetail(){return projectDetail;}
    ////获取用户详情（指的是自己去看对方的信息）
    public UserData getUserDetail(){return userDetail;}

    //先调用相应接口再调用这个方法
    public List<ProjectData> getProjectList(){return projectList;}
    //获取一系列用户数据
    public List<UserData> getUserList(){return userList;}
    public String getCheckResult(){return checkResult;}

    public void setContext(Context context){this.context=context;}

    public Retrofit getRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new PersistentCookieJar(context))
                .build();

        Retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return Retrofit;
    }
}
class PersistentCookieJar implements CookieJar {
    private Context context;
    SharedPreferences sharedPreferences ;
    public PersistentCookieJar(Context context) {
        this.context=context;
        sharedPreferences = context.getSharedPreferences("cookieData", Context.MODE_PRIVATE);
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (Cookie cookie : cookies) {
            editor.putString(cookie.name(), cookie.toString());
        }
        editor.apply();
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookieList = new ArrayList<>();
        for (String cookieName : sharedPreferences.getAll().keySet()) {
            String cookieValue = sharedPreferences.getString(cookieName, "");
            cookieList.add(Cookie.parse(url, cookieValue));
        }
        return cookieList;
    }
}


