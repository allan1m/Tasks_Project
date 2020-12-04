package com.cs246.cleaningtasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.api.Distribution;

import java.util.ArrayList;

public class TaskView extends AppCompatActivity {
    private ArrayList<SubTask> subTaskList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        Task task = intent.getParcelableExtra("IndividualTask");

        String mainTaskTitle = task.getMainTaskTitle();
        String assignee = task.getAssignee();
        String mainTaskDescription = task.getMainTaskDescription();
        subTaskList = task.getSubTaskList();

        TextView taskTitle = findViewById(R.id.mainTaskTitle);
        TextView assignedTo = findViewById(R.id.taskAssignee);
        TextView description = findViewById(R.id.mainDescription);

        /**
         * @TODO subTaskList
         */

        taskTitle.setText(mainTaskTitle);
        assignedTo.setText(assignee);
        description.setText(mainTaskDescription);

        //Toast.makeText(this, "I WORKED", Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.subtask_recycler);

        setSubTaskList();

        setSubTaskAdapter();
        
    }

    private void setSubTaskAdapter() {
        subtask_adapter adapter = new subtask_adapter(subTaskList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setSubTaskList() {
        subTaskList.add(new SubTask("Cook"));
        subTaskList.add(new SubTask("Cook"));
    }

}
