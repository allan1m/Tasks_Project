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

public class TaskView extends AppCompatActivity
        implements NewSubTaskDialog.NewSubTaskDialogListener,
        subtask_adapter.ClickListener {
    private ArrayList<SubTask> subTaskList;
    private RecyclerView recyclerView;
    private subtask_adapter adapter;
    private Button addSubTaskButton;
    private Task task;

    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        task = intent.getParcelableExtra("IndividualTask");
        position = intent.getIntExtra("Position", 0);

        String mainTaskTitle = task.getMainTaskTitle();
        String assignee = task.getAssignee();
        String mainTaskDescription = task.getMainTaskDescription();

        subTaskList = task.getSubTaskList();

        TextView taskTitle = findViewById(R.id.mainTaskTitle);
        TextView assignedTo = findViewById(R.id.taskAssignee);
        TextView description = findViewById(R.id.mainDescription);
        taskTitle.setText(mainTaskTitle);
        assignedTo.setText(assignee);
        description.setText(mainTaskDescription);

        recyclerView = findViewById(R.id.subtask_recycler);
        setSubTaskAdapter();

        addSubTaskButton = findViewById(R.id.addSubTaskButton);
        addSubTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewSubTaskDialog();
            }
        });
    }

    private void openNewSubTaskDialog() {
        NewSubTaskDialog newSubTaskDialog = new NewSubTaskDialog();
        newSubTaskDialog.show(getSupportFragmentManager(), "Add SubTask Dialog");
    }

    private void setSubTaskAdapter() {
        adapter = new subtask_adapter(subTaskList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void applySubTask(String title) {
        int position = subTaskList.size();
        subTaskList.add(new SubTask(title, false));
        adapter.notifyItemInserted(position);
    }


    @Override
    public void onBackPressed() {

        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("subTaskList", subTaskList);
        resultIntent.putExtra("SubTask_Position", position);

        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }
    @Override
    public void onDeleteClick(int position) {
        subTaskList.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onCheckedClick(int position, Boolean isChecked) {
        subTaskList.get(position).setIsChecked(isChecked);
    }
}
