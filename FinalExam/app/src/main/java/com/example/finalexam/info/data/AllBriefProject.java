package com.example.finalexam.info.data;

import com.example.finalexam.model.project.BriefProject;

import java.util.List;

public class AllBriefProject {//用来适应返回的不同data内容而产生
    private int total;
    private List<BriefProject> list;

    public int getTotal() {
        return total;
    }

    public List<BriefProject> getList() {
        return list;
    }
}
