package com.example.finalexam.info.data;

import com.example.finalexam.model.ProjectData;


import java.util.List;

public class AllBriefProject {//用来适应返回的不同data内容而产生
    private int total;
    private List<ProjectData> data;

    public int getTotal() {
        return total;
    }

    public List<ProjectData> getData() {
        return data;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setData(List<ProjectData> data) {
        this.data = data;
    }
}
