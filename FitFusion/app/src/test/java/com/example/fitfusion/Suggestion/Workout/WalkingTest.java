package com.example.fitfusion.Suggestion.Workout;

import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;
import com.example.fitfusion.Suggestion.Workout.Walking;
import junit.framework.TestCase;

public class WalkingTest extends TestCase {

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

    public void testWalkingDurationForTeen() {
        // Set the age to be a teen
        bodyInfo.setAge(16);
        Walking walkingTeen = new Walking(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        // For example, assuming the default duration for teens is 20 minutes
        assertEquals("20", walkingTeen.getWorkoutInfo().getValue());
    }

    public void testWalkingDurationForYoung() {
        // Set the age to be young
        bodyInfo.setAge(25);
        Walking walkingYoung = new Walking(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.MEDIUM);
        // For example, assuming the default duration for young age is 20 minutes
        assertEquals("20", walkingYoung.getWorkoutInfo().getValue());
    }

    public void testWalkingDurationForMidAged() {
        // Set the age to be mid-aged
        bodyInfo.setAge(45);
        Walking walkingMidAged = new Walking(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.HIGH);
        // For example, assuming the default duration for mid-aged is 12 minutes
        assertEquals("12", walkingMidAged.getWorkoutInfo().getValue());
    }

    public void testWalkingDurationForOld() {
        // Set the age to be old
        bodyInfo.setAge(65);
        Walking walkingOld = new Walking(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        // For example, assuming the default duration for old age is 5 minutes
        assertEquals("5", walkingOld.getWorkoutInfo().getValue());
    }

    public void testWalkingDurationForUnderweight() {
        // Set the body weight to be underweight
        bodyInfo.setWeight(50);
        Walking walkingUnderweight = new Walking(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.MEDIUM);
        // For example, assuming the default duration for underweight is 30 minutes
        assertEquals("30", walkingUnderweight.getWorkoutInfo().getValue());
    }

    public void testWalkingDurationForHighEnergyLevel() {
        // Set the energy level to be high
        bodyInfo.setCurrHeartRate(100);
        Walking walkingHighEnergy = new Walking(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        // For example, assuming the default duration for high energy level is 20 minutes
        assertEquals("20", walkingHighEnergy.getWorkoutInfo().getValue());
    }
}
