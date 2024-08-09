package com.example.finalexam.info;

import com.example.finalexam.info.data.AllApplyingProject;
import com.example.finalexam.model.project.ApplyingProject;

import java.util.List;

public class InfoApplyingProjectList {
    private int code;
    private String msg;
    private AllApplyingProject data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AllApplyingProject getData() {
        return data;
    }

    public void setData(AllApplyingProject data) {
        this.data = data;
    }
}
