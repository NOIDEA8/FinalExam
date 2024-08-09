package com.example.finalexam.model;

public class LogData {
    private String illegalRequest;
    private String illegalRequestTIme;
    private String ipAddress;
    private int userId;
    private String operate;
    private String operateTime;
    private String operateType;
    private String logType;
    private String logTime;
    private String logInfo;
    private String date;
    private int totalVisit;//总访问量
    private int visits;//日访问量
    private int errorNumber;
    private float errorRate;
    private int frontErrorNumber;//前端错误数量
    private int backErrorNumber;
    private int mobileErrorNumber;
    private int description;//项目操作描述（项目发布或者更新的描述）
}
