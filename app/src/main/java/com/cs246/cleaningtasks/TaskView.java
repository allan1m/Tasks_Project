package com.cs246.cleaningtasks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TaskView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        Task task = intent.getParcelableExtra("IndividualTask");

        String mainTaskTitle = task.getMainTaskTitle();
        String assignee = task.getAssignee();
        String mainTaskDescription = task.getMainTaskDescription();
        ArrayList<String> subTaskList = task.getSubTaskList();

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
    }

}
