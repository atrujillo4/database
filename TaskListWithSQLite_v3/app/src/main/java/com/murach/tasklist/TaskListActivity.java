package com.murach.tasklist;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TaskListActivity extends Activity
implements OnClickListener {

    private Button addButton;
    private Button updateButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        addButton = (Button) findViewById(R.id.addButton);
        updateButton = (Button) findViewById(R.id.updateButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        addButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        TaskListDB db;
        StringBuilder sb;
        Task task, task2;
        ArrayList<Task> tasks;

        switch (v.getId()) {
            case R.id.addButton:
                // get db and StringBuilder objects
                db = new TaskListDB(this);
                sb = new StringBuilder();

                // insert a task - for testing purpose
                task = new Task(1, "Pay bills", "", "0", "0");
                long insertId = db.insertTask(task);
                if (insertId > 0) {
                    sb.append("Row inserted! Insert Id: " + insertId + "\n");
                }

                // insert a second task - for testing purpose
                task2 = new Task(2, "Do the project2", "", "0", "0");
                long insertId2 = db.insertTask(task2);
                if (insertId2 > 0) {
                    sb.append("Row inserted! Insert Id: " + insertId2 + "\n");
                }

                // display all "Personal" tasks
                sb.append("=== List of Personal Tasks =====\n");
                tasks = db.getTasks("Personal");
                for (Task t : tasks) {
                    sb.append(t.getId() + "|" + t.getName() + "\n");
                }

                // display all "Business" tasks
                sb.append("=== List of Business Tasks =====\n");
                tasks = db.getTasks("Business");
                for (Task t : tasks) {
                    sb.append(t.getId() + "|" + t.getName() + "\n");
                }

                // display a message on Toast
                Toast.makeText(getApplicationContext(), sb.toString(),
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.updateButton:
                // get db and StringBuilder objects
                db = new TaskListDB(this);
                sb = new StringBuilder();

                // update the first task from "Personal" tasks
                tasks = db.getTasks("Personal");
                for (Task t : tasks) {
                    t.setName("Pay bills - Updated.");
                    int updateCount = db.updateTask(t);
                    if (updateCount == 1) {
                        sb.append("Task updated! Update count: " + updateCount + "\n");
                    }
                    break;
                }

                // display a message on Toast
                Toast.makeText(getApplicationContext(), sb.toString(),
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.deleteButton:
                // get db and StringBuilder objects
                db = new TaskListDB(this);
                sb = new StringBuilder();

                // delete all "Personal" tasks
                sb.append("=== List of Deleted Personal Tasks =====\n");
                tasks = db.getTasks("Personal");
                for (Task t : tasks) {
                    long deleteID = t.getId();

                    // delete a task
                    int deleteCount = db.deleteTask(deleteID);
                    if (deleteCount == 1) {
                        sb.append("Task " + deleteID + " deleted!\n");
                    }
                }

                // display a message on Toast
                Toast.makeText(getApplicationContext(), sb.toString(),
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }
}