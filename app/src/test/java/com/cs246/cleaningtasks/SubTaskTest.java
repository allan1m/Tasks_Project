package com.cs246.cleaningtasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <h1>Test: SUBTASK CLASS TEST</h1>
 * <p>Tests correct functioning of SubTask Class</p>
 */
class SubTaskTest {

    @Test
    //Tests both setIsChecked and getCheckedStatus
    void setIsChecked() {
        SubTask subTask = new SubTask("Test", true);
        SubTask subTask2 = new SubTask("Test2", false);

        assertEquals(true, subTask.getCheckedStatus());
        assertEquals(false, subTask2.getCheckedStatus());
    }

    @Test
    //Tests Both getSubTask and setSubTask
    void getSubTask() {

        SubTask subTask = new SubTask("Test", true);
        SubTask subTask2 = new SubTask("Test2", false);

        assertEquals("Test", subTask.getSubTask());
        assertEquals("Test2", subTask2.getSubTask());

    }

}