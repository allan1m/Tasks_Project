package com.cs246.cleaningtasks;

import java.util.ArrayList;

public class Board {

    private ArrayList<Task> taskList;

    /**
     * Constructor
     */
    public Board() {

    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public void addTask(Task task ){
        taskList.add(task);
    }

    public void deleteTask(){

    }
}
