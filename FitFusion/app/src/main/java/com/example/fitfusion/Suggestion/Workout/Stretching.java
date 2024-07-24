package com.example.fitfusion.Suggestion.Workout;

import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;

public class Stretching extends Workout {

    public Stretching(BodyInfo bodyInfo, Constants.LAST_WORKOUT_INTENSITY lastWorkoutIntensity) {
        super(bodyInfo, lastWorkoutIntensity);
    }

    @Override
    public String getName() {
        return Constants.STRETCHING;
    }

    @Override
    public String getTime() {
        // Stretching time is always 7 minutes for everyone
        return "7";
    }

    @Override
    public String getUnit() {
        return Constants.MINUTES_ACRONYM;
    }

    @Override
    public String getBgImg() {
        return Constants.STRETCHING_BG_IMG;
    }
}
