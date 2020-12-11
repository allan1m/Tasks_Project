package com.cs246.cleaningtasks;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <h1>SUBTASK</h1>
 * <p>SubTasks works like a task but includes a boolean
 * to hold the checked status of the checkBox</p>
 * <p>SubTask is parcelable (can be added to intents without
 * serializing into json).</p>
 */
public class SubTask implements Parcelable {
    //Global Variables
    private String subTask;
    private Boolean checkedStatus;

    /**
     * <h2>SubTask default-constructor</h2>
     * <p>left blank with a note to implement features in next release</p>
     */
    public SubTask() {
        //Intentionally left blank
        // TODO: 12/10/2020 Default creator to instantiate pre-sets in future releases
    }

    /**
     * <h2>SubTask non-default constructor</h2>
     * <p>creates a subtask based on a description and a boolean</p>
     * @param subTask
     */
    public SubTask(String subTask, Boolean isChecked) {
        this.subTask = subTask;
        this.checkedStatus = isChecked;
    }

    /**
     * <h2>SubTask parcelable-constructor</h2>
     * <p>receives the parcel and reads it into class variables</p>
     * @param in
     */
    protected SubTask(Parcel in) {
        subTask = in.readString();
        byte tmpIsChecked = in.readByte();
        checkedStatus = tmpIsChecked == 0 ? null : tmpIsChecked == 1;
    }

    /**
     * <h2>SubTask parcel CREATOR</h2>
     * <p>invokes parcelable constructor to reconstruct subtask from parcel</p>
     * <p>This is Run on reception of parcel</p>
     */
    public static final Creator<SubTask> CREATOR = new Creator<SubTask>() {
        //invokes/delegates parcelable constructor
        @Override
        public SubTask createFromParcel(Parcel in) {
            return new SubTask(in);
        }

        //necessary override for parcel creation
        @Override
        public SubTask[] newArray(int size) {
            return new SubTask[size];
        }
    };

    /**
     * <h2>SubTask setIsChecked</h2>
     * <p>Setter</p>
     * <p>Mark checkedStatus true or false</p>
     * <p>Necessary for checkbox function in layout</p>
     * @param isChecked
     */
    public void setIsChecked(Boolean isChecked){
        this.checkedStatus = isChecked;
    }

    /**
     * <h2>SubTask getCheckedStatus</h2>
     * <p>Getter</p>
     * <p>Retrieves checkedStatus true or false</p>
     * <p>Necessary for checkbox function in layout</p>
     * @return
     */
    public Boolean getCheckedStatus(){
        return checkedStatus;
    }

    /**
     * <h2>SubTask getSubTask</h2>
     * <p>Getter</p>
     * <p>Retrieves subTask (string)</p>
     * @return
     */
    public String getSubTask() {
        return subTask;
    }

    /**
     * <h2>SubTask setSubTask</h2>
     * <p>Getter</p>
     * <p>Sets subTask (string)</p>
     * <p>not used but necessary for future</p>
     * @param subTask
     */
    public void setSubTask(String subTask) {
        this.subTask = subTask;
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
     * <h2>SubTask writeToParcel</h2>
     * <p>This method creates the parcel to be sent when passing SubTask into intentions</p>
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subTask);
        dest.writeByte((byte) (checkedStatus == null ? 0 : checkedStatus ? 1 : 2));
    }
}
