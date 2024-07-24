package com.example.fitfusion.Suggestion.Workout;

import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;
import com.example.fitfusion.Suggestion.Workout.Running;
import junit.framework.TestCase;

public class WorkoutTest extends TestCase {

    private BodyInfo bodyInfo;

    @Override
    protected void setUp() throws Exception {
        // Set up a BodyInfo object with relevant information for testing
        bodyInfo = new BodyInfo(
                30,                      // Age
                Constants.GENDER.FEMALE, // Gender
                160,                     // Height
                65,                      // Weight
                70,                      // Current Heart Rate
                65,                      // Average Heart Rate
                18,                      // Current Respiration Rate
                16,                      // Average Respiration Rate
                8000,                    // Current Steps
                12000,                   // Target Steps
                1800,                    // Current Calories
                2000                     // Target Calories
        );
        bodyInfo.setNonPrimitiveMetrics();
    }

    public void testRunningTimeForUnderweight() {
        // Set the body weight to be underweight
        bodyInfo.setWeight(50);
        Running runningUnderweight = new Running(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.MEDIUM);
        // For example, assuming the default time for underweight is 17 minutes
        assertEquals("17", runningUnderweight.getWorkoutInfo().getValue());
    }

    public void testRunningTimeForHighEnergyLevel() {
        // Set the energy level to be high
        bodyInfo.setCurrHeartRate(100);
        Running runningHighEnergy = new Running(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        // For example, assuming the default time for high energy level is 11 minutes
        assertEquals("11", runningHighEnergy.getWorkoutInfo().getValue());
    }
}
