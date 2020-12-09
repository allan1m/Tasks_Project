package com.cs246.cleaningtasks;

import android.os.Parcel;
import android.os.Parcelable;

public class SubTask implements Parcelable {
    private String subTask;
    private Boolean checkedStatus;

    public SubTask() {


    }
    /**
     * Constructor
     * @param subTask
     */
    public SubTask(String subTask, Boolean isChecked) {
        this.subTask = subTask;
        this.checkedStatus = isChecked;
    }

    protected SubTask(Parcel in) {
        subTask = in.readString();
        byte tmpIsChecked = in.readByte();
        checkedStatus = tmpIsChecked == 0 ? null : tmpIsChecked == 1;
    }

    public static final Creator<SubTask> CREATOR = new Creator<SubTask>() {
        @Override
        public SubTask createFromParcel(Parcel in) {
            return new SubTask(in);
        }

        @Override
        public SubTask[] newArray(int size) {
            return new SubTask[size];
        }
    };

    public void setIsChecked(Boolean isChecked){
        this.checkedStatus = isChecked;
    }

    public Boolean getCheckedStatus(){
        return checkedStatus;
    }

    /**
     * subTaskList Getter
     * @return
     */
    public String getSubTask() {
        return subTask;
    }

    /**
     * subTaskList Setter
     * @param subTask
     */
    public void setSubTask(String subTask) {
        this.subTask = subTask;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subTask);
        dest.writeByte((byte) (checkedStatus == null ? 0 : checkedStatus ? 1 : 2));
    }
}
