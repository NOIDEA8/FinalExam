package com.example.finalexam.model;

public class LogData {
    private String illegalRequest;
    private String illegalRequestTime;
    private String ipAddress;
    private int userId;
    private String operate;
    private String operateTime;
    private String operateType;
    private String logType;
    private String logTime;
    private String logInfo;
    private int logId;
    private String date;
    private int totalVisit;//总访问量
    private int visits;//日访问量
    private int errorNumber;
    private float errorRate;
    private int frontErrorNumber;//前端错误数量
    private int backErrorNumber;
    private int mobileErrorNumber;
    private int description;//项目操作描述（项目发布或者更新的描述）
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIllegalRequest() {
        return illegalRequest;
    }

    public void setIllegalRequest(String illegalRequest) {
        this.illegalRequest = illegalRequest;
    }

    public String getIllegalRequestTime() {
        return illegalRequestTime;
    }

    public void setIllegalRequestTime(String illegalRequestTime) {
        this.illegalRequestTime = illegalRequestTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalVisit() {
        return totalVisit;
    }

    public void setTotalVisit(int totalVisit) {
        this.totalVisit = totalVisit;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public int getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }

    public float getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(float errorRate) {
        this.errorRate = errorRate;
    }

    public int getFrontErrorNumber() {
        return frontErrorNumber;
    }

    public void setFrontErrorNumber(int frontErrorNumber) {
        this.frontErrorNumber = frontErrorNumber;
    }

    public int getBackErrorNumber() {
        return backErrorNumber;
    }

    public void setBackErrorNumber(int backErrorNumber) {
        this.backErrorNumber = backErrorNumber;
    }

    public int getMobileErrorNumber() {
        return mobileErrorNumber;
    }

    public void setMobileErrorNumber(int mobileErrorNumber) {
        this.mobileErrorNumber = mobileErrorNumber;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }
}
