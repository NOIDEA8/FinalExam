package com.example.finalexam.model;

public class UserData {



    private String enabled;//0未冻结、1冻结?
    private String isOnline;
    private String password;
    private String createTime;
    private String username;//这个就是账号

    private int userId;//操作必要




    public String getEnabled() {
        return enabled;
    }

    public String getOnline() {
        return isOnline;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
