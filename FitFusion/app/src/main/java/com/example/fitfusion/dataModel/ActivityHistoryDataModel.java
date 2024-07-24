package com.example.fitfusion.dataModel;

public class ActivityHistoryDataModel {

    private String date;
    private String calories;
    private String steps;
    private String details;

    public ActivityHistoryDataModel(String date, String calories, String steps, String details) {
        this.date = date;
        this.calories = calories;
        this.steps = steps;
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
