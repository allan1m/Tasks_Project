package com.cs246.cleaningtasks;

import java.util.ArrayList;

public class Task {
    private String mainTaskTitle;

    private String assignee;

    private String mainTaskDescription;

    private ArrayList<String> subTaskList;



    /**
     *  The task is a part of the list held in the Board. Each task has 4 quickview
     *  items
     * @param mainTaskTitle
     * @param assignee
     * @param mainTaskDescription
     */
    public Task(String mainTaskTitle, String assignee, String mainTaskDescription) {
        this.assignee = assignee;
        this.mainTaskDescription = mainTaskDescription;
        this.mainTaskTitle = mainTaskTitle;
    }

    public String getMainTaskTitle() {
        return mainTaskTitle;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getMainTaskDescription() {
        return mainTaskDescription;
    }

    /**
     * AddTask: Adds a task to the subTaskList
     * @param subTask
     */
    public void addSubTask(String subTask){
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
