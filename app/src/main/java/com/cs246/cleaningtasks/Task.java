package com.cs246.cleaningtasks;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Task implements Parcelable {
    private String mainTaskTitle;

    private String assignee;

    private String mainTaskDescription;

    private ArrayList<SubTask> subTaskList;


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
        this.subTaskList = new ArrayList<>();
    }

    /**
     * Parcelable constructor
     * @param in
     */
    protected Task(Parcel in) {
        mainTaskTitle = in.readString();
        assignee = in.readString();
        mainTaskDescription = in.readString();
        subTaskList = in.createTypedArrayList(SubTask.CREATOR);
    }

    /**
     * Parcelable creator
     */
    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

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
        subTaskList.add(new SubTask(subTask));
    }

    /**
     * subTaskList Getter
     * @return
     */
    public ArrayList<SubTask> getSubTaskList() {
        return subTaskList;
    }

    /**
     * subTaskList Setter
     * @param subTaskList
     */
    public void setSubTaskList(ArrayList<SubTask> subTaskList) {
        this.subTaskList = subTaskList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write into parcel for passage between activities
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mainTaskTitle);
        dest.writeString(assignee);
        dest.writeString(mainTaskDescription);
        dest.writeTypedList(subTaskList);
    }
}
