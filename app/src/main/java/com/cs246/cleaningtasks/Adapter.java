package com.cs246.cleaningtasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 *  The Adapter populates the RecyclerView
 */
public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private ArrayList<Task> taskList;
    private OnTouchListener onTouchListener;


    public Adapter(ArrayList<Task> taskList, OnTouchListener onTouchListener){
        this.onTouchListener = onTouchListener;
        this.taskList = taskList;

    }

    /**
     *  The ViewHolder is what holds each individual card's value to be recycled
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mainTaskTitle;
        private TextView mainDescription;
        private TextView asignee;
        private TextView completedStatus;
        public ImageView delete_icon;
        OnTouchListener onTouchListener;


        public MyViewHolder(final View view, OnTouchListener onTouchListener){
            super(view);

            mainTaskTitle = view.findViewById(R.id.mainTaskTitle);
            mainDescription = view.findViewById(R.id.mainDescription);
            asignee = view.findViewById(R.id.asignee);
            completedStatus = view.findViewById(R.id.completed);
            delete_icon = view.findViewById(R.id.image_delete);
            this.onTouchListener = onTouchListener;


            //itemView.setOnClickListener(this);
            view.setOnClickListener(this);

            delete_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        onTouchListener.onDeleteClick(position);
                    }
                }
            });


        }


        @Override
        public void onClick(View v) {

            onTouchListener.onCardClick(getAdapterPosition());
        }
    }

    /**
     *  When created the Adapter viewHolder loads the individual card layout to reuse
     */
    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(itemView, onTouchListener);
    }

    /**
     *  Each Card is bound to a position in the list
     */
    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        String mainTaskDescrpt = taskList.get(position).getMainTaskDescription();
        String mainTaskTitle= taskList.get(position).getMainTaskTitle();
        String assignee = taskList.get(position).getAssignee();
        String completedStat = taskList.get(position).calculateCompleted();

        //double completedperct = taskList.get(position).getCompletedPercentage();

        holder.mainTaskTitle.setText(mainTaskTitle);
        holder.asignee.setText(assignee);
        holder.mainDescription.setText(mainTaskDescrpt);
        holder.completedStatus.setText(completedStat);
        //holder.completed.setText(completedperct);
    }

    /**
     *  Gives the recyclerview a size to iterate through
     */
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public interface OnTouchListener{
        void onCardClick(int position);
        void onDeleteClick(int position);
    }
}


