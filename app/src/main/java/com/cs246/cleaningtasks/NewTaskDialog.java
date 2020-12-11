package com.cs246.cleaningtasks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * <h1>NEW TASK DIALOG</h1>
 * <p>Fragment that shows up as a dialog when a Task is created</p>
 */
public class NewTaskDialog extends AppCompatDialogFragment {
    private EditText editTextTaskTitle;
    private EditText editTextDescription;
    private EditText editTextAssignee;
    private NewTaskDialogListener listener;

    private static final String TAG = "NewTaskDialog";

    /**
     * <h2>OnCreateDialog</h2>
     *  <p>On Creation, the dialog is inflated (or populated) to include
     *  add and cancel buttons and an EdiText to type new Task description</p>
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Alert (Dialog) is built and inflated (populated)
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        //the Builder sets the internal contents of the dialog
        builder.setView(view)
                .setTitle("New Task")
                //Negative button should do nothing and changes to editText are not registered
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                //Positive button (Add) applies changes and creates a new task in TaskBoard.Java
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = editTextTaskTitle.getText().toString();
                        String description = editTextDescription.getText().toString();
                        String assignee = editTextAssignee.getText().toString();
                        listener.applyTask(title, assignee, description);
                    }
                });

        //Widgets associated to layout
        editTextTaskTitle = view.findViewById(R.id.task_name);
        editTextAssignee = view.findViewById(R.id.task_assignee);
        editTextDescription = view.findViewById(R.id.task_description);

        return builder.create();
    }

    /**
     * <h2>Dialog onAttach</h2>
     * <p>When created, this method applies the context to the interface:
     * NewTaskDialogListener. This way we can effect changes to TaskView generated
     * from THIS context</p>
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (NewTaskDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement dialog listener");
        }
    }

    /**
     * <h2>Public Interface: NewTaskDialogListener</h2>
     * <p>The method applySubTask is overriden in TaskView to allow
     * for information to be shared from this activity to that one</p>
     */
    public interface NewTaskDialogListener{
        void applyTask(String title, String assignee, String description);
    }
}
