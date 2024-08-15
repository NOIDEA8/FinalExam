package com.example.finalexam.model.sendmodel;

public class OffsetSend {
    private int userId;
    private String methodName;

    public OffsetSend(int userId, String methodName) {
        this.userId = userId;
        this.methodName = methodName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
