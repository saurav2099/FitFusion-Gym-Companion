package com.example.fitfusion.Suggestion.Workout;

import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;

import java.util.Objects;

public class Walking extends Workout {
    public Walking(BodyInfo bodyInfo, Constants.LAST_WORKOUT_INTENSITY lastWorkoutIntensity) {
        super(bodyInfo, lastWorkoutIntensity);
    }

    public String getName() {
        return Constants.WALKING;
    }

    public String getTime() {
        int walkingTime = 30; // Default walking time (adjusted to the maximum)

        // Adjust walking time based on conditions
        switch (getBodyInfo().getAgeGroup()) {
            case TEEN:
                // No adjustments for teens
                break;
            case YOUNG:
                // No adjustments for young age
                break;
            case MID_AGED:
                walkingTime -= 8; // Adjusted to reduce by 8 minutes for mid-aged
                break;
            case OLD:
                walkingTime -= 15; // Adjusted to reduce by 15 minutes for old people
                break;
        }

        switch (getBodyInfo().getBMI()) {
            case UNDER_WEIGHT:
                // No adjustments for underweight
                break;
            case HEALTHY_WEIGHT:
                // No adjustments for healthy weight
                break;
            case OVER_WEIGHT:
                walkingTime -= 10; // Adjusted to reduce by 10 minutes for overweight
                break;
            case OBESE:
                walkingTime -= 15; // Adjusted to reduce by 15 minutes for obese
                break;
        }

        switch (getBodyInfo().getEnergyLevel()) {
            case LOW:
                walkingTime -= 12; // Adjusted to reduce by 12 minutes for low energy level
                break;
            case MEDIUM:
                // No adjustments for medium energy level
                break;
            case HIGH:
                // No adjustments for high energy level
                break;
        }

        // Walking time is 0 if CurrCalories is equal to targetCalories
        if (getBodyInfo().getCurrCalories() == getBodyInfo().getTargetCalories()) {
            walkingTime = 0;
        }

        // Walking time is 0 if currSteps == targetSteps
        if (getBodyInfo().getCurrSteps() == getBodyInfo().getTargetSteps()) {
            walkingTime = 0;
        }

        // Ensure the walking time is within the range of 0 to 30 minutes
        walkingTime = Math.max(0, walkingTime);

        return String.valueOf(walkingTime);
    }

    @Override
    public String getUnit() {
        return Constants.MINUTES_ACRONYM;
    }

    @Override
    public String getBgImg() {
        return Constants.WALKING_BG_IMG;
    }
}
