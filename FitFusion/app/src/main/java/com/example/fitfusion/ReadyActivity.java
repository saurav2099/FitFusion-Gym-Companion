package com.example.fitfusion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReadyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready);

        Button startWorkoutBtn = findViewById(R.id.updateGoalsBtn);
        startWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the new activity
                Intent intent = new Intent(ReadyActivity.this, WorkoutActivity.class);
                startActivity(intent);
            }
        });
    }
}