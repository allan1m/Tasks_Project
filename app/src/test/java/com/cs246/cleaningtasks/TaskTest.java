package com.cs246.cleaningtasks;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <h1>Test: TASK CLASS TEST</h1>
 * <p>Tests correct functioning of Task Class</p>
 */
class TaskTest {

    @org.junit.jupiter.api.Test
    void getMainTaskTitle() {
        Task task = new Task("Title","Assignee","Description");
        assertEquals("Title", task.getMainTaskTitle());
    }

    @org.junit.jupiter.api.Test
    void getAssignee() {
        Task task = new Task("Title","Assignee","Description");
        assertEquals("Assignee", task.getAssignee());
    }

    @org.junit.jupiter.api.Test
    void getMainTaskDescription() {
        Task task = new Task("Title","Assignee","Description");
        assertEquals("Description", task.getMainTaskDescription());
    }

    //Tests both SetSubTask and GetSubTask
    @org.junit.jupiter.api.Test
    void getSubTaskList() {
        //create task
        Task task = new Task("Title","Assignee","Description");

        //create subTaskList
        ArrayList<SubTask> subTaskArrayList = new ArrayList<>();

        //add elements to subTaskList
        subTaskArrayList.add(0, new SubTask("TestAdd1", false));
        subTaskArrayList.add(1, new SubTask("TestAdd2", true));

        task.setSubTaskList(subTaskArrayList);

        ArrayList<SubTask> resultList = new ArrayList<SubTask>(task.getSubTaskList());

        assertEquals("TestAdd1", resultList.get(0).getSubTask());
        assertEquals("TestAdd2", resultList.get(1).getSubTask());
    }

    @org.junit.jupiter.api.Test
    void calculateCompleted() {
        //create task
        Task task = new Task("Title","Assignee","Description");

        //create subTaskList
        ArrayList<SubTask> subTaskArrayList = new ArrayList<>();

        //add elements to subTaskList
        subTaskArrayList.add(0, new SubTask("TestAdd1", false));
        subTaskArrayList.add(1, new SubTask("TestAdd2", true));
        subTaskArrayList.add(2, new SubTask("TestAdd3", true));
        subTaskArrayList.add(0, new SubTask("TestAdd4", false));

        task.setSubTaskList(subTaskArrayList);

        assertEquals("Completed: 2 / 4", task.calculateCompleted());
    }
}