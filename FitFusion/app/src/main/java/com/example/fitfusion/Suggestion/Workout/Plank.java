package com.example.fitfusion.Suggestion.Workout;

import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;

public class Plank extends Workout {

    public Plank(BodyInfo bodyInfo, Constants.LAST_WORKOUT_INTENSITY lastWorkoutIntensity) {
        super(bodyInfo, lastWorkoutIntensity);
    }

    public String getName() {
        return Constants.PLANK;
    }

    public String getTime() {
        int runningTime = 120; // Default plank time (adjusted to the maximum)

        // Adjust running time based on conditions
        switch (getBodyInfo().getBMI()) {
            case UNDER_WEIGHT:
                runningTime -= 60; // Adjusted to reduce by 30 second
                break;
            case HEALTHY_WEIGHT:
                // No adjustments for healthy weight
                break;
            case OVER_WEIGHT:
            case OBESE:
                runningTime -= 90; // Adjusted to reduce by 90 secs
                break;
            // Adjusted to reduce by 9 minutes
        }

        switch (getBodyInfo().getAgeGroup()) {
            case TEEN:
                runningTime -= 10; // Adjusted to reduce by 10 secs
                break;
            case YOUNG:
                // No adjustments for young age
                break;
            case MID_AGED:
                runningTime -= 60; // Adjusted to reduce by 60 sec
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
                // No adjustments for medium energy level
                break;
        }

        switch (getBodyInfo().getGender()) {
            case MALE:
                // No specific adjustments for males
                break;
            case FEMALE:
                runningTime -= 10; // Adjusted to reduce by 10 Seconds for females
                break;
        }

        // Ensure the plank time is within the range of 0 to 120 sec
        runningTime = Math.max(0, runningTime);

        return String.valueOf(runningTime);
    }

    @Override
    public String getUnit() {
        return Constants.SECONDS_ACRONYM;
    }

    @Override
    public String getBgImg() {
        return Constants.PLANK_BG_IMG;
    }
}
