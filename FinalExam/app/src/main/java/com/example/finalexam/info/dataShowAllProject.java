package com.example.finalexam.info;

import com.example.finalexam.model.ProjectData;

import java.util.List;

public class dataShowAllProject {//用来适应返回的不同data内容而产生
    private int total;
    private List<ProjectData> list;

    public int getTotal() {
        return total;
    }

    public List<ProjectData> getList() {
        return list;
    }
}
