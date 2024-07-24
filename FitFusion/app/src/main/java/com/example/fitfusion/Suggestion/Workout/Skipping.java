package com.example.fitfusion.Suggestion.Workout;

import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;

import java.util.Objects;

public class Skipping extends Workout {

    public Skipping(BodyInfo bodyInfo, Constants.LAST_WORKOUT_INTENSITY lastWorkoutIntensity) {
        super(bodyInfo, lastWorkoutIntensity);
    }

    public String getName() {
        return Constants.SKIPPING;
    }

    public String getTime() {
        int skippingTime = 15; // Default skipping time (adjusted to the maximum)

        // Adjust skipping time based on conditions
        switch (getBodyInfo().getAgeGroup()) {
            case TEEN:
                // No adjustments for teens
                break;
            case YOUNG:
                // No adjustments for young age
                break;
            case MID_AGED:
                skippingTime -= 5; // Adjusted to reduce by 5 minutes for mid-aged
                break;
            case OLD:
                skippingTime = 0; // Adjusted to reduce to 0 minutes for old people
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
                skippingTime -= 5; // Adjusted to reduce by 5 minutes for overweight
                break;
            case OBESE:
                skippingTime -= 10; // Skipping time is 0 for obese
                break;
        }

        switch (getBodyInfo().getEnergyLevel()) {
            case LOW:
                skippingTime -= 8; // Adjusted to reduce by 8 minutes for low energy level
                break;
            case MEDIUM:
                // No adjustments for medium energy level
                break;
            case HIGH:
                // No adjustments for high energy level
                break;
        }

        // Skipping time is 0 if CurrCalories is equal to targetCalories
        if (getBodyInfo().getCurrCalories() == getBodyInfo().getTargetCalories()) {
            skippingTime = 0;
        }

        // Women do less skipping than men
        if (getBodyInfo().getGender() == Constants.GENDER.FEMALE) {
            skippingTime -= 3; // Adjusted to reduce by 3 minutes for women
        }

        // Ensure the skipping time is within the range of 0 to 15 minutes
        skippingTime = Math.max(0, skippingTime);

        return String.valueOf(skippingTime);
    }

    @Override
    public String getUnit() {
        return Constants.MINUTES_ACRONYM;
    }

    @Override
    public String getBgImg() {
        return Constants.SKIPPING_BG_IMG;
    }
}
