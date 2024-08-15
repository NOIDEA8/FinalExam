package com.example.finalexam.model.sendmodel;

public class OffsetSend {
    private String data;
    private String methodName;

    public OffsetSend(String userId, String methodName) {
        this.data = userId;
        this.methodName = methodName;
    }

    public String getUserId() {
        return data;
    }

    public void setUserId(String userId) {
        this.data = userId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
