package com.example.fitfusion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.fitfusion.adapters.ActivityAdapter;
import com.example.fitfusion.dataModel.ActivityHistoryDataModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TrackingActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    String userEmail;
    boolean shown=false;

    ListView listView;

    Map<String, Integer> stepsByDate = new HashMap<>();
    Map<String, Integer> caloriesByDate = new HashMap<>();
    Map<String, String> workoutByDate = new HashMap<>();
    List<ActivityHistoryDataModel> dataList = new ArrayList<>();
    ArrayList<String> dateTracker = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = sanitizeEmailForFirebase(currentUser.getEmail());
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userEmail).child("workoutTime");
        listView = findViewById(R.id.listViewLayout);
        //dataList.add(new ActivityHistoryDataModel("Nov 24", "3,120", "1,785", "Stretching x15min\nPush Ups x15\nStretching x15min\nPush Ups x15\nStretching x15min\nPush Ups x15\n"));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot workoutSnapshot : dataSnapshot.getChildren()) {
                    String key=workoutSnapshot.getKey();
                    dateTracker.add(key);
                    for (DataSnapshot data : workoutSnapshot.getChildren()) {
                        String dataKey=data.getKey();
                        if(dataKey.equals("Steps"))
                            stepsByDate.put(key,Integer.parseInt(data.getValue().toString()));
                        else if(dataKey.equals("Calories"))
                            caloriesByDate.put(key,Integer.parseInt(data.getValue().toString()));
                        else if(dataKey.equals("Workout"))
                            workoutByDate.put(key, (data.getValue().toString()));

                        Log.d("FirebaseData", workoutSnapshot.getKey()+":"+data.getKey() + ": " + data.getValue());
                    }


                    Log.d("FirebaseData", "------------------------");

                }
                if(!shown) {
                    shown=true;
                    onComplete();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });


    }

    private void onComplete() {


        // Create a list of data
        Log.e("Checker","DOne");
        Collections.sort(dateTracker, Comparator.reverseOrder());

        for (String key : dateTracker) {
            String formattedDate = formatDateString(key);
            String cals=String.valueOf(caloriesByDate.get(key));
            String steps=String.valueOf(stepsByDate.get(key));
            String workout=workoutByDate.get(key);
            Log.e("Checker",formattedDate+":"+cals+":"+steps+":"+workout);
            if(cals!=null && !cals.trim().isEmpty()){
                dataList.add(new ActivityHistoryDataModel(formattedDate, cals, steps,workout));
            }


        }
        ActivityAdapter adapter = new ActivityAdapter(this, R.layout.activity_card, dataList);
        listView.setAdapter(adapter);
    }

    public static String sanitizeEmailForFirebase(String email) {
        // Replace invalid characters with underscores
        return email.replaceAll("[.#$\\[\\]]", "_");
    }
    private static String formatDateString(String dateString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = inputFormat.parse(dateString);

            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd", Locale.US);
            return outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
            return dateString; // Return original date string if parsing fails
        }
    }

}