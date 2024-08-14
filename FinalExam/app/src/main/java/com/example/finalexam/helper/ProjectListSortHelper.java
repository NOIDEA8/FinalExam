package com.example.finalexam.helper;

import com.example.finalexam.model.ProjectData;

import java.util.ArrayList;
import java.util.List;

public class ProjectListSortHelper {

    public static void sortWithCreator(List<ProjectData> list) {
        if (list == null) return;
        else if (list.size() < 2) return;

        int num = list.size();
        List<ProjectData> tempList = new ArrayList<>();
        //预计复杂度：NlogN
        for (;;) {
            String creator = list.get(0).getCreator();
            for (int i = 0; i < list.size(); i++) {
                ProjectData tempData = list.get(i);
                String tempCreator = tempData.getCreator();
                if(creator == null)
                    tempList.add(tempData);
                else if (creator.equals(tempCreator))
                    tempList.add(tempData);
            }
            list.removeAll(tempList);
            if(list.isEmpty()) break;
        }
        list.addAll(tempList);
    }
}
