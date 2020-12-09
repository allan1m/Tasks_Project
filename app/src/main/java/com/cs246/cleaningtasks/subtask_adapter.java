package com.cs246.cleaningtasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class subtask_adapter extends RecyclerView.Adapter<subtask_adapter.MyViewHolder> {
    private ArrayList<SubTask> subTaskList;
    private ClickListener clickListener;


    public subtask_adapter(ArrayList<SubTask> subTaskList, ClickListener clickListener){
        this.subTaskList = subTaskList;
        this.clickListener = clickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CheckBox subTaskCheckBox;
        private ImageView delete_icon;
        ClickListener clickListener;

        public MyViewHolder(@NonNull View itemView, ClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;

            subTaskCheckBox = itemView.findViewById(R.id.checkBox);
            delete_icon = itemView.findViewById(R.id.subtask_delete);

            delete_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        clickListener.onDeleteClick(position);
                    }
                }
            });

            subTaskCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (subTaskCheckBox.isChecked()) {
                        clickListener.onCheckedClick(getAdapterPosition(), true);
                    }else{
                        clickListener.onCheckedClick(getAdapterPosition(), false);
                    }
                }
            });
        }


        @Override
        public void onClick(View v) {
            clickListener.onDeleteClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public subtask_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtask_layout, parent, false);

        return new MyViewHolder(itemView, clickListener);


    }

    @Override
    public void onBindViewHolder(@NonNull subtask_adapter.MyViewHolder holder, int position) {
        String subTask = subTaskList.get(position).getSubTask();
        Boolean subTaskIsChecked = subTaskList.get(position).getCheckedStatus();
        holder.subTaskCheckBox.setChecked(subTaskIsChecked);
        holder.subTaskCheckBox.setText(subTask);
    }

    @Override
    public int getItemCount() {
        return subTaskList.size();
    }

    public interface ClickListener{
        void onDeleteClick(int position);
        void onCheckedClick(int position, Boolean isChecked);
    }
}
