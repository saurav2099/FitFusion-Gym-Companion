package com.example.fitfusion.Suggestion.Workout;

import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;

public class PushUps extends Workout {

    public PushUps(BodyInfo bodyInfo, Constants.LAST_WORKOUT_INTENSITY lastWorkoutIntensity) {
        super(bodyInfo, lastWorkoutIntensity);
    }

    @Override
    public String getName() {
        return Constants.PUSH_UPS;
    }

    @Override
    public String getTime() {
        int pushUpsCount = 50; // Ideal push-ups count

        // Adjust push-ups count based on conditions
        switch (getBodyInfo().getAgeGroup()) {
            case TEEN:
                // No adjustments for teens
                break;
            case YOUNG:
                // No adjustments for young age
                break;
            case MID_AGED:
            case OLD:
                // No push-ups for older people
                // No push-ups for mid-aged
                pushUpsCount = 0;
                break;
        }

        // Women do fewer push-ups than men
        if (getBodyInfo().getGender() == Constants.GENDER.FEMALE) {
            pushUpsCount /= 2; // Adjusted to half for women
        }

        // Adjust push-ups count based on energy level
        switch (getBodyInfo().getEnergyLevel()) {
            case LOW:
                pushUpsCount /= 2; // Adjusted to half for low energy level
                break;
            case MEDIUM:
                pushUpsCount /= 1.5;
                break;
            case HIGH:
                // No adjustments for high energy level
                break;
        }

        // Obese people do fewer push-ups
        if (getBodyInfo().getBMI() == Constants.BMI.OBESE) {
            pushUpsCount /= 2.5; // Adjusted to half for obese people
        }

        // Ensure the push-ups count is within a reasonable range
        pushUpsCount = Math.max(0, pushUpsCount);

        return String.valueOf(pushUpsCount);
    }

    @Override
    public String getUnit() {
        return Constants.COUNT;
    }

    @Override
    public String getBgImg() {
        return Constants.PUSH_UPS_BG_IMG;
    }
}
