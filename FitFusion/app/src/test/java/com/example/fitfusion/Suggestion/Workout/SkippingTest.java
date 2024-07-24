package com.example.fitfusion.Suggestion.Workout;

import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;
import com.example.fitfusion.Suggestion.Workout.Skipping;
import junit.framework.TestCase;

public class SkippingTest extends TestCase {

    private BodyInfo bodyInfo;

    @Override
    protected void setUp() throws Exception {
        // Set up a BodyInfo object with relevant information for testing
        bodyInfo = new BodyInfo(
                25,                      // Age
                Constants.GENDER.MALE,   // Gender
                175,                     // Height
                70,                      // Weight
                75,                      // Current Heart Rate
                70,                      // Average Heart Rate
                16,                      // Current Respiration Rate
                15,                      // Average Respiration Rate
                5000,                    // Current Steps
                10000,                   // Target Steps
                2000,                    // Current Calories
                2500                     // Target Calories
        );
        bodyInfo.setNonPrimitiveMetrics();
    }

    public void testSkippingDurationForUnderweight() {
        // Set the body weight to be underweight
        bodyInfo.setWeight(50);
        Skipping skippingUnderweight = new Skipping(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        // For example, assuming the default duration for underweight is 15 minutes
        assertEquals("15", skippingUnderweight.getWorkoutInfo().getValue());
    }

    public void testSkippingDurationForHealthyWeight() {
        // Set the body weight to be healthy
        bodyInfo.setWeight(70);
        Skipping skippingHealthyWeight = new Skipping(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.MEDIUM);
        // For example, assuming the default duration for healthy weight is 15 minutes
        assertEquals("15", skippingHealthyWeight.getWorkoutInfo().getValue());
    }

    public void testSkippingDurationForOverweight() {
        // Set the body weight to be overweight
        bodyInfo.setWeight(90);
        Skipping skippingOverweight = new Skipping(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.HIGH);
        // For example, assuming the default duration for overweight is 10 minutes
        assertEquals("10", skippingOverweight.getWorkoutInfo().getValue());
    }

    public void testSkippingDurationForObese() {
        // Set the body weight to be obese
        bodyInfo.setWeight(110);
        Skipping skippingObese = new Skipping(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        // For example, assuming the default duration for obese is 5 minutes
        assertEquals("5", skippingObese.getWorkoutInfo().getValue());
    }

    public void testSkippingDurationForHighEnergyLevel() {
        // Set the energy level to be high
        bodyInfo.setCurrHeartRate(100);
        Skipping skippingHighEnergy = new Skipping(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        // For example, assuming the default duration for high energy level is 15 minutes
        assertEquals("15", skippingHighEnergy.getWorkoutInfo().getValue());
    }

    public void testSkippingDurationForLowEnergyLevel() {
        // Set the energy level to be low
        bodyInfo.setCurrHeartRate(50);
        Skipping skippingLowEnergy = new Skipping(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.MEDIUM);
        // For example, assuming the default duration for low energy level is 7 minutes
        assertEquals("7", skippingLowEnergy.getWorkoutInfo().getValue());
    }

}
