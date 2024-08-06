package com.example.finalexam.Model;

public class UserData {
    private String msg;
    private String name;
    private String account;
    private String passwords;

    //返回信息
    public String getMsg() {
        return msg;
    }
    //获取账号
    public String getAccount() {
        return account;
    }
    //创建账号
    public void setAccount(String account) {this.account = account;}
    //获取名称
    public String getName() {
        return name;
    }
    //设置名称
    public void setName(String name) {
        this.name = name;
    }
    //重置密码
    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

}
