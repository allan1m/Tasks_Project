package com.cs246.cleaningtasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class subtask_adapter extends RecyclerView.Adapter<subtask_adapter.MyViewHolder> {
    private ArrayList<SubTask> subTaskList;


    public subtask_adapter(ArrayList<SubTask> subTaskList){
        this.subTaskList = subTaskList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private CheckBox subTaskCheckBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            subTaskCheckBox = itemView.findViewById(R.id.checkBox);
        }


    }

    @NonNull
    @Override
    public subtask_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtask_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull subtask_adapter.MyViewHolder holder, int position) {
        String subTask = subTaskList.get(position).getSubTask();
        holder.subTaskCheckBox.setText(subTask);
    }

    @Override
    public int getItemCount() {
        return subTaskList.size();
    }
}
