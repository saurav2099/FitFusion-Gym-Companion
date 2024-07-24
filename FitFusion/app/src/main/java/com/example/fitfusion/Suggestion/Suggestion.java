package com.example.fitfusion.Suggestion;

import com.example.fitfusion.Suggestion.Workout.CoolDown;
import com.example.fitfusion.Suggestion.Workout.Plank;
import com.example.fitfusion.Suggestion.Workout.PushUps;
import com.example.fitfusion.Suggestion.Workout.Resting;
import com.example.fitfusion.Suggestion.Workout.Running;
import com.example.fitfusion.Suggestion.Workout.Skipping;
import com.example.fitfusion.Suggestion.Workout.Stretching;
import com.example.fitfusion.Suggestion.Workout.Walking;
import com.example.fitfusion.Suggestion.Workout.Workout;
import com.example.fitfusion.Suggestion.Workout.WorkoutInfo;

import java.util.ArrayList;
import java.util.Objects;

public class Suggestion {

    BodyInfo bodyInfo;
    Constants.LAST_WORKOUT_INTENSITY lastWorkoutIntensity;

    public Suggestion(BodyInfo bodyInfo) {
        this.bodyInfo = bodyInfo;
        // considering initially the user is rested
        lastWorkoutIntensity = Constants.LAST_WORKOUT_INTENSITY.LOW;
    }

    public void updateLastWorkoutIntensity(Constants.LAST_WORKOUT_INTENSITY lastWorkoutIntensity) {
        this.lastWorkoutIntensity = lastWorkoutIntensity;
    }

    public Constants.LAST_WORKOUT_INTENSITY getLastWorkoutIntensity() {
        return this.lastWorkoutIntensity;
    }

    private Workout getCoolDown() {
        Workout coolDown = new CoolDown(bodyInfo, getLastWorkoutIntensity());
        if (coolDown.getWorkoutInfo() != null) {
            // update last workout intensity
            updateLastWorkoutIntensity(Constants.LAST_WORKOUT_INTENSITY.LOW);
            return coolDown;
        }
        return coolDown;
    }

    private void checkAndAddWorkout(ArrayList<WorkoutInfo> workouts, WorkoutInfo workoutInfo,
            Constants.LAST_WORKOUT_INTENSITY lastWorkoutIntensity) {
        if (workoutInfo != null) {
            workouts.add(workoutInfo);
            updateLastWorkoutIntensity(lastWorkoutIntensity);
        }
    }

    public ArrayList<WorkoutInfo> getSuggestions() {
        // set non-primitive Metrics of BodyInfo
        bodyInfo.setNonPrimitiveMetrics();

        ArrayList<WorkoutInfo> workouts = new ArrayList<>();
        if (bodyInfo.getEnergyLevel() == Constants.ENERGY_LEVEL.LOW
                || bodyInfo.getCurrHeartRate() >= 2 * bodyInfo.getAvgHeartRate()) {
            updateLastWorkoutIntensity(Constants.LAST_WORKOUT_INTENSITY.HIGH);
        }

        Workout workout;

        // if user is already tired or currHeartRate is high, give cool down
        workout = getCoolDown();
        checkAndAddWorkout(workouts, workout.getWorkoutInfo(), Constants.LAST_WORKOUT_INTENSITY.LOW);

        workout = new Stretching(bodyInfo, getLastWorkoutIntensity());
        checkAndAddWorkout(workouts, workout.getWorkoutInfo(), Constants.LAST_WORKOUT_INTENSITY.LOW);

        // add Push ups if user is under weight or healthy, else add running workout
        if (bodyInfo.getBMI() == Constants.BMI.UNDER_WEIGHT || bodyInfo.getBMI() == Constants.BMI.HEALTHY_WEIGHT) {
            workout = new PushUps(bodyInfo, getLastWorkoutIntensity());
            checkAndAddWorkout(workouts, workout.getWorkoutInfo(), Constants.LAST_WORKOUT_INTENSITY.HIGH);
        } else {
            workout = new Running(bodyInfo, getLastWorkoutIntensity());
            checkAndAddWorkout(workouts, workout.getWorkoutInfo(), Constants.LAST_WORKOUT_INTENSITY.HIGH);
        }

        // add cool down
        workout = getCoolDown();
        checkAndAddWorkout(workouts, workout.getWorkoutInfo(), Constants.LAST_WORKOUT_INTENSITY.LOW);

        // add plank if user is not old or low in energy level
        if (bodyInfo.getAgeGroup() != Constants.AGE_GROUP.OLD
                && bodyInfo.getEnergyLevel() != Constants.ENERGY_LEVEL.LOW) {
            workout = new Plank(bodyInfo, getLastWorkoutIntensity());
            checkAndAddWorkout(workouts, workout.getWorkoutInfo(), Constants.LAST_WORKOUT_INTENSITY.HIGH);
        } else {
            workout = new Walking(bodyInfo, getLastWorkoutIntensity());
            checkAndAddWorkout(workouts, workout.getWorkoutInfo(), Constants.LAST_WORKOUT_INTENSITY.MEDIUM);
        }

        // add cool down
        workout = getCoolDown();
        checkAndAddWorkout(workouts, workout.getWorkoutInfo(), Constants.LAST_WORKOUT_INTENSITY.LOW);

        // add skipping workout
        workout = new Skipping(bodyInfo, getLastWorkoutIntensity());
        checkAndAddWorkout(workouts, workout.getWorkoutInfo(), Constants.LAST_WORKOUT_INTENSITY.HIGH);

        // add resting only if last workout was not cool down
        if (workouts.size() > 0 && !Objects.equals(workouts.get(workouts.size() - 1).getName(), Constants.COOL_DOWN)) {
            workout = new Resting(bodyInfo, getLastWorkoutIntensity());
            checkAndAddWorkout(workouts, workout.getWorkoutInfo(), Constants.LAST_WORKOUT_INTENSITY.LOW);
        }

        return workouts;
    }

}