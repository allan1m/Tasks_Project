package com.cs246.cleaningtasks;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserBoard extends AppCompatActivity{
    //Gloabal variables in order to access textviews
    private TextView nameTextView, occupationTextView, idTextView, companyTextView;

    //Gloabl variable in order to access button
    private Button button;

    //Path to User database
    private static final String USER = "User";

    //Initialization and connection to Database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userReference = database.getReference(USER);

    //implemented User class in order to store User info locally as well.
    private User user = new User();

    TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        nameTextView = findViewById(R.id.name);
        occupationTextView = findViewById(R.id.occupation);
        companyTextView = findViewById(R.id.company);
        button = findViewById(R.id.task);

        //This method is responsible for collection database info and storing it inside
        //local variables in order to those local variables into textviews
        userReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                //Database child "name" from under User is stored in variable name
                //name is then set to nameTexview so that user can view
                //his/her name on app
                String name = snapshot.child("name").getValue(String.class);
                nameTextView.setText(name);
                user.setName(name);

                //Database child "occupation" from under User is stored in variable occupation
                //occupation is then set to nameTexview so that user can view
                //his/her occupation on app
                String occupation = snapshot.child("occupation").getValue(String.class);
                occupationTextView.setText(occupation);
                user.setOccupation(occupation);

                Long id = snapshot.child("id").getValue(Long.class);
                user.setId(id);

                //Database child "company" from under User is stored in variable company
                //company is then set to nameTexview so that user can view
                //his/her company on app
                String company = snapshot.child("company").getValue(String.class);
                companyTextView.setText(company);
                user.setCompany(company);


                //}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Task Button to navigate to start of tasks
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TaskBoard.class));
            }
        });
    }


}
