package com.example.fitfusion.Suggestion.Workout;

import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;

import java.util.Objects;

public class Running extends Workout {

    public Running(BodyInfo bodyInfo, Constants.LAST_WORKOUT_INTENSITY lastWorkoutIntensity) {
        super(bodyInfo, lastWorkoutIntensity);
    }

    public String getName() {
        return Constants.RUNNING;
    }

    public String getTime() {
        int runningTime = 20; // Default running time (adjusted to the maximum)

        // Adjust running time based on conditions
        switch (getBodyInfo().getBMI()) {
            case UNDER_WEIGHT:
                runningTime -= 3; // Adjusted to reduce by 3 minutes
                break;
            case HEALTHY_WEIGHT:
                // No adjustments for healthy weight
                break;
            case OVER_WEIGHT:
                runningTime -= 6; // Adjusted to reduce by 6 minutes
                break;
            case OBESE:
                runningTime -= 9; // Adjusted to reduce by 9 minutes
                break;
        }

        switch (getBodyInfo().getAgeGroup()) {
            case TEEN:
                runningTime -= 3; // Adjusted to reduce by 3 minutes
                break;
            case YOUNG:
                // No adjustments for young age
                break;
            case MID_AGED:
                runningTime -= 6; // Adjusted to reduce by 6 minutes
                break;
            case OLD:
                runningTime = 0; // No running for old people
                break;
        }

        switch (getBodyInfo().getEnergyLevel()) {
            case LOW:
                runningTime = 0; // No running for low energy level
                break;
            case MEDIUM:
                // No adjustments for medium energy level
                break;
            case HIGH:
                runningTime -= 3; // Adjusted to reduce by 3 minutes for high energy level
                break;
        }

        switch (getBodyInfo().getGender()) {
            case MALE:
                // No specific adjustments for males
                break;
            case FEMALE:
                runningTime -= 3; // Adjusted to reduce by 3 minutes for females
                break;
        }

        // Ensure the running time is within the range of 0 to 20 minutes
        runningTime = Math.max(0, runningTime);

        // No running if CurrCalories is equal to targetCalories
        if (getBodyInfo().getCurrCalories() == getBodyInfo().getTargetCalories()) {
            runningTime = 0;
        }

        return String.valueOf(runningTime);
    }

    @Override
    public String getUnit() {
        return Constants.MINUTES_ACRONYM;
    }

    @Override
    public String getBgImg() {
        return Constants.RUNNING_BG_IMG;
    }
}
