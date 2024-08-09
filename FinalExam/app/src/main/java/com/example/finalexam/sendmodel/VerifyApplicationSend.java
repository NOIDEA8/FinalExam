package com.example.finalexam.sendmodel;

public class VerifyApplicationSend {
    private int projectId;
    private int reviewResult;
    private String rejectResason;

    public VerifyApplicationSend(int projectId, int reviewResult, String rejectResason) {
        this.projectId = projectId;
        this.reviewResult = reviewResult;
        this.rejectResason = rejectResason;
    }
}
