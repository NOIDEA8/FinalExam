package com.example.finalexam.Model;

import java.util.List;

public class UserData {
    private String msg;
    private String name="用户";
    private String account;
    private String passwords;
    private List<ProjectData> releasedProject;
    private List<ProjectData> monitoredProject;

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

    public List<ProjectData> getMonitoredProject() {
        return monitoredProject;
    }

    public void setMonitoredProject(List<ProjectData> monitoredProject) {
        this.monitoredProject = monitoredProject;
    }

    public List<ProjectData> getReleasedProject() {
        return releasedProject;
    }

    public void setReleasedProject(List<ProjectData> releasedProject) {
        this.releasedProject = releasedProject;
    }
}
