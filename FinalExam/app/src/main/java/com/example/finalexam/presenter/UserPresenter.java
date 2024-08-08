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
import com.example.finalexam.info.InfoUser;
import com.example.finalexam.model.ProjectData;
import com.example.finalexam.model.UserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserPresenter {
    private static final String TAG = "UserPresenter";
    private static final String baseUrl="http://47.113.224.195:31110/";
    public UserDataShowInterface activity;
    private static UserPresenter presenter=new UserPresenter();
    private UserData user =new UserData();//一个用户一个presenter
    private List<ProjectData> projectList;
    private ProjectData projectDetail;
    private Context context;//接cookie用


    public static final int STATUS_SUCCESS = 100;
    public static final int STATUS_FAILED = 101;//发布项目他返回的code可能是不成功的那个，暂且写着
    public static final int STATUS_NO_INTERNET = 0;
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
        this.context=context;


        OkHttpClient receiver = new OkHttpClient.Builder()
                .addInterceptor(new ReceivedCookiesInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(receiver)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api userApi = retrofit.create(Api.class);
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

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("username", account);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<InfoUser> dataCall = userApi.register(jsonObject);
        jsonObject=null;

        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(@NonNull Call<InfoUser> call, @NonNull Response<InfoUser> response) {
                InfoUser info = response.body();
                if (info == null) activity.userRegister(STATUS_NO_INTERNET);
                else if(info.getData()==null) activity.userRegister(STATUS_ACCOUNT_ALREADY_EXIST);
                else {
                  /*  SPPresenter.accordAccount(context, account);
                    SPPresenter.accordPassword(context, password);
                    SPPresenter.accordLoggedStatus(context, true);
                    user.setUsername(account);
                    user.setPassword(password);*/
                    activity.userRegister(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(@NonNull Call<InfoUser> call, @NonNull Throwable throwable) {
                activity.userRegister(STATUS_NO_INTERNET);
            }
        });
    }

    //做了无数据返回时给AllBriefProject赋一个new出来的值
    public void fetchAllBriefProject(){
        OkHttpClient adder = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(adder)
                .build();
        Api api = retrofit.create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<InfoShowAllProject> dataCall = api.getAllProjectForUser(1,0) ;//拿取所有数据

        dataCall.enqueue(new Callback<InfoShowAllProject>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoShowAllProject> call, Response<InfoShowAllProject> response) {
                InfoShowAllProject info=response.body();
                if(info==null){
                    projectList =new ArrayList<>();
                    activity.projectListResult(STATUS_NO_INTERNET);
                } else if (info.getData()==null) {
                    projectList =new ArrayList<>();
                    activity.projectListResult(STATUS_NO_DATA);
                } else{
                    projectList =info.getData().getList();
                    activity.projectListResult(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoShowAllProject> call, Throwable t) {
                projectList =new ArrayList<>();
                activity.projectListResult(STATUS_NO_INTERNET);
            }
        });
    }


   //做了无数据返回时给ProjectDetail赋一个new出来的值
    public void fetchProjectDetail(int projectId){
        OkHttpClient adder = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(adder)
                .build();
        Api api = retrofit.create(Api.class);
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
        OkHttpClient adder = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(adder)
                .build();
        Api api = retrofit.create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<InfoProjectList> dataCall = api.getSelfProjects(user.getUserId());
        dataCall.enqueue(new Callback<InfoProjectList>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoProjectList> call, Response<InfoProjectList> response) {
                InfoProjectList info= response.body();
                if(info==null){
                    projectList=new ArrayList<>();
                    activity.projectListResult(STATUS_NO_INTERNET);
                }else if (info.getData()==null) {
                    projectList =new ArrayList<>();
                    activity.projectListResult(STATUS_NO_DATA);
                } else{
                    projectList=info.getData();
                    activity.projectListResult(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoProjectList> call, Throwable t) {
                projectList=new ArrayList<>();
                activity.projectListResult(STATUS_NO_INTERNET);
            }
        });
    }
    //获取自己监控的项目
    public void fetchMonitorProjects(){
        OkHttpClient adder = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(adder)
                .build();
        Api api = retrofit.create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<InfoProjectList> dataCall = api.getHaveMonitorProjects(user.getUserId());
        dataCall.enqueue(new Callback<InfoProjectList>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoProjectList> call, Response<InfoProjectList> response) {
                InfoProjectList info= response.body();
                if(info==null){
                    projectList=new ArrayList<>();
                    activity.projectListResult(STATUS_NO_INTERNET);
                }else if (info.getData()==null) {
                    projectList =new ArrayList<>();
                    activity.projectListResult(STATUS_NO_DATA);
                } else{
                    projectList=info.getData();
                    activity.projectListResult(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoProjectList> call, Throwable t) {
                projectList=new ArrayList<>();
                activity.projectListResult(STATUS_NO_INTERNET);
            }
        });
    }
    //获取正在申请的项目
    public void fetchApplyingProjects(){
        OkHttpClient adder = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(adder)
                .build();
        Api api = retrofit.create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        Call<InfoProjectList> dataCall = api.getApplyingProject(user.getUserId());
        dataCall.enqueue(new Callback<InfoProjectList>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoProjectList> call, Response<InfoProjectList> response) {
                InfoProjectList info= response.body();
                if(info==null){
                    projectList=new ArrayList<>();
                    activity.projectListResult(STATUS_NO_INTERNET);
                } else if (info.getData()==null) {
                    projectList =new ArrayList<>();
                    activity.projectListResult(STATUS_NO_DATA);
                } else{
                    projectList=info.getData();
                    activity.projectListResult(STATUS_SUCCESS);
                }
            }

            @Override
            public void onFailure(Call<InfoProjectList> call, Throwable t) {
                projectList=new ArrayList<>();
                activity.projectListResult(STATUS_NO_INTERNET);
            }
        });
    }
    //自己发布项目
    public void pushProject(String projectName,String description,int userId,String projectUrl,String projectPassword){
        OkHttpClient adder = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(adder)
                .build();
        Api api = retrofit.create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);


        JSONObject jsonObject = new JSONObject();
        //项目名、项目描述、发布者id、项目url、项目口令
        try {
            jsonObject.put("projectName", projectName);
            jsonObject.put("description", description);
            jsonObject.put("userId", userId);
            jsonObject.put("projectUrl", projectUrl);
            jsonObject.put("projectPassword", projectPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<InfoUser> dataCall=api.publishProject(jsonObject);
        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                InfoUser infoUser=response.body();
                if(infoUser==null){
                    activity.projectPublishResult(STATUS_NO_INTERNET);
                } else if (infoUser.getCode()==0) {
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
        OkHttpClient adder = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor(context))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(adder)
                .build();
        Api api = retrofit.create(Api.class);
        Log.d(TAG, "baseUrl = " + baseUrl);

        //用户id、项目id
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("userId", userId);
            jsonObject.put("projectId",projectId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<InfoUser> dataCall=api.publishProject(jsonObject);

        dataCall.enqueue(new Callback<InfoUser>() {
            UserDataShowInterface activity = UserPresenter.this.activity;
            @Override
            public void onResponse(Call<InfoUser> call, Response<InfoUser> response) {
                InfoUser info=response.body();
                if(info==null){
                    activity.applyMonitorPermission(STATUS_NO_INTERNET);
                }else if(info.getCode()==0){
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
    public int getUserId(){
        return user.getUserId();
    }

    //先调用相应接口再调用这个方法
    public ProjectData getProjectDetail(){
        return projectDetail;
    }

    //先调用相应接口再调用这个方法
    public List<ProjectData> getProjectList(){
        return projectList;
    }



}
class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;
    public ReceivedCookiesInterceptor(Context context) {
        super();
        this.context=context;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

            HashSet<String> cookies = new HashSet<>();
            for(String header: originalResponse.headers("Set-Cookie"))
            {
                Log.i("ReceivedCookiesInterceptor", "拦截的cookie是："+header);
                cookies.add(header);
            }
            if (context!=null) {
                //保存的sharepreference文件名为cookieData
                SharedPreferences sharedPreferences = context.getSharedPreferences("cookieData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("cookie", cookies);
                editor.commit();
            }
        }

        return originalResponse;
    }
}
class AddCookiesInterceptor implements Interceptor {
    private Context context;

    public AddCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        if (context!=null) {
            HashSet<String> perferences = (HashSet) context.getSharedPreferences("cookieData", Context.MODE_PRIVATE).getStringSet("cookie", null);
            if (perferences != null) {
                for (String cookie : perferences) {
                    builder.addHeader("Cookie", cookie);
                }
            }
        }
        return chain.proceed(builder.build());
    }
}


