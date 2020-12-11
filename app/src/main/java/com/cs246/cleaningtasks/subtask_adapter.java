package com.cs246.cleaningtasks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * <h1>SUBTASK_ADAPTER</h1>
 * <p>The Adapter populates the RecyclerView</p>
 */
public class subtask_adapter extends RecyclerView.Adapter<subtask_adapter.MyViewHolder> {
    //Global Vars
    private ArrayList<SubTask> subTaskList;
    private ClickListener clickListener;

    //Debugging Tag
    private static final String TAG = "subtask_adapter";

    /**
     * <h1>subtask_adapter Non-Default Constructor</h1>
     * <p>Creates the adapter and receives the subTasklist and public interface
     *  ClickListener</p>
     * @param subTaskList
     * @param clickListener
     */
    public subtask_adapter(ArrayList<SubTask> subTaskList, ClickListener clickListener){
        this.subTaskList = subTaskList;
        this.clickListener = clickListener;
    }

    /**
     * <h2>MyViewHolder</h2>
     * <p>The ViewHolder is what holds each individual card's value to be recycled</p>
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CheckBox subTaskCheckBox;
        private ImageView delete_icon;

        //This interface is used to be implemented by TaskView in order to use local
        //   variable position for each individual bound viewholder. This way a click
        //   detected can receive the position of the individual card clicked
        ClickListener clickListener;

        /**
         * <h3>MyViewHolder non-default constructor</h3>
         * <p>It sets variables and onClickListeners</p>
         * @param itemView
         * @param clickListener
         */
        public MyViewHolder(@NonNull View itemView, ClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;

            //Widgets association
            subTaskCheckBox = itemView.findViewById(R.id.checkBox);
            delete_icon = itemView.findViewById(R.id.subtask_delete);

            //listener for the delete button
            delete_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){

                        //calls for the interface's onDeleteClick which will
                        //   be overwritten in TaskView
                        clickListener.onDeleteClick(position);
                    }
                }
            });

            //listener for the checkBox
            subTaskCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //If it's already checked, uncheck; and vice-versa
                    if (subTaskCheckBox.isChecked()) {
                        //calls for the interface's onCheckedClick which will
                        //   be overwritten in TaskView
                        clickListener.onCheckedClick(getAdapterPosition(), true);
                    }else{
                        //calls for the interface's onCheckedClick which will
                        //   be overwritten in TaskView
                        clickListener.onCheckedClick(getAdapterPosition(), false);
                    }
                }
            });
        }

        /**
         * <h3>Card onCLick</h3>
         * <p>this sends the position of the current card to be used
         * to delete the appropriate subTask</p>
         * @param v
         */
        @Override
        public void onClick(View v) {
            clickListener.onDeleteClick(getAdapterPosition());
        }
    }

    /**
     * <h2>OnCreateViewHolder</h2>
     * <p>When created the Adapter viewHolder loads the individual card layout to reuse</p>
     */
    @NonNull
    @Override
    public subtask_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //When created the viewHolder will be inflated (or populated) into the layout subtask_layout
        //   which represents each individual card
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtask_layout, parent, false);

        return new MyViewHolder(itemView, clickListener);
    }

    /**
     * <h2>OnBindViewHolder</h2>
     *  <p>Each taskList position will be bound to a card in the Recyclerview/p>
     */
    @Override
    public void onBindViewHolder(@NonNull subtask_adapter.MyViewHolder holder, int position) {
        String subTask = subTaskList.get(position).getSubTask();
        Boolean subTaskIsChecked = subTaskList.get(position).getCheckedStatus();
        holder.subTaskCheckBox.setChecked(subTaskIsChecked);
        holder.subTaskCheckBox.setText(subTask);
    }

    /**
     * <h2>getItemCount</h2>
     * <p>Gives the recyclerview a size to iterate through;
     * the size returned is the size of the subTasklist</p>
     */
    @Override
    public int getItemCount() {
        return subTaskList.size();
    }

    /**
     * <h2>Public Interface: ClickListener</h2>
     * <p>The methods in this interface are overwritten in TaskView
     * but emanate from here; the purpose is to pass position information
     * from each bound viewHolder and into TaskView to propagate changes in
     * TaskView.Java with information from viewholders</p>
     */
    public interface ClickListener{
        void onDeleteClick(int position);
        void onCheckedClick(int position, Boolean isChecked);
    }
}
