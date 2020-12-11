package com.cs246.cleaningtasks;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <h1>Test: ADAPTER TEST</h1>
 * <p>Tests correct reception of tasklist for recyclerview</p>
 */
class AdapterTest {

    @Test
    //Test that itemCount is the correct size of the TaskList passed into adapter
    void getItemCount() {

        //Create taskList
        ArrayList<Task> taskList = new ArrayList<>();
        //Add 5 Tasks to TaskList
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());
        taskList.add(new Task());

        //Create mock onTouchListener
        Adapter.OnTouchListener onTouchListener = null;

        //create adapter
        Adapter adapter = new Adapter(taskList, onTouchListener);

        //assert that itemCount is correct
        assertEquals(4, adapter.getItemCount());
    }
}