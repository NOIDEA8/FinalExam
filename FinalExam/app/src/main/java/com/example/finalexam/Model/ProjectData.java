package com.example.finalexam.Model;

import java.util.List;

public class ProjectData {
    private String name;
    private String url;
    private String Introduction;
    private int totalVisit;
    private int dayVisit;
    private List<UserData> releaserList;
    private List<UserData> monitorList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(String introduction) {
        Introduction = introduction;
    }

    public int getTotalVisit() {
        return totalVisit;
    }

    public int getDayVisit() {
        return dayVisit;
    }

    public List<UserData> getReleaserList() {
        return releaserList;
    }

    public void setReleaserList(List<UserData> releaserList) {
        this.releaserList = releaserList;
    }

    public List<UserData> getMonitorList() {
        return monitorList;
    }

    public void setMonitorList(List<UserData> monitorList) {
        this.monitorList = monitorList;
    }
}
