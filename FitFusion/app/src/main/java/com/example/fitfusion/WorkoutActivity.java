package com.example.fitfusion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;
import com.example.fitfusion.Suggestion.Suggestion;
import com.example.fitfusion.Suggestion.Workout.WorkoutInfo;
import com.example.fitfusion.adapters.WorkoutCardAdapter;
import com.example.fitfusion.dataModel.UserDataModel;
import com.example.fitfusion.dataModel.WorkoutCardModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WorkoutActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceGoals;
    DatabaseReference databaseReferenceHeart;
    DatabaseReference databaseReferenceWorkouts;
    int avgHr,avgRr;
    int currRespRate;
    String userEmail;
    int age;
    int currentWeight;
    int currHeartRate;
    int currCalories;
    int currSteps;
    int height;
    Constants.GENDER gender;
    int targetCalories;
    int targetSteps;
    int targetWeight;
    private RecyclerView recyclerView;
    private WorkoutCardAdapter adapter;
    private List<WorkoutCardModel> dataList;
    SharedPreferences sharedPreferences;
    TextView calculating;
    boolean workoutFinished = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = sanitizeEmailForFirebase(currentUser.getEmail());
        databaseReferenceGoals = FirebaseDatabase.getInstance().getReference("users").child(userEmail).child("goals");
        databaseReferenceWorkouts = FirebaseDatabase.getInstance().getReference("users").child(userEmail).child("workoutTime").child(getDate());
        databaseReferenceHeart = FirebaseDatabase.getInstance().getReference("users").child("Ashish");
        int getHeartRate=getInt(getApplicationContext(),"hr",0);
        int getRespRate=getInt(getApplicationContext(),"rr",0);
        int getCount=getInt(getApplicationContext(),"count",1);
        avgHr=getHeartRate/getCount;
        avgRr=getRespRate/getCount;
        accessGoogleFitData();
        recyclerView = findViewById(R.id.recyclerView);
        dataList = getData();// TODO: call after getting suggestions
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WorkoutCardAdapter(dataList);
        recyclerView.setAdapter(adapter);
        calculating=findViewById(R.id.textView43);


        Handler handler = new Handler();
        Runnable fetchDataRunnable = new Runnable() {
            @Override
            public void run() {
                // Fetch data from Firebase for user "Ashish"
                fetchDataFromFirebase("Ashish");

                accessGoogleFitData();


                int counter=0;
                for( WorkoutCardModel data:dataList){
                    if(data.isCompleted())
                        counter++;
                }

                if(targetCalories < currCalories){
                    workoutFinished=true;
                    dataList.clear();
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter = new WorkoutCardAdapter(dataList);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);

                    String workoutList="";
                    for(WorkoutCardModel d:dataList){
                        if(d.isCompleted())
                            workoutList+=d.getWorkout()+" x "+d.getTime()+" "+d.getUnit()+" #";

                    }
                    workoutList=workoutList.replace("#","\n");
                    if(!workoutList.isEmpty())
                    appendWorkoutList(workoutList);
                    calculating.setText("Workout finished!");

                }
                else {
                    workoutFinished = false;
                    calculating.setText("Calculating...");
                }
                if(counter==dataList.size()) {
                    String workoutList="";
                    for(WorkoutCardModel d:dataList){
                        workoutList+=d.getWorkout()+" x "+d.getTime()+" "+d.getUnit()+" #";

                    }
                    workoutList=workoutList.replace("#","\n");
                    if(!workoutList.isEmpty())
                        appendWorkoutList(workoutList);
                    dataList.clear();

                    databaseReferenceHeart.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // Handle the data change here
                            if (dataSnapshot.exists() && !workoutFinished) {
                                Toast.makeText(getApplicationContext(), "Refreshing tasks", Toast.LENGTH_SHORT).show();
                                currHeartRate = dataSnapshot.child("heartRate").getValue(Integer.class);
                                currRespRate = currHeartRate/5;
                                BodyInfo bodyInfo = new BodyInfo(age,gender,height,currentWeight,currHeartRate,avgHr,currRespRate,avgRr,currSteps,targetSteps,currCalories,targetCalories);
                                bodyInfo.setNonPrimitiveMetrics();
                                Suggestion suggestion = new Suggestion(bodyInfo);
                                ArrayList<WorkoutInfo> workouts = suggestion.getSuggestions();
                                for (WorkoutInfo workout : workouts) {
                                    String name = workout.getName();
                                    String value = workout.getValue();
                                    String bgImg=workout.getBgImg();
                                    String unit=workout.getUnit();
                                    int imageResourceId;
                                    if(bgImg==null)
                                        imageResourceId=R.drawable.wo_pullups;
                                    else
                                        imageResourceId= getResources().getIdentifier(bgImg, "drawable", getPackageName());


                                    // Create a new WorkoutData object
                                    WorkoutCardModel data = new WorkoutCardModel(imageResourceId,value, name,false,unit);

                                    // Add the extracted data to the new ArrayList
                                    dataList.add(data);


                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                adapter = new WorkoutCardAdapter(dataList);
                                adapter.notifyDataSetChanged();
                                recyclerView.setAdapter(adapter);

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle errors, if any
                        }
                    });


                }

                // Schedule the task to run again after 2 seconds
                handler.postDelayed(this, 2000);
            }
        };
        handler.post(fetchDataRunnable);
        databaseReferenceGoals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot contains the data from the database

                    // Map the data to UserData object
                    UserDataModel userDataModel = dataSnapshot.getValue(UserDataModel.class);

                    // Now you can use the userData object
                    if (userDataModel != null) {
                        age = userDataModel.getAge();
                        currentWeight = userDataModel.getCurrentWeight();
                        gender=convertToConstantFormat(userDataModel.getGender());
                        height = userDataModel.getHeight();
                        targetCalories = userDataModel.getTargetCalories();
                        targetSteps = userDataModel.getTargetSteps();
                        targetWeight = userDataModel.getTargetWeight();
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

    public static String sanitizeEmailForFirebase(String email) {
        // Replace invalid characters with underscores
        return email.replaceAll("[.#$\\[\\]]", "_");
    }
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(key, defaultValue);
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
                    int avgHr=getInt(getApplicationContext(),"hr",70)/getInt(getApplicationContext(),"count",1);

                    if(heartRate > avgHr*2.6)

                        showAlert(WorkoutActivity.this, "Overheat", "Please cooldown for 15 minutes");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors, if any
            }
        });
    }

    private void showAlert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do something when OK button is clicked
                        dialog.dismiss(); // Dismiss the dialog
                    }
                });

        AlertDialog alertDialog = builder.create();
        if(shouldShowAlert()) {
            alertDialog.show();
            saveAlertTime(System.currentTimeMillis());
        }
    }
    private boolean shouldShowAlert() {
        long currentTime = System.currentTimeMillis();
        long lastAlertTime = getLastAlertTime();

        // Check if more than 15 minutes have passed since the last alert
        return (currentTime - lastAlertTime) > (15 * 60 * 1000);
    }

    private long getLastAlertTime() {
        return sharedPreferences.getLong("lastAlert", 0);
    }

    private void saveAlertTime(long time) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("lastAlert", time);
        editor.apply();
    }

    private void accessGoogleFitData() {


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

        Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .readData(readRequest)
                .addOnCompleteListener(new OnCompleteListener<DataReadResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<DataReadResponse> task) {
                        if (task.isSuccessful()) {
                            // Handle the step count data
                            DataReadResponse dataReadResponse = task.getResult();
                            currSteps = extractStepCount(dataReadResponse);
                            currCalories = (int) extractCalories(dataReadResponse);

                        } else {
                            // Handle failure
                            Log.e("Google Fit", "Failed to retrieve step count data: " + task.getException());
                        }
                    }
                });
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
    private float extractCalories(DataReadResponse dataReadResponse) {
        currCalories=0;
        if (dataReadResponse != null) {
            for (Bucket bucket : dataReadResponse.getBuckets()) {
                for (com.google.android.gms.fitness.data.DataSet dataSet : bucket.getDataSets()) {
                    for (com.google.android.gms.fitness.data.DataPoint dp : dataSet.getDataPoints()) {
                        for (Field field : dp.getDataType().getFields()) {
                            // Check if the value is a float field
                            if (field.getFormat() == Field.FORMAT_FLOAT) {
                                currCalories += dp.getValue(field).asFloat();
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
        return currCalories;
    }
    private List<WorkoutCardModel> getData() {
        List<WorkoutCardModel> data = new ArrayList<>();

        return data;
    }
    private String getDate(){
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Create a SimpleDateFormat for the desired date pattern
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Format the date using the SimpleDateFormat
        String formattedDate = dateFormat.format(currentDate);
        return formattedDate;
    }
    public void appendWorkoutList(String newWorkouts) {
        // Retrieve the existing value from the database
        databaseReferenceWorkouts.child("Workout").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Concatenate the new string with the existing value
                String existingValue = task.getResult().getValue(String.class);
                if(existingValue == null) existingValue="";
                String updatedValue = existingValue + newWorkouts;
                databaseReferenceWorkouts.child("Workout").setValue(updatedValue);
                databaseReferenceWorkouts.child("Calories").setValue(currCalories);
                databaseReferenceWorkouts.child("Steps").setValue(currSteps);

                // Update the value in the database

            } else {
                // Handle the retrieval failure
                System.out.println("Failed to retrieve existing value");
            }
        });
    }
    public static Constants.GENDER convertToConstantFormat(String inputGender) {
        switch (inputGender.toLowerCase()) {
            case "Female":
                return Constants.GENDER.FEMALE;
            default:
                return Constants.GENDER.MALE;
        }
    }
}
