package com.example.finalexam.info;

import com.example.finalexam.model.project.MonitorProject;


import java.util.List;

public class InfoMonitorProjectList {
    private int code;
    private String msg;
    private List<MonitorProject> data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<MonitorProject> getData() {
        return data;
    }
}
