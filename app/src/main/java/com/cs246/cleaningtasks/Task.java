package com.cs246.cleaningtasks;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * <h1>TASK</h1>
 * <p>Tasks is a composite of local variables and SubTask.class;
 * it holds a list of SubTasks and other task information</p>
 * <p>Task is parcelable (can be added to intents without
 * serializing into json).</p>
 */
public class Task implements Parcelable {
    //Global Vars
    private String mainTaskTitle;
    private String assignee;
    private String mainTaskDescription;
    private ArrayList<SubTask> subTaskList;

    /**
     * <h2>Task default constructor</h2>
     * <p>Sets all values to empty and creates a default ArrayList</p>
     */
    public Task() {
        this.mainTaskTitle = "";
        this.assignee = "";
        this.mainTaskDescription = "";
        this.subTaskList = new ArrayList<>();
    }

    /**
     * <h2>Task non-default constructor</h2>
     *  <p>It creates a task from a title, asignee, and description</p>
     *  <p>It creates a default (empty) ArrayList</p>
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
     * <h2>Task copy-constructor</h2>
     * <p>It creates a task by copying a provided, existing Task</p>
     * @param copiedTask
     */
    public Task(Task copiedTask) {
        this.assignee = copiedTask.getAssignee();
        this.mainTaskDescription = copiedTask.getMainTaskDescription();
        this.mainTaskTitle = copiedTask.getMainTaskTitle();
        this.subTaskList = copiedTask.getSubTaskList();
    }

    /**
     * <h2>Task parcelable-constructor</h2>
     * <p>receives the parcel and reads it into class variables</p>
     * @param in
     */
    protected Task(Parcel in) {
        mainTaskTitle = in.readString();
        assignee = in.readString();
        mainTaskDescription = in.readString();
        subTaskList = in.createTypedArrayList(SubTask.CREATOR);
    }

    /**
     * <h2>Task parcel CREATOR</h2>
     * <p>invokes parcelable constructor to reconstruct task from parcel</p>
     * <p>This is Run on reception of parcel</p>
     */
    public static final Creator<Task> CREATOR = new Creator<Task>() {
        //invokes/delegates parcelable constructor
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        //necessary override for parcel creation
        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    /**
     * <h2>mainTaskTitle Getter</h2>
     * @return String
     */
    public String getMainTaskTitle() {
        return mainTaskTitle;
    }

    /**
     * <h2>assignee Getter</h2>
     * @return String
     */
    public String getAssignee() {
        return assignee;
    }

    /**
     * <h2>mainTaskDescription Getter</h2>
     * @return String
     */
    public String getMainTaskDescription() {
        return mainTaskDescription;
    }

    /**
     * <h2>AddSubTask</h2>
     * <p>Adds a task to the subTaskList</p>
     * @param subTask
     */
    public void addSubTask(String subTask, Boolean isChecked){
        subTaskList.add(new SubTask(subTask, isChecked));
    }

    /**
     * <h2>subTaskList Getter</h2>
     * @return ArrayList<SubTask>
     */
    public ArrayList<SubTask> getSubTaskList() {
        return subTaskList;
    }

    /**
     * <h2>subTaskList Setter</h2>
     * @param subTaskList2
     */
    public void setSubTaskList(ArrayList<SubTask> subTaskList2) {
        //clears current list
        this.subTaskList.clear();

        //sets local list to inbound list's values
        for(SubTask i: subTaskList2) {
            this.subTaskList.add(i);
        }

    }

    /**
     * <h2>Calculate Completed</h2>
     * <p>Calculates how many of the subTasks in
     * subTaskList are marked true (checkedStatus)</p>
     * @return String
     */
    public String calculateCompleted(){
        //counter for completed tasks
        int completed = 0;

        //iterates through the list and counts completed
        for(SubTask i: subTaskList){
            if(i.getCheckedStatus() == true){
                completed = completed + 1;
            }
        }

        //this string will be assigned to the widget that
        //   indicates completed status in the layout
        String completedTotal = "Completed: " +
                Integer.toString(completed) +
                " / " +
                Integer.toString( (subTaskList.size()) );

        return completedTotal;
    }


    /**
     * <h2>DescribeContents</h2>
     * <p>Automatically generated as part of parcelable</p>
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * <h2>Task writeToParcel</h2>
     * <p>This method creates the parcel to be sent when passing Task into intentions</p>
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
