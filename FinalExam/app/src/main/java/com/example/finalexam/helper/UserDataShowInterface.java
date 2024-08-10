package com.example.finalexam.helper;



public interface UserDataShowInterface {
    //TODO 以下四个方法均为回调方法，如果需要使用，可以查看UserPresenter

    void applyMonitorPermission(int STATUS);//申请监管权限
    void checkMonitorResult(int STATUS);//询问是否有该项目的权限
    void freeze(int STATUS);//冻结（项目和用户）

    void projectPublishResult(int STATUS);//发布项目

    void briefProjectList(int STATUS);//所有项目

    void selfProjectList(int STATUS);//自己发布的项目

    void monitorProjectList(int STATUS);//自己监管的项目

    void applyingMonitorProjectList(int STATUS);//申请项目监控的记录
    void applyingProjectList(int STATUS);//申请项目发布或更新的记录

    void projectDetail(int STATUS);//项目详细

    void updateProject(int STATUS);//更新项目
    void cancelMonitor(int STATUS);//撤销监管
    void deleteProject(int STATUS);//删除项目
    void freezeOrNotProjectList(int STATUS);//获取冻结或未被冻结的项目
    void applyOrNotProjectList(int STATUS);//获取审核或未审核的项目

    void userLog(int STATUS);//登录

    void userRegister(int STATUS);//注册

    void monitorUserListResult(int STATUS);//获取监管者名单

    void userDetail(int STATUS);//用户详情

    void verify(int STATUS);//审核项目
    void application(int STATUS);//获取我收到的申请
    void attackServerLogList(int STATUS);//获取攻击服务器日志
    void allUserOperationLogList(int STATUS);//获取所有用户操作日志
    void logDataListByGroup(int STATUS);//按照前端后台移动这样子拿的一个项目的日志
    void projectPresentationDateOneWeek(int STATUS);//最近一周内的项目的访问数据和报错统计
    void ViewProjectOperateLog(int STATUS);//查看项目操作日志(包括项目发布，更新日志)
    void increaseView(int STATUS);//增加项目的访问次数

}
