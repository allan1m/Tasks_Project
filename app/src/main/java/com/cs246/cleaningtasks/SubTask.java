package com.cs246.cleaningtasks;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class SubTask implements Parcelable {
    private String subTask;
    private Boolean isChecked;

    public SubTask() {
        this.subTask = "";
        this.isChecked = false;
    }
    /**
     * Constructor
     * @param subTask
     */
    public SubTask(String subTask) {
        this.subTask = subTask;
        this.isChecked = false;
    }

    protected SubTask(Parcel in) {
        subTask = in.readString();
        byte tmpIsChecked = in.readByte();
        isChecked = tmpIsChecked == 0 ? null : tmpIsChecked == 1;
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
        dest.writeByte((byte) (isChecked == null ? 0 : isChecked ? 1 : 2));
    }
}
