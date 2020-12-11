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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <h1>TASKBOARD</h1>
 * <p>TaskBoard holds the logic for the Recyclerview that holds Tasks(in quickview mode)</p>
 * <p>It handles database queries and updates</p>
 * <p>Implements two interfaces to receive information from adapters and Dialogs</p>
 */
public class TaskBoard extends AppCompatActivity
        implements Adapter.OnTouchListener,
        NewTaskDialog.NewTaskDialogListener{

    //Global Vars
    private static final String mTask = "New Task";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference taskReference = database.getReference(mTask);
    private ArrayList<Task> taskList;
    private RecyclerView recyclerView;
    private Button addTaskButton;
    private Adapter adapter;
    private Button logOut;

    /**
     * <h2>TaskBoard onCreate</h2>
     * <p>It sets the adapter, click listeners (add Tasks), and database updates</p>
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_recycler_layout);

        //Widget Associations and variable initializations
        taskList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        addTaskButton = (Button) findViewById(R.id.addTaskButton);
        logOut = findViewById(R.id.logoutBtn);

        //Listener for Logout button
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Signs out from Firebase Auth and sends the user back to login layout
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Log_In.class));
                finish();
            }
        });

        //Listener for Add Task Button
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        //Receives the information from database
        setTaskInfo();

        //sets the adapter for the recyclerview
        setAdapter();

        //Title for Activity
        setTitle("Adding task");
    }

    /**
     * <h2>Activity LifeCycle ONSTOP</h2>
     * <p>onStop is used to write information to database before shutdown</p>
     */
    @Override
    protected void onStop() {
        super.onStop();

        //handles database update
        updateDataBase();
    }

    /**
     * <h2>TaskBoard updateDatabase</h2>
     * <p>updateDataBase is responsible for updating database
     * with either eliminating task entry or adding task entry.</p>
     */
    private void updateDataBase() {
        //refreshes data on Recyclerview before update
        adapter.notifyDataSetChanged();
        HashMap map = new HashMap();
        map.put("New Task", taskList);

        database.getReference().updateChildren(map);
    }

    /**
     * <h2>TaskBoard openDialog</h2>
     * <p>Opens fragment (dialog) that receives the data to add a new task</p>
     */
    public void openDialog(){
        NewTaskDialog newTaskDialog = new NewTaskDialog();
        newTaskDialog.show(getSupportFragmentManager(), "New Task Dialog");
    }

    /**
     * <h2>TaskBoard setAdapter</h2>
     * <p>sets the adapter and initializes it to feed data to the recyclerview</p>
     */
    private void setAdapter() {
        adapter = new Adapter(taskList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    /**
     * <h2>TaskBoard setTaskInfo</h2>
     * <p>Handles data reception from database when Activity is created</p>
     */
    private void setTaskInfo() {
        //Read and listen for changes to the entire contents of a path.
        taskReference.addValueEventListener(new ValueEventListener() {
            @Override
            // onDataChange is meant to read a static snapshot of the contents at a given path, as they
            // a given path, as they existed at the time of the event. This method is triggered once when
            // the listener is attached and again every time the data, including children, changes.
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //Temporary ArrayList meant to hold taskList list
                ArrayList<Task> tempList= new ArrayList<>();

                //Receive data from database and add to tempList
                //   this prevents duplication in local taskList without
                //   having to erase it until data is read successfully from database
                for (DataSnapshot task: snapshot.getChildren()) {
                    Task i = new Task(task.getValue(Task.class));
                    tempList.add(i);
                }

                //clear local tasklist
                taskList.clear();

                //copy data from database list  to local list
                for (Task a: tempList){
                    taskList.add(a);
                }

                //make sure the recyclerview is notified about the change
                adapter.notifyDataSetChanged();
            }

            /**
             * <h3>Database onCancelled</h3>
             * <p>makes a toast to inform that there were errors receiving from database</p>
             * @param error
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TaskBoard.this, "Error connecting to database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * <h2>TaskBoard onCardClick</h2>
     * <p><b>(overwritten from public interface ONTOUCHLISTENER)</b></p>
     * <p>Sends intent with parcels, and initiates TaskView.Class</p>
     * @param position
     */
    @Override
    public void onCardClick(int position) {

        Intent intent = new Intent(this, TaskView.class);
        intent.putExtra("IndividualTask", taskList.get(position));
        intent.putExtra("Position", position);

        //starts the activity to receive created subTaskList
        //   This will receive any changes made in the individual Task
        startActivityForResult(intent,1);

    }

    /**
     * <h2>TaskBoard applyTask</h2>
     * <p><b>(overwritten from public interface ONTOUCHLISTENER)</b></p>
     * <p>Creates new Task and applies changes to current activity</p>
     * @param title
     * @param assignee
     * @param description
     */
    @Override
    public void applyTask(String title, String assignee, String description) {
        //This ensures it is added at the end
        int position = taskList.size();
        //task creation
        taskList.add(new Task(title, assignee, description));
        //notify that an item was inserted into tasklist, this allows for animations
        //   to be used on insertion
        adapter.notifyItemInserted(position);
        //upDatabase will update Database when a new task is added
        updateDataBase();

    }

    /**
     * <h2>TaskBoard onDeleteClick</h2>
     * <p><b>(overwritten from public interface ONTOUCHLISTENER)</b></p>
     * @param position
     */
    @Override
    public void onDeleteClick(int position) {
        //position is received from the cardview where the delete button
        //   was clicked. This way we delete the appropriate one
        taskList.remove(position);
        //notify that an item was removed from tasklist, this allows for animations
        //   to be used on delete
        adapter.notifyItemRemoved(position);
        //upDatabase will update Database when a task is deleted
        updateDataBase();
    }

    /**
     * <h2>TaskBoard onActivityResult</h2>
     * <p>Receives the results from startActivityForResults</p>
     * <p>startActivityForResults called from OnCardClick and is expecting
     * changes to subTaskList</p>
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //RequestCode 1 is the one provided in OnCardClick
        if(requestCode == 1){
            //If result was provided from the opened activity
            if (resultCode == RESULT_OK) {
                //get position from resulting intent
                int position = data.getIntExtra("SubTask_Position", 0);
                //update subTaskList from appropriate Task in Tasklist
                taskList.get(position).setSubTaskList( data.getParcelableArrayListExtra("subTaskList") );
            }
        }
        //Make sure changes are notified; updates completion status
        adapter.notifyDataSetChanged();
    }
}
