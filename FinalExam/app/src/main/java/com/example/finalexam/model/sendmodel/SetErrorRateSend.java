package com.example.finalexam.model.sendmodel;

public class SetErrorRateSend {
    private int projectId;
    private double errorRate;

    public SetErrorRateSend(int projectId, double errorRate) {
        this.projectId = projectId;
        this.errorRate = errorRate;
    }
}
