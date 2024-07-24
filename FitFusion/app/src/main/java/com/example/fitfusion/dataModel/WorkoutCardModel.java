package com.example.fitfusion.dataModel;

public class WorkoutCardModel {
    private int imageResourceId;
    private String time;
    private String workout;
    private String unit;
    private boolean completed;




    public WorkoutCardModel(int imageResourceId, String time, String workout,boolean completed,String unit) {
        this.imageResourceId = imageResourceId;
        this.time = time;
        this.workout = workout;
        this.completed = completed;
        this.unit=unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWorkout() {
        return workout;
    }

    public void setWorkout(String workout) {
        this.workout = workout;
    }

}
