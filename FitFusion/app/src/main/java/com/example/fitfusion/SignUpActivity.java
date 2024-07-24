package com.example.fitfusion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitfusion.dataModel.SignupUserModel;
import com.example.fitfusion.helpers.toastHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword, editTextFirstName, editTextLastName, editTextConfirmPassword;
    Button ButtonReg;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        editTextFirstName = findViewById(R.id.editTextText);
        editTextLastName = findViewById(R.id.editTextText2);
        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextTextPassword2);
        ButtonReg = findViewById(R.id.updateGoalsBtn);



        ButtonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String Email, Password, Firstname, Lastname, Confirm_Password, PasswordPattern;
                Firstname = String.valueOf(editTextFirstName.getText());
                Lastname = String.valueOf(editTextLastName.getText());
                Email = String.valueOf(editTextEmail.getText());
                Password =String.valueOf(editTextPassword.getText());
                PasswordPattern = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$";
                Confirm_Password = String.valueOf(editTextConfirmPassword.getText());


                if (TextUtils.isEmpty(Firstname)){
                    toastHelper.showLongMessageSnackbar(findViewById(android.R.id.content), "Enter First Name");
                    return;
                }

                if (TextUtils.isEmpty(Lastname)){
                    toastHelper.showLongMessageSnackbar(findViewById(android.R.id.content), "Enter Last Name");
                    return;
                }


                if (TextUtils.isEmpty(Email)){
                    toastHelper.showLongMessageSnackbar(findViewById(android.R.id.content), "Enter email");
                    return;
                }

                if (TextUtils.isEmpty(Password)){
                    toastHelper.showLongMessageSnackbar(findViewById(android.R.id.content), "Enter a password");
                    return;
                }

                if (!Password.matches(PasswordPattern)) {
                    toastHelper.showLongMessageSnackbar(findViewById(android.R.id.content), "Password must contain at least one uppercase letter, one number, and be at least 8 characters long");
                    return;
                }

                if (TextUtils.isEmpty(Confirm_Password)){
                    toastHelper.showLongMessageSnackbar(findViewById(android.R.id.content), "Please Confirm Password");
                    return;
                }

                if (!Password.equals(Confirm_Password)) {
                    toastHelper.showLongMessageSnackbar(findViewById(android.R.id.content), "Passwords do not match");
                    return;
                }



                mAuth.createUserWithEmailAndPassword(Email,Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Get the registered user
                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                    // Get the unique UID for the user
                                    String userId = firebaseUser.getUid();

                                    SignupUserModel helperclass = new SignupUserModel(Firstname, Lastname, Email, Password);
                                    reference.child(sanitizeEmailForFirebase(Email)).child("userInfo").setValue(helperclass);
                                    Toast.makeText(SignUpActivity.this, "Account Created",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



            }
        });
    }
    public static String sanitizeEmailForFirebase(String email) {
        // Replace invalid characters with underscores
        return email.replaceAll("[.#$\\[\\]]", "_");
    }
}