package com.cs246.cleaningtasks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class NewTaskDialog extends AppCompatDialogFragment {
    private EditText editTextTaskTitle;
    private EditText editTextDescription;
    private EditText editTextAssignee;
    private NewTaskDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("New Task")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = editTextTaskTitle.getText().toString();
                        String description = editTextDescription.getText().toString();
                        String assignee = editTextAssignee.getText().toString();
                        listener.applyTask(title, assignee, description);
                    }
                });

        editTextTaskTitle = view.findViewById(R.id.task_name);
        editTextAssignee = view.findViewById(R.id.task_assignee);
        editTextDescription = view.findViewById(R.id.task_description);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (NewTaskDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement dialog listener");
        }
    }

    public interface NewTaskDialogListener{
        void applyTask(String title, String assignee, String description);
    }
}
