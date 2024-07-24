package com.example.fitfusion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fitfusion.dataModel.UserDataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GoalsActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText height,age,currentWeight,targetWeight,targetSteps,targetCalories;
    AutoCompleteTextView gender;
    Button updateGoals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);
        height=findViewById(R.id.heightInput);
        age=findViewById(R.id.ageInput);
        gender=findViewById(R.id.genderInput);
        currentWeight=findViewById(R.id.weightInput);
        targetCalories=findViewById(R.id.targetCaloriesInput);
        targetWeight=findViewById(R.id.targetWeightInput);
        targetSteps=findViewById(R.id.targetStepCount);
        updateGoals=findViewById(R.id.updateGoalsBtn);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = sanitizeEmailForFirebase(currentUser.getEmail());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userEmail).child("goals");
        String[] genderOptions = {"Male", "Female", "Others"};

        // Create an ArrayAdapter with the gender options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, genderOptions);

        // Set the ArrayAdapter to the AutoCompleteTextView
        gender.setAdapter(adapter);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Data for the user already exists

                    // Retrieve existing data
                    UserDataModel existingUserDataModel = dataSnapshot.getValue(UserDataModel.class);

                    // Populate UI elements with existing data
                    height.setText(String.valueOf(existingUserDataModel.getHeight()));
                    currentWeight.setText(String.valueOf(existingUserDataModel.getCurrentWeight()));
                    age.setText(String.valueOf(existingUserDataModel.getAge()));
                    targetWeight.setText(String.valueOf(existingUserDataModel.getTargetWeight()));
                    targetCalories.setText(String.valueOf(existingUserDataModel.getTargetCalories()));
                    targetSteps.setText(String.valueOf(existingUserDataModel.getTargetSteps()));
                    gender.setText(String.valueOf(existingUserDataModel.getGender()));



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error

            }
        });


        updateGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalsSet();
                String heightString = String.valueOf(height.getText());
                String currentWeightString = String.valueOf(currentWeight.getText());
                String ageString = String.valueOf(age.getText());
                String targetWeightString = String.valueOf(targetWeight.getText());
                String targetCaloriesString = String.valueOf(targetCalories.getText());
                String targetStepsString = String.valueOf(targetSteps.getText());
                String genderString=String.valueOf(gender.getText());

// Check if any of the values is empty
                if (TextUtils.isEmpty(heightString)
                        || TextUtils.isEmpty(currentWeightString)
                        || TextUtils.isEmpty(ageString)
                        || TextUtils.isEmpty(targetWeightString)
                        || TextUtils.isEmpty(targetCaloriesString)
                        || TextUtils.isEmpty(genderString)
                        || TextUtils.isEmpty(targetStepsString)) {
                    // Display a toast message indicating that all fields must be filled
                    Toast.makeText(GoalsActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                } else {
                    // Convert the non-empty values to integers
                    int heightVal = Integer.parseInt(heightString);
                    int currentWeightVal = Integer.parseInt(currentWeightString);
                    int ageVal = Integer.parseInt(ageString);
                    int targetWeightVal = Integer.parseInt(targetWeightString);
                    int targetCaloriesVal = Integer.parseInt(targetCaloriesString);
                    int targetStepsVal = Integer.parseInt(targetStepsString);

                    // Continue with Firebase upload...
                    UserDataModel userDataModel = new UserDataModel();
                    userDataModel.setGender(genderString);
                    userDataModel.setHeight(heightVal);
                    userDataModel.setCurrentWeight(currentWeightVal);
                    userDataModel.setAge(ageVal);
                    userDataModel.setTargetWeight(targetWeightVal);
                    userDataModel.setTargetCalories(targetCaloriesVal);
                    userDataModel.setTargetSteps(targetStepsVal);

                    // Upload the UserData instance to Firebase
                    databaseReference.setValue(userDataModel, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                // Data upload successful
                                Toast.makeText(GoalsActivity.this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                // Data upload failed
                                Toast.makeText(GoalsActivity.this, "Data upload failed: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
    public static String sanitizeEmailForFirebase(String email) {
        // Replace invalid characters with underscores
        return email.replaceAll("[.#$\\[\\]]", "_");
    }
    private void goalsSet() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("goalsSet", 1);
        editor.apply();
    }
}
