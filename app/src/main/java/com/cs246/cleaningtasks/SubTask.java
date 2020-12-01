package com.cs246.cleaningtasks;

import java.util.ArrayList;

public class SubTask {
    private ArrayList<String> subTaskList;

    /**
     * Constructor
     * @param subTaskList
     */
    public SubTask(ArrayList<String> subTaskList) {
        this.subTaskList = subTaskList;
    }

    /**
     * AddTask: Adds a task to the subTaskList
     * @param subTask
     */
    void addSubTask(String subTask){
        subTaskList.add(subTask);
    }

    /**
     * subTaskList Getter
     * @return
     */
    public ArrayList<String> getSubTaskList() {
        return subTaskList;
    }

    /**
     * subTaskList Setter
     * @param subTaskList
     */
    public void setSubTaskList(ArrayList<String> subTaskList) {
        this.subTaskList = subTaskList;
    }
}
