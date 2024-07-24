package com.example.fitfusion.Suggestion.Workout;

import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;

import java.util.Objects;

abstract public class Workout {
    BodyInfo bodyInfo;
    Constants.LAST_WORKOUT_INTENSITY lastWorkoutIntensity;

    Workout(BodyInfo bodyInfo, Constants.LAST_WORKOUT_INTENSITY lastWorkoutIntensity) {
        this.bodyInfo = bodyInfo;
        this.lastWorkoutIntensity = lastWorkoutIntensity;
    }

    public BodyInfo getBodyInfo() {
        return this.bodyInfo;
    }

    public Constants.LAST_WORKOUT_INTENSITY getLastWorkoutIntensity() {
        return lastWorkoutIntensity;
    }

    public WorkoutInfo getWorkoutInfo() {
        if (Objects.equals(getTime(), "0")) {
            return null;
        }
        return new WorkoutInfo(getName(), getTime(), getUnit(), getBgImg());
    }

    abstract public String getName();

    abstract public String getTime();

    abstract public String getUnit();

    abstract public String getBgImg();
}
