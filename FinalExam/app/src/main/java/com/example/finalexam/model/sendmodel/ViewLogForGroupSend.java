package com.example.finalexam.model.sendmodel;

public class ViewLogForGroupSend {
    private int groupType;
    private int pageSize;
    private int page;
    private int projectId;
    private int logType;//日志类型(0异常/1其他包括性能，正常日志/2后台自定义日志)

    public ViewLogForGroupSend(int groupType, int pageSize, int page, int projectId, int logType) {
        this.groupType = groupType;
        this.pageSize = pageSize;
        this.page = page;
        this.projectId = projectId;
        this.logType = logType;
    }
    public ViewLogForGroupSend(int groupType ,int logType,int projectId) {
        this.groupType = groupType;
        this.projectId = projectId;
        this.logType = logType;
    }
}
