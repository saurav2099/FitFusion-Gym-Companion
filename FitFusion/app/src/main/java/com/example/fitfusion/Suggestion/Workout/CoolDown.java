package com.example.fitfusion.Suggestion.Workout;

import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;

public class CoolDown extends Workout {

    public CoolDown(BodyInfo bodyInfo, Constants.LAST_WORKOUT_INTENSITY lastWorkoutIntensity) {
        super(bodyInfo, lastWorkoutIntensity);
    }

    @Override
    public String getName() {
        return Constants.COOL_DOWN;
    }

    @Override
    public String getTime() {
        int coolDownTime = 3; // Default cool down time (adjusted to the maximum)

        // Adjust cool down time based on conditions
        switch (getBodyInfo().getAgeGroup()) {
            case TEEN:
                // No adjustments for teens
                break;
            case YOUNG:
                // No adjustments for young age
                break;
            case MID_AGED:
                coolDownTime += 3; // Adjusted to increase by 5 minutes for mid-aged
                break;
            case OLD:
                coolDownTime += 5; // Adjusted to increase by 10 minutes for old people
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
                coolDownTime += 3; // Adjusted to increase by 5 minutes for overweight
                break;
            case OBESE:
                coolDownTime += 4; // Adjusted to increase by 10 minutes for obese
                break;
        }

        switch (getBodyInfo().getEnergyLevel()) {
            case LOW:
                coolDownTime += 3; // Adjusted to increase by 8 minutes for low energy level
                break;
            case MEDIUM:
                // No adjustments for medium energy level
                break;
            case HIGH:
                // No adjustments for high energy level
                break;
        }

        // Cool down time increases as lastWorkoutIntensity becomes high
        if (getLastWorkoutIntensity() == Constants.LAST_WORKOUT_INTENSITY.HIGH) {
            coolDownTime += 3; // Adjusted to increase by 5 minutes for high intensity
        }

        // Cool down is 0 if lastWorkoutIntensity was low
        if (getLastWorkoutIntensity() == Constants.LAST_WORKOUT_INTENSITY.LOW) {
            coolDownTime = 0;
        }
        return String.valueOf(coolDownTime);
    }

    @Override
    public String getUnit() {
        return Constants.MINUTES_ACRONYM;
    }

    @Override
    public String getBgImg() {
        return Constants.COOL_DOWN_BG_IMG;
    }
}
