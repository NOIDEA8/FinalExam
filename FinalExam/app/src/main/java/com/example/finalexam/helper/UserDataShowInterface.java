package com.example.finalexam.helper;



public interface UserDataShowInterface {
    //TODO 以下四个方法均为回调方法，如果需要使用，可以查看UserPresenter

    void applyMonitorPermission(int STATUS);
    void checkMonitorResult(int STATUS);
    void freeze(int STATUS);

    void projectPublishResult(int STATUS);

    void briefProjectList(int STATUS);

    void selfProjectList(int STATUS);

    void monitorProjectList(int STATUS);

    void applyingMonitorProjectList(int STATUS);//申请项目监控的记录
    void applyingProjectList(int STATUS);//申请项目发布或更新的记录

    void projectDetail(int STATUS);

    void updateProject(int STATUS);
    void cancelMonitor(int STATUS);
    void deleteProject(int STATUS);
    void freezeOrNotProjectList(int STATUS);
    void applyOrNotProjectList(int STATUS);

    void userLog(int STATUS);

    void userRegister(int STATUS);

    void userListResult(int STATUS);

    void userDetail(int STATUS);

    void verify(int STATUS);

}
