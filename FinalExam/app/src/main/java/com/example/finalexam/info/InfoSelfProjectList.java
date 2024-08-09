package com.example.finalexam.info;

import com.example.finalexam.model.ProjectData;
import com.example.finalexam.model.project.SelfProject;

import java.util.List;

public class InfoSelfProjectList {
    private int code;
    private String msg;
    private List<SelfProject> data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<SelfProject> getData() {
        return data;
    }
}
