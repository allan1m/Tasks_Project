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
 * <h1>New SubTask Dialog</h1>
 * <p>Fragment that shows up as a dialog when a subTask is created</p>
 */
public class NewSubTaskDialog extends AppCompatDialogFragment {
    private EditText editTextSubTaskTitle;
    private NewSubTaskDialog.NewSubTaskDialogListener listener;

    private static final String TAG = "NewSubTaskDialog";

    /**
     * <h2>OnCreateDialog</h2>
     * <p>On Creation, the dialog is inflated (or populated) to include
     * add and cancel buttons and an EdiText to type new subTask description</p>
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //Alert (Dialog) is built and inflated (populated)
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_subtask_dialog, null);
        editTextSubTaskTitle = view.findViewById(R.id.subtask_name);

        //the Builder sets the internal contents of the dialog
        builder.setView(view)
                .setTitle("New Sub-Task")
                //Negative button should do nothing and changes to editText are not registered
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Dialog Cancel Click: Changes should be dismissed");
                    }
                })
                //Positive button (Add) applies changes and creates a new task in TaskView.Java
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = editTextSubTaskTitle.getText().toString();
                        Log.d(TAG, "Dialog Add Click");
                        listener.applySubTask(title);
                    }
                });



        return builder.create();
    }

    /**
     * <h2>Dialog onAttach</h2>
     * <p>When created, this method applies the context to the interface:
     * NewSubTaskDialogListener. This way we can effect changes to TaskView generated
     * from THIS context</p>
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (NewSubTaskDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement dialog listener");
        }
    }

    /**
     * <h2>Public Interface: NewSubTaskDialogListener</h2>
     * <p>The method applySubTask is overriden in TaskView to allow
     * for information to be shared from this activity to that one</p>
     */
    public interface NewSubTaskDialogListener{
        void applySubTask(String title);
    }
}
