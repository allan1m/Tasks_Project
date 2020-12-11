package com.cs246.cleaningtasks;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/**
 * <h1>ADAPTER</h1>
 * <p>The Adapter populates the RecyclerView</p>
 */
public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    //Global Vars
    private ArrayList<Task> taskList;
    private OnTouchListener onTouchListener;
    //Debugging Tag
    private static final String TAG = "Adapter";

    /**
     * <h1>Adapter Non-Default Constructor</h1>
     * <p>Creates the adapter and receives the tasklist and public interface
     *  OnTouchListener</p>
     * @param taskList
     * @param onTouchListener
     */
    public Adapter(ArrayList<Task> taskList, OnTouchListener onTouchListener){
        this.onTouchListener = onTouchListener;
        this.taskList = taskList;

    }

    /**
     * <h2>MyViewHolder</h2>
     * <p>The ViewHolder is what holds each individual card's value to be recycled</p>
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mainTaskTitle;
        private TextView mainDescription;
        private TextView asignee;
        private TextView completedStatus;
        public ImageView delete_icon;
        //This interface is used to be implemented by TaskBoard in order to use local
        //   variable position for each individual bound viewholder. This way a click
        //   detected can receive the position of the individual card clicked
        OnTouchListener onTouchListener;

        /**
         * <h3>MyViewHolder non-default constructor</h3>
         * <p>It sets variables and onClickListeners</p>
         * @param view
         * @param onTouchListener
         */
        public MyViewHolder(final View view, OnTouchListener onTouchListener){
            super(view);

            //Widgets association
            mainTaskTitle = view.findViewById(R.id.mainTaskTitle);
            mainDescription = view.findViewById(R.id.mainDescription);
            asignee = view.findViewById(R.id.asignee);
            completedStatus = view.findViewById(R.id.completed);
            delete_icon = view.findViewById(R.id.image_delete);
            this.onTouchListener = onTouchListener;

            //This listens for a click in the card in general, not specific buttons
            view.setOnClickListener(this);

            //listener for the delete button
            delete_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){

                        //calls for the interface's onDeleteClick which will
                        //   be overwritten in TaskBoard
                        onTouchListener.onDeleteClick(position);
                    }
                }
            });


        }

        /**
         * <h3>Card onCLick</h3>
         * <p>this sends the position of the current card to be used
         * to open full task view when a quickview card is clicked/touched</p>
         * @param v
         */
        @Override
        public void onClick(View v) {
            onTouchListener.onCardClick(getAdapterPosition());
        }
    }

    /**
     * <h2>OnCreateViewHolder</h2>
     * <p>When created the Adapter viewHolder loads the individual card layout to reuse</p>
     */
    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //When created the viewHolder will be inflated (or populated) into the layout card_view
        //   which represents each individual card
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

        return new MyViewHolder(itemView, onTouchListener);
    }

    /**
     * <h2>OnBindViewHolder</h2>
     *  <p>Each taskList position will be bound to a card in the Recyclerview/p>
     */
    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        //Variable information received from taskList to be used in Widget info set
        String mainTaskDescrpt = taskList.get(position).getMainTaskDescription();
        String mainTaskTitle= taskList.get(position).getMainTaskTitle();
        String assignee = taskList.get(position).getAssignee();
        String completedStat = taskList.get(position).calculateCompleted();

        //Widget information set from variables
        holder.mainTaskTitle.setText(mainTaskTitle);
        holder.asignee.setText(assignee);
        holder.mainDescription.setText(mainTaskDescrpt);
        holder.completedStatus.setText(completedStat);
    }

    /**
     * <h2>getItemCount</h2>
     * <p>Gives the recyclerview a size to iterate through;
     * the size returned is the size of the tasklist</p>
     */
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    /**
     * <h2>Public Interface: OnTouchListener</h2>
     * <p>The methods in this interface are overwritten in TaskBoard
     * but emanate from here; the purpose is to pass position information
     * from each bound viewHolder and into TaskBoard to propagate changes in
     * TaskBoard.Java with information from viewholders</p>
     */
    public interface OnTouchListener{
        //General Card Touch
        void onCardClick(int position);
        //Delete Icon click
        void onDeleteClick(int position);
    }
}


