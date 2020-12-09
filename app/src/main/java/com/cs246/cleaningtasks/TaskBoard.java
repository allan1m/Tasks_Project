package com.cs246.cleaningtasks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;

public class TaskBoard extends AppCompatActivity
        implements Adapter.OnTouchListener,
        NewTaskDialog.NewTaskDialogListener{

    private static final String mTask = "New Task";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference taskReference = database.getReference(mTask);
    private ArrayList<Task> taskList;
    private RecyclerView recyclerView;
    private Button addTaskButton;
    private Adapter adapter;
    private Button logOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_recycler_layout);
        taskList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        addTaskButton = (Button) findViewById(R.id.addTaskButton);
        logOut = findViewById(R.id.logoutBtn);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

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
    }

    /*  updateDataBase is responsible for updating database with either
        eliminating task entry or adding task entry.
     */
    private void updateDataBase() {
        adapter.notifyDataSetChanged();
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
                //Custom ArrayList meant to hold taskList list
                ArrayList<Task> tempList= new ArrayList<>();

                for (DataSnapshot task: snapshot.getChildren()) {
                    Task i = new Task(task.getValue(Task.class));
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
    }

    @Override
    public void onCardClick(int position) {

        Intent intent = new Intent(this, TaskView.class);
        intent.putExtra("IndividualTask", taskList.get(position));
        intent.putExtra("Position", position);
        startActivityForResult(intent,1);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra("SubTask_Position", 0);
                taskList.get(position).setSubTaskList( data.getParcelableArrayListExtra("subTaskList") );
            }
        }
        adapter.notifyDataSetChanged();
    }
}
