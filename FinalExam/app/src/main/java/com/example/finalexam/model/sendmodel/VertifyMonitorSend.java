package com.example.finalexam.model.sendmodel;

public class VertifyMonitorSend {
    private int applicationId;
    private int status;
    private String rejectReason;

    public VertifyMonitorSend(int applicationId, int status, String rejectReason) {
        this.applicationId = applicationId;
        this.status = status;
        this.rejectReason = rejectReason;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
