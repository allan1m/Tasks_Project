package com.cs246.cleaningtasks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskBoard extends AppCompatActivity
        implements Adapter.OnTouchListener,
        NewTaskDialog.NewTaskDialogListener{

    //private static final String TASK = "Task";
    private static final String mTask = "New Task";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference taskReference = database.getReference(mTask);
    /*private DatabaseReference newTaskReference = database.getReference(mTask);*/
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

        /*Toast.makeText(TaskBoard.this, "Before Thread!", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TaskBoard.this, "First Thread Example Running!", Toast.LENGTH_SHORT).show();
                setTaskInfo();
            }
        }).start();*/

        setTaskInfo();
        setAdapter();

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        setTitle("Adding task");
    }

    /*  onStop is used to write information to database before shutdown*/
    @Override
    protected void onStop() {
        super.onStop();
        updateDataBase();

        /*HashMap map = new HashMap();
        map.put("New Task", taskList);

        database.getReference().updateChildren(map);*/
    }

    /*  updateDataBase is responsible for updating database with either
        eliminating task entry or adding task entry.
     */
    private void updateDataBase() {
        HashMap map = new HashMap();
        map.put("New Task", taskList);

        database.getReference().updateChildren(map);
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

        //Read and listen for changes to the entire contents of a path.
        taskReference.addValueEventListener(new ValueEventListener() {
            @Override
            // onDataChange is meant to read a static snapshot of the contents at a given path, as they
            // a given path, as they existed at the time of the event. This method is triggered once when
            // the listener is attached and again every time the data, including children, changes.
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*employee = snapshot.child("Assignee").getValue(String.class);
                task = snapshot.child("MainTask").getValue(String.class);
                description = snapshot.child("Description").getValue(String.class);

                taskList.add(new Task(task, employee, description));*/

                //Custom ArrayList meant to hold taskList list
                ArrayList<Task> tempList= new ArrayList<>();

                for (DataSnapshot task: snapshot.getChildren()) {
                    Task i = task.getValue(Task.class);
                    tempList.add(i);
                }

                taskList.clear();

                for (Task a: tempList){
                    taskList.add(a);
                }

                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*newTaskReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                employee = snapshot.child("1").child("Assign").getValue(String.class);


                taskList.add(new Task(task, employee, description));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        /*taskList.add(new Task(task, employee, description));
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
    }

    @Override
    public void applyTask(String title, String assignee, String description) {
        int position = taskList.size();
        taskList.add(new Task(title, assignee, description));
        adapter.notifyItemInserted(position);

        //upDatabase will update Database when a new task is added
        updateDataBase();
    }

    @Override
    public void onDeleteClick(int position) {
        taskList.remove(position);
        adapter.notifyItemRemoved(position);

        //upDatabase will update Database when a task is deleted
        updateDataBase();
    }
}
