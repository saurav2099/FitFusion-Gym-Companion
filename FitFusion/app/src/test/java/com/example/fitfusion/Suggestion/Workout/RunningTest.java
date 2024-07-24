package com.example.fitfusion.Suggestion.Workout;

import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;
import junit.framework.TestCase;

public class RunningTest extends TestCase {

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

    public void testRunningDurationForUnderweight() {
        // Set the body weight to be underweight
        bodyInfo.setWeight(50);
        Running runningUnderweight = new Running(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        // Assuming the default duration for underweight is 17 minutes
        assertEquals("17", runningUnderweight.getTime());
    }

    public void testRunningDurationForHealthyWeight() {
        // Set the body weight to be healthy
        bodyInfo.setWeight(70);
        Running runningHealthyWeight = new Running(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.MEDIUM);
        // Assuming the default duration for healthy weight is 20 minutes
        assertEquals("20", runningHealthyWeight.getTime());
    }

    public void testRunningDurationForOverweight() {
        // Set the body weight to be overweight
        bodyInfo.setWeight(90);
        Running runningOverweight = new Running(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.HIGH);
        // Assuming the default duration for overweight is 14 minutes
        assertEquals("14", runningOverweight.getTime());
    }

    public void testRunningDurationForObese() {
        // Set the body weight to be obese
        bodyInfo.setWeight(110);
        Running runningObese = new Running(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        // Assuming the default duration for obese is 11 minutes
        assertEquals("11", runningObese.getTime());
    }

    public void testRunningDurationForHighEnergyLevel() {
        // Set the energy level to be high
        bodyInfo.setCurrHeartRate(100);
        Running runningHighEnergy = new Running(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        // Assuming the default duration for high energy level is 20 minutes
        assertEquals("20", runningHighEnergy.getTime());
    }

    public void testRunningDurationForLowEnergyLevel() {
        // Set the energy level to be low
        bodyInfo.setCurrHeartRate(50);
        Running runningLowEnergy = new Running(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.MEDIUM);
        // Assuming the default duration for low energy level is 0 minutes
        assertEquals("0", runningLowEnergy.getTime());
    }

    public void testRunningDurationForOldAge() {
        // Set the age to be in the old age group
        bodyInfo.setAge(70);
        Running runningOldAge = new Running(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.MEDIUM);
        // Assuming no running for old age
        assertEquals("0", runningOldAge.getTime());
    }

    public void testRunningDurationForEqualCalories() {
        // Set current calories to be equal to target calories
        bodyInfo.setCurrCalories(2500);
        Running runningEqualCalories = new Running(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.HIGH);
        // Assuming no running when calories are equal
        assertEquals("0", runningEqualCalories.getTime());
    }
}
