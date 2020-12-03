package com.cs246.cleaningtasks;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TaskBoard extends AppCompatActivity
        implements Adapter.OnTouchListener,
        NewTaskDialog.NewTaskDialogListener{

    private static final String TASK = "Task";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference taskReference = database.getReference(TASK);
    private String employee, task, description;
    private ArrayList<Task> taskList;
    private RecyclerView recyclerView;
    private Button addTaskButton;
    private Adapter adapter;
    private int tempPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_recycler_layout);
        taskList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        addTaskButton = (Button) findViewById(R.id.addTaskButton);

        setTaskInfo();
        setAdapter();

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        taskReference.setValue(taskList);
    }

    public void openDialog(){
        NewTaskDialog newTaskDialog = new NewTaskDialog();
        newTaskDialog.show(getSupportFragmentManager(), "New Task Dialog");
    }

    private void setAdapter() {
        adapter = new Adapter(taskList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setTaskInfo() {

        taskReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                employee = snapshot.child("Assignee").getValue(String.class);
                task = snapshot.child("MainTask").getValue(String.class);
                description = snapshot.child("TaskDescription").getValue(String.class);



                taskList.add(new Task(task, employee, description));
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*taskList.add(new Task("Barrer","Rufino","blabbity blah"));
        taskList.add(new Task("Trapear","Allan","blobber"));
        taskList.add(new Task("Aspirar","Daniel","thenasdas"));
        taskList.add(new Task("Trapear2","Allan2","blobber"));
        taskList.add(new Task("Aspirar2","Daniel2","thenasdas"));
        taskList.add(new Task("Trapear2","Allan2","blobber"));
        taskList.add(new Task("Aspirar3","Daniel3","thenasdas"));
        taskList.add(new Task("Trapear3","Allan3","blobber"));
        taskList.add(new Task("Aspirar3","Daniel3","thenasdas"));*/

    }



    @Override
    public void onCardClick(int position) {

        Intent intent = new Intent(this, TaskView.class);
        intent.putExtra("IndividualTask", taskList.get(position));
        startActivity(intent);

        //root.setValue(taskList.get(position));
    }

    @Override
    public void applyTask(String title, String assignee, String description) {
        int position = taskList.size();
        taskList.add(new Task(title, assignee, description));
        adapter.notifyItemInserted(position);
    }

    @Override
    public void onDeleteClick(int position) {
        taskList.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
