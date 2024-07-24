package com.example.fitfusion.helpers;


import static com.example.fitfusion.TrackingActivity.sanitizeEmailForFirebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MyAlarmReceiver extends BroadcastReceiver {
    private DatabaseReference databaseReference;
    String userEmail;
    GoogleSignInAccount account ;
    int totalSteps,totalCalories;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("TRRIGGERED","DONE");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = sanitizeEmailForFirebase(currentUser.getEmail());
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userEmail);
        account = GoogleSignIn.getLastSignedInAccount(context);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long startOfDay = calendar.getTimeInMillis();
        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                .aggregate(DataType.TYPE_CALORIES_EXPENDED, DataType.AGGREGATE_CALORIES_EXPENDED)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startOfDay, System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .build();
        account=GoogleSignIn.getLastSignedInAccount(context);

        Fitness.getHistoryClient(context, account)
                .readData(readRequest)
                .addOnCompleteListener(new OnCompleteListener<DataReadResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<DataReadResponse> task) {
                        if (task.isSuccessful()) {
                            // Handle the step count data
                            DataReadResponse dataReadResponse = task.getResult();
                            Calendar calendar = Calendar.getInstance();
                            Date currentDate = calendar.getTime();

                            // Format the date as a string
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String date = dateFormat.format(currentDate);
                            totalSteps = extractStepCount(dataReadResponse);
                            totalCalories = (int) extractCalories(dataReadResponse);
                            databaseReference.child("workoutTime").child(date).child("Steps").setValue(totalSteps);
                            databaseReference.child("workoutTime").child(date).child("Calories").setValue(totalCalories);
                        } else {
                            // Handle failure
                            Log.e("Google Fit", "Failed to retrieve step count data: " + task.getException());
                        }
                    }
                });
    }
    private int extractStepCount(DataReadResponse dataReadResponse) {
        totalSteps = 0;
        if (dataReadResponse != null) {
            for (Bucket bucket : dataReadResponse.getBuckets()) {
                for (com.google.android.gms.fitness.data.DataSet dataSet : bucket.getDataSets()) {
                    for (com.google.android.gms.fitness.data.DataPoint dp : dataSet.getDataPoints()) {
                        for (Field field : dp.getDataType().getFields()) {
                            // Check if the value is an integer field
                            if (field.getFormat() == Field.FORMAT_INT32) {
                                totalSteps += dp.getValue(field).asInt();
                            } else {
                                // Handle other cases (e.g., float, double) as needed
                                // You might want to log a message or handle it differently
                                Log.e("Google Fit", "Unexpected data type for step count");
                            }
                        }
                    }
                }
            }
        }
        return totalSteps;
    }
    private float extractCalories(DataReadResponse dataReadResponse) {
        totalCalories=0;
        if (dataReadResponse != null) {
            for (Bucket bucket : dataReadResponse.getBuckets()) {
                for (com.google.android.gms.fitness.data.DataSet dataSet : bucket.getDataSets()) {
                    for (com.google.android.gms.fitness.data.DataPoint dp : dataSet.getDataPoints()) {
                        for (Field field : dp.getDataType().getFields()) {
                            // Check if the value is a float field
                            if (field.getFormat() == Field.FORMAT_FLOAT) {
                                totalCalories += dp.getValue(field).asFloat();
                            } else {
                                // Handle other cases (e.g., integer, double) as needed
                                // You might want to log a message or handle it differently
                                Log.e("Google Fit", "Unexpected data type for calories");
                            }
                        }
                    }
                }
            }
        }
        return totalCalories;
    }
    private void uploadDataToFirebase(Context context) {
        // Get the current date and time

    }
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, defaultValue);
    }


}

