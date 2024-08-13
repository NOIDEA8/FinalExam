package com.example.finalexam.model.sendmodel;

public class ShowDetailedLogSend {
    private int groupType;
    private int logId;
    private int logType;

    public ShowDetailedLogSend(int groupType, int logId, int logType) {
        this.groupType = groupType;
        this.logId = logId;
        this.logType = logType;
    }
}
