package com.cs246.cleaningtasks;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TaskBoard extends AppCompatActivity implements Adapter.OnTouchListener {

    private ArrayList<Task> taskList;
    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_recycler_layout);
        taskList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        setTaskInfo();
        setAdapter();
    }

    private void setAdapter() {
        Adapter adapter = new Adapter(taskList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setTaskInfo() {
        taskList.add(new Task("Barrer","Rufino","blabbity blah"));
        taskList.add(new Task("Trapear","Allan","blobber"));
        taskList.add(new Task("Aspirar","Daniel","thenasdas"));
        taskList.add(new Task("Trapear2","Allan2","blobber"));
        taskList.add(new Task("Aspirar2","Daniel2","thenasdas"));
        taskList.add(new Task("Trapear2","Allan2","blobber"));
        taskList.add(new Task("Aspirar3","Daniel3","thenasdas"));
        taskList.add(new Task("Trapear3","Allan3","blobber"));
        taskList.add(new Task("Aspirar3","Daniel3","thenasdas"));


    }

    @Override
    public void onCardClick(int position) {
        taskList.get(position);

        Intent intent = new Intent(this, TaskView.class);
        startActivity(intent);

        root.setValue(taskList.get(position));
    }
}
