package com.cs246.cleaningtasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>REGISTER</h1>
 * <p>This class is used  to register new users to Firebase Auth</p>
 */
public class Register extends AppCompatActivity {
    private static final String TAG = "Register";
    EditText mFullName, mEmail, mPassword, mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    private View decorView;
    private FirebaseDatabase dbUsers = FirebaseDatabase.getInstance();
    private DatabaseReference userReference = dbUsers.getReference().child("USERS");

    /**
     * <h2>Register Oncreate</h2>
     * <p>Sets all the widgets and click listeners</p>
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //decorView handles the background of the Log_In layout
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        //Widget association
        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phone);
        mRegisterBtn = findViewById(R.id.loginBtn);
        mLoginBtn = findViewById(R.id.newAccount);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //For Security purposes the app forces the login each time,
        //   code left here to allow for the sponsor to change their mind
        //   in the future.
        /*
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), TaskBoard.class));
            finish();
        }
        */

        //Click Listener for Register Button: Creates new user in Firebase
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fullName = mFullName.getText().toString();
                String phone = mPhone.getText().toString();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required!");
                    return;
                }
                if (password.length() < 8){
                    mPassword.setError("Password must have 8 characters!");
                    return;
                }

                //REGISTERING THE USER AND PASSWORD THEN SAVING DATA ON FIREBASE
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Create User With Email: Success!", Toast.LENGTH_SHORT).show();
                            //STORING DATA IN FIRE-STORE DATABASE
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fullName",fullName);
                            userReference.setValue(mFullName);
                            user.put("email",email);
                            user.put("password",password);
                            user.put("phoneNumber",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });

                            startActivity(new Intent(getApplicationContext(), TaskBoard.class));
                        }else{
                            Toast.makeText(Register.this, "Create User With Email: Failure! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        /**
         * <h3>Login Button Listener</h3>
         * <p>Brings you back to login once user is created</p>
         */
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Log_In.class));
            }
        });

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
    }
}