package com.example.fitfusion.Suggestion.Workout;

import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;

public class Resting extends Workout {

    public Resting(BodyInfo bodyInfo, Constants.LAST_WORKOUT_INTENSITY lastWorkoutIntensity) {
        super(bodyInfo, lastWorkoutIntensity);
    }

    public String getName() {
        return Constants.RESTING;
    }

    public String getTime() {
        int restTime = 0;
        switch (getBodyInfo().getBMI()) {
            case UNDER_WEIGHT:
                restTime += 5;
                break;
            case HEALTHY_WEIGHT:
                // No additional rest time for healthy weight
                break;
            case OVER_WEIGHT:
                restTime += 10;
                break;
            case OBESE:
                restTime += 15;
                break;
        }

        switch (getBodyInfo().getAgeGroup()) {
            case TEEN:
                restTime += 5;
                break;
            case YOUNG:
                // No additional rest time for young age
                break;
            case MID_AGED:
                restTime += 10;
                break;
            case OLD:
                restTime += 15;
                break;
        }

        switch (getBodyInfo().getEnergyLevel()) {
            case LOW:
                restTime += 10;
                break;
            case MEDIUM:
                // No additional rest time for medium energy level
                break;
            case HIGH:
                restTime -= 5; // Subtract 5 minutes for high energy level
                break;
        }

        switch (getBodyInfo().getGender()) {
            case MALE:
                // No specific adjustments for male
                break;
            case FEMALE:
                restTime += 5; // Add 5 minutes for females
                break;
        }

        // Cool down time increases as lastWorkoutIntensity becomes high
        if (lastWorkoutIntensity == Constants.LAST_WORKOUT_INTENSITY.HIGH) {
            restTime += 5; // Adjusted to increase by 5 minutes for high intensity
        }

        // Ensure the rest time is within the range of 5 to 20 minutes
        restTime = Math.max(5, Math.min(20, restTime));

        return String.valueOf(restTime);
    }

    @Override
    public String getUnit() {
        return Constants.MINUTES_ACRONYM;
    }

    @Override
    public String getBgImg() {
        return Constants.RESTING_BG_IMG;
    }
}
