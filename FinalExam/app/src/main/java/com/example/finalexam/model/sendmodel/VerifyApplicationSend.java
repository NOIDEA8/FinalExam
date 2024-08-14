package com.example.finalexam.model.sendmodel;

public class VerifyApplicationSend {
    private int applicationId;
    private int status;
    private String rejectReason;

    public VerifyApplicationSend(int applicationId, int status, String rejectReason) {
        this.applicationId = applicationId;
        this.status = status;
        this.rejectReason = rejectReason;
    }
}
