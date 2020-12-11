package com.cs246.cleaningtasks;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <h1>Test: ADAPTER TEST</h1>
 * <p>Tests correct reception of subTasklist for recyclerview</p>
 */
class subtask_adapterTest {

    @Test
    void getItemCount() {

        //Create subtaskList
        ArrayList<SubTask> subTaskList = new ArrayList<SubTask>();
        //add 3 items to subTaskList
        subTaskList.add(new SubTask());
        subTaskList.add(new SubTask());
        subTaskList.add(new SubTask());

        //Create mock ClickListener
        subtask_adapter.ClickListener clickListener = null;

        //create subTaskAdapter and pass subTaskList
        subtask_adapter subtaskAdapter = new subtask_adapter(subTaskList, clickListener);

        assertEquals(3, subtaskAdapter.getItemCount());
    }
}