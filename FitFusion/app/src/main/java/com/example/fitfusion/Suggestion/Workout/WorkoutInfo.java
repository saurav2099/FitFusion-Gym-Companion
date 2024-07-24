package com.example.fitfusion.Suggestion.Workout;

public class WorkoutInfo {
    String name;
    String value;
    String unit;
    String bgImg;

    public WorkoutInfo(String name, String value, String unit, String bgImg) {
        this.name = name;
        this.value = value;
        this.unit = unit;
        this.bgImg = bgImg;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getBgImg() {
        return bgImg;
    }

    public String getUnit() {
        return unit;
    }

}
