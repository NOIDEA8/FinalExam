package com.example.finalexam.info;

import com.example.finalexam.model.project.BriefProject;


import java.util.List;

public class InfoMonitorProjectList {
    private int code;
    private String msg;
    private List<BriefProject> data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<BriefProject> getData() {
        return data;
    }
}
