package com.example.fitfusion.Suggestion;

public class Constants {
    // Constants for BMI categories
    public static final float UNDER_WEIGHT_THRESHOLD = 18.5F;
    public static final float HEALTHY_WEIGHT_THRESHOLD = 24.9F;
    public static final float OVER_WEIGHT_THRESHOLD = 29.9F;

    // Constants for age groups
    public static final int TEEN_THRESHOLD = 18;
    public static final int YOUNG_THRESHOLD = 35;
    public static final int MID_AGED_THRESHOLD = 50;

    // Constants for energy levels
    public static final int LOW_HEART_RATE_THRESHOLD = 60;
    public static final int HIGH_HEART_RATE_THRESHOLD = 100;
    public static final int LOW_CALORIES_THRESHOLD = 700;
    public static final int HIGH_CALORIES_THRESHOLD = 1500;

    // Workout Label Texts and background images
    public static final String COOL_DOWN = "Cool Down";
    public static final String COOL_DOWN_BG_IMG = "wo_cooldown";

    public static final String RESTING = "Resting";
    public static final String RESTING_BG_IMG = "wo_resting";

    public static final String RUNNING = "Running";
    public static final String RUNNING_BG_IMG = "wo_running";

    public static final String SKIPPING = "Skipping";
    public static final String SKIPPING_BG_IMG = "wo_skipping";

    public static final String WALKING = "Walking";
    public static final String WALKING_BG_IMG = "wo_walking";

    public static final String PUSH_UPS = "Push Ups";
    public static final String PUSH_UPS_BG_IMG = "wo_pushups";

    public static final String STRETCHING = "Stretching";
    public static final String STRETCHING_BG_IMG = "wo_stretching";

    public static final String PLANK = "Plank";
    public static final String PLANK_BG_IMG = "wo_plank";

    public static final String MINUTES_ACRONYM = "mins";

    public static final String SECONDS_ACRONYM = "sec";
    public static final String COUNT = "count";

    // BMI categories
    public enum BMI {
        UNDER_WEIGHT,
        HEALTHY_WEIGHT,
        OVER_WEIGHT,
        OBESE
    }

    // Age groups
    public enum AGE_GROUP {
        TEEN,
        YOUNG,
        MID_AGED,
        OLD
    }

    // Energy levels
    public enum ENERGY_LEVEL {
        LOW,
        MEDIUM,
        HIGH
    }

    public enum GENDER {
        MALE,
        FEMALE
    }

    public enum LAST_WORKOUT_INTENSITY {
        LOW,
        MEDIUM,
        HIGH
    }
}