package com.example.fitfusion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitfusion.dataModel.UserDataModel;
import com.example.fitfusion.helpers.MyAlarmReceiver;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceGoals;
    TextView heartrate;
    private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;
    TextView stepText;

    TextView caloriesText;
    TextView respRateText;
    int avgHr,avgRr;
    int totalSteps;
    int totalCalories;
    int targetCalories;
    TextView percentageWorkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        scheduleAlarm();
        stepText=findViewById(R.id.stepsDisplay);
        caloriesText=findViewById(R.id.caloriesDisplay);
        respRateText=findViewById(R.id.oxygenDisplay);
        percentageWorkout=findViewById(R.id.textView29);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope("https://www.googleapis.com/auth/fitness.activity.read"), new Scope("https://www.googleapis.com/auth/fitness.activity.write"))
                .build();

        GoogleSignInClient signInClient = GoogleSignIn.getClient(this, gso);

        // Check if the user is already signed in
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) {
            // If not signed in, start the Google Sign-In activity
            startActivityForResult(signInClient.getSignInIntent(), GOOGLE_FIT_PERMISSIONS_REQUEST_CODE);
        } else {
            // User is already signed in, you can proceed with accessing Google Fit data
            //accessGoogleFitData();
        }
        Handler handler = new Handler();
        Runnable fetchDataRunnable = new Runnable() {
            @Override
            public void run() {
                // Fetch data from Firebase for user "Ashish"
                fetchDataFromFirebase("Ashish");

                accessGoogleFitData();
                double percentage=0.0;
                if(targetCalories!=0)
                percentage=((double)(totalCalories)/(double)(targetCalories))*100;
                int showPerentage = (int) percentage;
                if(showPerentage>100) showPerentage=100;

                percentageWorkout.setText(String.valueOf(showPerentage)+"%");




                // Schedule the task to run again after 2 seconds
                handler.postDelayed(this, 2000);
            }
        };
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = sanitizeEmailForFirebase(currentUser.getEmail());
        databaseReferenceGoals = FirebaseDatabase.getInstance().getReference("users").child(userEmail).child("goals");
        getTargetCalories();
        heartrate = findViewById(R.id.heartRateDisplay);
        Button workoutBtn = findViewById(R.id.workoutBtn); // TODO: update to ID constraintLayout. Need to make Layout clickable
        Button goalSettingBtn = findViewById(R.id.goalSettingBtn);
        Button activitybtn = findViewById(R.id.activityTrackingBtn);
        handler.post(fetchDataRunnable);
        workoutBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Open the new activity
                long goalsSet=getInt(getApplicationContext(),"goalsSet",0);
                if(goalsSet == 1) {
                Intent intent = new Intent(HomeActivity.this, ReadyActivity.class);
                startActivity(intent);
                }
                else
                Toast.makeText(getApplicationContext(),"Please set Goals before starting workout in the GoalSetting Page!",Toast.LENGTH_SHORT).show();
            }
        });
        goalSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    // Open the new activity
                    Intent intent = new Intent(HomeActivity.this, GoalsActivity.class);
                    startActivity(intent);

            }
        });

        activitybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the new activity
                Intent intent = new Intent(HomeActivity.this, TrackingActivity.class);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // User granted permission, access Google Fit data
                //accessGoogleFitData();
            } else {
                // Permission denied by the user
                Log.e("Google Fit", "Permission denied");
            }
        }
    }
    private void fetchDataFromFirebase(String userName) {
        // Use the DatabaseReference to listen for changes or retrieve data
        // For example, add a ValueEventListener to listen for changes
        databaseReference.child(userName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Handle the data change here
                if (dataSnapshot.exists()) {
                    int heartRate = dataSnapshot.child("heartRate").getValue(Integer.class);
                    if (heartRate > 0) {
                        heartrate.setText(String.valueOf(heartRate));
                        respRateText.setText(String.valueOf(heartRate/5));
                        int respRate=heartRate/5;
                        int getHeartRate,getCount,getResprate;
                        if(!doesKeyExist(getApplicationContext(),"count")) {
                            saveHeartrate(getApplicationContext(), "count", 1);
                            saveHeartrate(getApplicationContext(), "hr", heartRate);
                            saveHeartrate(getApplicationContext(), "rr", respRate);
                        }
                        else{
                            getHeartRate=getInt(getApplicationContext(),"hr",0);
                            getCount=getInt(getApplicationContext(),"count",1);
                            getResprate=getInt(getApplicationContext(),"rr",0);
                            avgHr=getHeartRate/getCount;
                            avgRr=getResprate/getCount;
                            int newHeartRate=heartRate+getHeartRate;
                            int newRespRate=respRate+getResprate;
                            if(newHeartRate>1000000) {
                                newHeartRate=avgHr+heartRate;
                                newRespRate=avgRr+respRate;
                                getCount=1;
                            }


                            saveHeartrate(getApplicationContext(), "hr", newHeartRate);
                            saveHeartrate(getApplicationContext(), "rr", newRespRate);
                            saveHeartrate(getApplicationContext(), "count", getCount+1);
                        }

                        // Update your UI or perform any desired actions with the heart rate data
                        Log.d("HeartRate", "Heart rate for " + userName + ": " + heartRate);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors, if any
            }
        });
    }
    private void accessGoogleFitData() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            long startOfDay = calendar.getTimeInMillis();
            saveHeartrate(getApplicationContext(), "steps", totalSteps);
            saveHeartrate(getApplicationContext(), "calories", totalCalories);

            DataReadRequest readRequest = new DataReadRequest.Builder()
                    .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                    .aggregate(DataType.TYPE_CALORIES_EXPENDED, DataType.AGGREGATE_CALORIES_EXPENDED)
                    .bucketByTime(1, TimeUnit.DAYS)
                    .setTimeRange(startOfDay, System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .build();

            Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                    .readData(readRequest)
                    .addOnCompleteListener(new OnCompleteListener<DataReadResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<DataReadResponse> task) {
                            if (task.isSuccessful()) {
                                // Handle the step count data
                                DataReadResponse dataReadResponse = task.getResult();
                                totalSteps = extractStepCount(dataReadResponse);
                                totalCalories = (int) extractCalories(dataReadResponse);
                                updateStepCountOnUI(totalSteps, (int) totalCalories);
                            } else {
                                // Handle failure
                                Log.e("Google Fit", "Failed to retrieve step count data: " + task.getException());
                            }
                        }
                    });
        }
    }
    private int extractStepCount(DataReadResponse dataReadResponse) {
        int totalSteps = 0;
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

    private void updateStepCountOnUI(int stepCount, int totalCalories) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Update the TextView with the step count

                stepText.setText(String.valueOf(stepCount));
                caloriesText.setText(String.valueOf(totalCalories));
            }
        });
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
    public static boolean doesKeyExist(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.contains(key);
    }
    public static void saveHeartrate(Context context, String key, int value) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(key, value);
            editor.apply();

    }
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, defaultValue);
    }
    private void scheduleAlarm() {
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        Intent intent = new Intent(this, uploadService.class);
//        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//
//// Set the alarm to start at 11:59 PM
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 14);
//        calendar.set(Calendar.MINUTE, 48);
//
//// Repeat the task every day
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pendingIntent);
//        // Set up the time for 11:59 PM
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);

        // Create an Intent for your BroadcastReceiver
        Intent intent = new Intent(this, MyAlarmReceiver.class);

        // Create a PendingIntent to be triggered when the alarm goes off
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Set up the AlarmManager to trigger the BroadcastReceiver
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            // Schedule the alarm to repeat every day
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }
    public static String sanitizeEmailForFirebase(String email) {
        // Replace invalid characters with underscores
        return email.replaceAll("[.#$\\[\\]]", "_");
    }
    private void getTargetCalories(){
        databaseReferenceGoals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot contains the data from the database

                    // Map the data to UserData object
                    UserDataModel userDataModel = dataSnapshot.getValue(UserDataModel.class);

                    // Now you can use the userData object
                    if (userDataModel != null) {

                        targetCalories = userDataModel.getTargetCalories();

                        //Log.d("Userdata : ", age+":"+currentWeight+":"+height+":"+targetCalories+":"+targetSteps+":"+targetWeight+":"+currHeartRate+":"+);
                        // Do something with the data...
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });

    }
}