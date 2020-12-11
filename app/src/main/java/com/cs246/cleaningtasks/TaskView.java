package com.cs246.cleaningtasks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/**
 * <h1>TASKVIEW</h1>
 * <p>TaskView holds the logic for the the Task that holds the Recyclerview that holds SubTasks</p>
 * <p>It handles SubTask creation and checkBoxes</p>
 * <p>Implements two interfaces to receive information from adapters and Dialogs</p>
 */
public class TaskView extends AppCompatActivity
        implements NewSubTaskDialog.NewSubTaskDialogListener,
        subtask_adapter.ClickListener {

    //Global Vars
    private ArrayList<SubTask> subTaskList;
    private RecyclerView recyclerView;
    private subtask_adapter adapter;
    private Button addSubTaskButton;
    private Task task;
    private int position;

    /**
     * <h2>TaskView onCreate</h2>
     * <p>It sets the adapter, click listeners (add SubTasks), and CheckBoxes</p>
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        //receive intent from calling activity
        Intent intent = getIntent();
        task = intent.getParcelableExtra("IndividualTask");
        position = intent.getIntExtra("Position", 0);
        String mainTaskTitle = task.getMainTaskTitle();
        String assignee = task.getAssignee();
        String mainTaskDescription = task.getMainTaskDescription();
        subTaskList = task.getSubTaskList();

        //Widget association
        TextView taskTitle = findViewById(R.id.mainTaskTitle);
        TextView assignedTo = findViewById(R.id.taskAssignee);
        TextView description = findViewById(R.id.mainDescription);

        //Set Widgets' text
        taskTitle.setText(mainTaskTitle);
        assignedTo.setText(assignee);
        description.setText(mainTaskDescription);

        //RecyclerView widget association and initialization
        recyclerView = findViewById(R.id.subtask_recycler);
        setSubTaskAdapter();

        //Button Widget association
        addSubTaskButton = findViewById(R.id.addSubTaskButton);

        //Click Listener for Add SubTask Button
        addSubTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Opens fragment that receives user input for subtask creation
                openNewSubTaskDialog();
            }
        });
    }

    /**
     * <h2>TaskView openSubTaskDialog</h2>
     * <p>Opens fragment (dialog) that receives the data to add a new subtask</p>
     */
    private void openNewSubTaskDialog() {
        NewSubTaskDialog newSubTaskDialog = new NewSubTaskDialog();
        newSubTaskDialog.show(getSupportFragmentManager(), "Add SubTask Dialog");
    }

    /**
     * <h2>TaskView setSubTaskAdapter</h2>
     * <p>sets the adapter and initializes it to feed data to the recyclerview</p>
     */
    private void setSubTaskAdapter() {
        adapter = new subtask_adapter(subTaskList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    /**
     * <h2>TaskView applySubTask</h2>
     * <p><b>(overwritten from public interface NEW SUBTASK DIALOG LISTENER)</b></p>
     * <p>Creates new subTask and applies changes to current activity</p>
     * @param title
     */
    @Override
    public void applySubTask(String title) {
        //This ensures it is added at the end
        int position = subTaskList.size();
        //subtask creation
        subTaskList.add(new SubTask(title, false));
        //notify that an item was inserted into tasklist, this allows for animations
        //   to be used on insertion
        adapter.notifyItemInserted(position);
    }

    /**
     * <h2>TaskView onBackPressed</h2>
     * <p>If the user presses the back key, information will be sent back to TaskBoard</p>
     */
    @Override
    public void onBackPressed() {

        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("subTaskList", subTaskList);
        resultIntent.putExtra("SubTask_Position", position);

        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }

    /**
     * <h2>TaskView onDeleteClick</h2>
     * <p><b>(overwritten from public interface CLICKLISTENER)</b></p>
     * @param position
     */
    @Override
    public void onDeleteClick(int position) {
        //position is received from the cardview where the delete button
        //   was clicked. This way we delete the appropriate one
        subTaskList.remove(position);
        //notify that an item was removed from tasklist, this allows for animations
        //   to be used on delete
        adapter.notifyItemRemoved(position);
    }

    /**
     * <h2>TaskView onCheckedClick</h2>
     * <p><b>(overwritten from public interface CLICKLISTENER)</b></p>
     * <p>Changes checkedStatus boolean var for individual subtasks</p>
     * @param position
     * @param isChecked
     */
    @Override
    public void onCheckedClick(int position, Boolean isChecked) {
        subTaskList.get(position).setIsChecked(isChecked);
    }
}
