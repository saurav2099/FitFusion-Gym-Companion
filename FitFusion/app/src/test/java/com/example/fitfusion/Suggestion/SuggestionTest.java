package com.example.fitfusion.Suggestion;

import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;
import com.example.fitfusion.Suggestion.Suggestion;
import com.example.fitfusion.Suggestion.Workout.WorkoutInfo;
import junit.framework.TestCase;

import java.util.ArrayList;

public class SuggestionTest extends TestCase {

    private BodyInfo restedBodyInfo;
    private BodyInfo highEnergyBodyInfo;

    @Override
    protected void setUp() throws Exception {
        // Set up a rested BodyInfo object for testing
        restedBodyInfo = new BodyInfo(
                25,                     // Age
                Constants.GENDER.MALE, // Gender
                175,                    // Height
                70,                     // Weight
                70,                     // Current Heart Rate
                65,                     // Average Heart Rate
                18,                     // Current Respiration Rate
                16,                     // Average Respiration Rate
                8000,                   // Current Steps
                12000,                  // Target Steps
                1800,                   // Current Calories
                2000                    // Target Calories
        );
        restedBodyInfo.setNonPrimitiveMetrics();

        // Set up a high energy BodyInfo object for testing
        highEnergyBodyInfo = new BodyInfo(
                30,                     // Age
                Constants.GENDER.FEMALE, // Gender
                160,                    // Height
                60,                     // Weight
                120,                    // Current Heart Rate
                100,                    // Average Heart Rate
                20,                     // Current Respiration Rate
                18,                     // Average Respiration Rate
                10000,                  // Current Steps
                15000,                  // Target Steps
                2500,                   // Current Calories
                3000                    // Target Calories
        );
        highEnergyBodyInfo.setEnergyLevel();
    }

    public void testGetSuggestionsRested() {
        Suggestion suggestion = new Suggestion(restedBodyInfo);
        ArrayList<WorkoutInfo> suggestions = suggestion.getSuggestions();

        // Rested state should have cool down, running, walking, skipping, and resting
        assertEquals(7, suggestions.size());

        // Check the order of workouts
        assertEquals(Constants.COOL_DOWN, suggestions.get(0).getName());
        assertEquals(Constants.RUNNING, suggestions.get(1).getName());
        assertEquals(Constants.COOL_DOWN, suggestions.get(2).getName());
        assertEquals(Constants.WALKING, suggestions.get(3).getName());
        assertEquals(Constants.COOL_DOWN, suggestions.get(4).getName());
    }

    public void testGetSuggestionsHighEnergy() {
        Suggestion suggestion = new Suggestion(highEnergyBodyInfo);
        ArrayList<WorkoutInfo> suggestions = suggestion.getSuggestions();

        // High energy state should have cool down, running, cool down, walking, skipping, and resting
        assertEquals(7, suggestions.size());

        // Check the order of workouts
        assertEquals(Constants.COOL_DOWN, suggestions.get(0).getName());
        assertEquals(Constants.RUNNING, suggestions.get(1).getName());
        assertEquals(Constants.COOL_DOWN, suggestions.get(2).getName());
        assertEquals(Constants.WALKING, suggestions.get(3).getName());
        assertEquals(Constants.COOL_DOWN, suggestions.get(4).getName());
        assertEquals(Constants.SKIPPING, suggestions.get(5).getName());
    }

    public void testUpdateLastWorkoutIntensity() {
        Suggestion suggestion = new Suggestion(restedBodyInfo);

        // Initially, last workout intensity should be LOW
        assertEquals(Constants.LAST_WORKOUT_INTENSITY.LOW, suggestion.getLastWorkoutIntensity());

        // Update to MEDIUM
        suggestion.updateLastWorkoutIntensity(Constants.LAST_WORKOUT_INTENSITY.MEDIUM);
        assertEquals(Constants.LAST_WORKOUT_INTENSITY.MEDIUM, suggestion.getLastWorkoutIntensity());

        // Update to HIGH
        suggestion.updateLastWorkoutIntensity(Constants.LAST_WORKOUT_INTENSITY.HIGH);
        assertEquals(Constants.LAST_WORKOUT_INTENSITY.HIGH, suggestion.getLastWorkoutIntensity());
    }

    // Add more test cases as needed

    public void testGetSuggestionsWithHighHeartRate() {
        // Test scenario with high heart rate
        highEnergyBodyInfo.setCurrHeartRate(150);
        Suggestion suggestion = new Suggestion(highEnergyBodyInfo);
        ArrayList<WorkoutInfo> suggestions = suggestion.getSuggestions();

        // High heart rate should result in cool down and running
        assertEquals(7, suggestions.size());

        // Check the order of workouts
        assertEquals(Constants.COOL_DOWN, suggestions.get(0).getName());
        assertEquals(Constants.RUNNING, suggestions.get(1).getName());
    }

    public void testGetSuggestionsWithLowEnergy() {
        // Test scenario with low energy
        restedBodyInfo.setCurrCalories(1200);
        Suggestion suggestion = new Suggestion(restedBodyInfo);
        ArrayList<WorkoutInfo> suggestions = suggestion.getSuggestions();

        // Low energy should result in cool down, running, walking, and resting
        assertEquals(7, suggestions.size());

        // Check the order of workouts
        assertEquals(Constants.COOL_DOWN, suggestions.get(0).getName());
        assertEquals(Constants.RUNNING, suggestions.get(1).getName());
        assertEquals(Constants.COOL_DOWN, suggestions.get(2).getName());
        assertEquals(Constants.WALKING, suggestions.get(3).getName());
    }

    public void testGetSuggestionsWithHighEnergyAndHighHeartRate() {
        // Test scenario with high energy and high heart rate
        highEnergyBodyInfo.setCurrHeartRate(160);
        Suggestion suggestion = new Suggestion(highEnergyBodyInfo);
        ArrayList<WorkoutInfo> suggestions = suggestion.getSuggestions();

        // High energy and high heart rate should result in cool down, running, cool down, walking, skipping, and resting
        assertEquals(7, suggestions.size());

        // Check the order of workouts
        assertEquals(Constants.COOL_DOWN, suggestions.get(0).getName());
        assertEquals(Constants.RUNNING, suggestions.get(1).getName());
        assertEquals(Constants.COOL_DOWN, suggestions.get(2).getName());
        assertEquals(Constants.WALKING, suggestions.get(3).getName());
        assertEquals(Constants.COOL_DOWN, suggestions.get(4).getName());
        assertEquals(Constants.SKIPPING, suggestions.get(5).getName());
    }

    public void testGetSuggestionsWithTargetCaloriesReached() {
        // Test scenario where target calories are reached
        restedBodyInfo.setCurrCalories(restedBodyInfo.getTargetCalories());
        Suggestion suggestion = new Suggestion(restedBodyInfo);
        ArrayList<WorkoutInfo> suggestions = suggestion.getSuggestions();

        // Target calories reached should result in cool down and resting
        assertEquals(7, suggestions.size());

        // Check the order of workouts
        assertEquals(Constants.COOL_DOWN, suggestions.get(0).getName());
        assertEquals(Constants.RUNNING, suggestions.get(1).getName());
    }
}
