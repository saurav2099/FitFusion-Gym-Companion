package com.example.fitfusion.Suggestion;

public class BodyInfo {
    int age;
    Constants.GENDER gender;
    // height in cm
    int height;
    // weight in kg
    float weight;
    int currHeartRate;
    int avgHeartRate;
    int currRespRate;
    int avgRespRate;
    int currSteps;
    int targetSteps;
    int currCalories;
    int targetCalories;

    // non-primitive data members
    // bmi calculated based on height(cm)/weight(kg)
    Constants.BMI bmi;

    Constants.AGE_GROUP ageGroup;

    Constants.ENERGY_LEVEL energyLevel;

    public BodyInfo(int age, Constants.GENDER gender, int height, int weight, int currHeartRate, int avgHeartRate,
            int currRespRate, int avgRespRate, int currSteps, int targetSteps, int currCalories, int targetCalories) {
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.currHeartRate = currHeartRate;
        this.avgHeartRate = avgHeartRate;
        this.currRespRate = currRespRate;
        this.avgRespRate = avgRespRate;
        this.currSteps = currSteps;
        this.targetSteps = targetSteps;
        this.currCalories = currCalories;
        this.targetCalories = targetCalories;
    }

    // Getter methods for data members
    public Constants.GENDER getGender() {
        return this.gender;
    }

    public int getCurrHeartRate() {
        return this.currHeartRate;
    }

    public int getAvgHeartRate() {
        return this.avgHeartRate;
    }

    public int getCurrSteps() {
        return this.currSteps;
    }

    public int getTargetSteps() {
        return this.targetSteps;
    }

    public int getCurrCalories() {
        return this.currCalories;
    }

    public int getTargetCalories() {
        return this.targetCalories;
    }

    public Constants.BMI getBMI() {
        return this.bmi;
    }

    public Constants.AGE_GROUP getAgeGroup() {
        return this.ageGroup;
    }

    public Constants.ENERGY_LEVEL getEnergyLevel() {
        return this.energyLevel;
    }

    public void setBmi() {
        this.bmi = calculateBmi();
    }

    public void setAgeGroup() {
        this.ageGroup = calculateAgeGroup();
    }

    public void setEnergyLevel() {
        this.energyLevel = calculateEnergyLevel();
    }

    public void setNonPrimitiveMetrics() {
        setBmi();
        setAgeGroup();
        setEnergyLevel();
    }

    // Calculate and set BMI based on weight and height
    public Constants.BMI calculateBmi() {
        double bmi = (weight / (height * height)) * 1e4;

        if (bmi < Constants.UNDER_WEIGHT_THRESHOLD) {
            return Constants.BMI.UNDER_WEIGHT;
        } else if (bmi <= Constants.HEALTHY_WEIGHT_THRESHOLD) {
            return Constants.BMI.HEALTHY_WEIGHT;
        } else if (bmi <= Constants.OVER_WEIGHT_THRESHOLD) {
            return Constants.BMI.OVER_WEIGHT;
        } else {
            return Constants.BMI.OBESE;
        }
    }

    // Calculate and set age group based on age
    public Constants.AGE_GROUP calculateAgeGroup() {
        if (age <= Constants.TEEN_THRESHOLD) {
            return Constants.AGE_GROUP.TEEN;
        } else if (age <= Constants.YOUNG_THRESHOLD) {
            return Constants.AGE_GROUP.YOUNG;
        } else if (age <= Constants.MID_AGED_THRESHOLD) {
            return Constants.AGE_GROUP.MID_AGED;
        } else {
            return Constants.AGE_GROUP.OLD;
        }
    }

    // Calculate and set energy level based on heart rate and calories burnt
    public Constants.ENERGY_LEVEL calculateEnergyLevel() {
        if (this.currHeartRate < Constants.LOW_HEART_RATE_THRESHOLD ||
                this.currCalories > Constants.HIGH_CALORIES_THRESHOLD) {
            return Constants.ENERGY_LEVEL.LOW;
        } else if (this.currHeartRate > Constants.HIGH_HEART_RATE_THRESHOLD ||
                this.currCalories < Constants.LOW_CALORIES_THRESHOLD) {
            return Constants.ENERGY_LEVEL.HIGH;
        } else {
            return Constants.ENERGY_LEVEL.MEDIUM;
        }
    }

    // Setters with setNonPrimitiveMetrics() invocation
    public void setAge(int age) {
        this.age = age;
        setNonPrimitiveMetrics();
    }

    public void setWeight(int weight) {
        this.weight = weight;
        setNonPrimitiveMetrics();
    }

    public void setCurrHeartRate(int currHeartRate) {
        this.currHeartRate = currHeartRate;
        setNonPrimitiveMetrics();
    }

    public void setGender(Constants.GENDER gender) {
        this.gender = gender;
        setNonPrimitiveMetrics();
    }

    public void setHeight(int height) {
        this.height = height;
        setNonPrimitiveMetrics();
    }

    public void setWeight(float weight) {
        this.weight = weight;
        setNonPrimitiveMetrics();
    }

    public void setAvgHeartRate(int avgHeartRate) {
        this.avgHeartRate = avgHeartRate;
        setNonPrimitiveMetrics();
    }

    public void setCurrRespRate(int currRespRate) {
        this.currRespRate = currRespRate;
        setNonPrimitiveMetrics();
    }

    public void setAvgRespRate(int avgRespRate) {
        this.avgRespRate = avgRespRate;
        setNonPrimitiveMetrics();
    }

    public void setCurrSteps(int currSteps) {
        this.currSteps = currSteps;
        setNonPrimitiveMetrics();
    }

    public void setTargetSteps(int targetSteps) {
        this.targetSteps = targetSteps;
        setNonPrimitiveMetrics();
    }

    public void setCurrCalories(int currCalories) {
        this.currCalories = currCalories;
        setNonPrimitiveMetrics();
    }

    public void setTargetCalories(int targetCalories) {
        this.targetCalories = targetCalories;
        setNonPrimitiveMetrics();
    }

    public void setBmi(Constants.BMI bmi) {
        this.bmi = bmi;
        setNonPrimitiveMetrics();
    }

    public void setAgeGroup(Constants.AGE_GROUP ageGroup) {
        this.ageGroup = ageGroup;
        setNonPrimitiveMetrics();
    }

    public void setEnergyLevel(Constants.ENERGY_LEVEL energyLevel) {
        this.energyLevel = energyLevel;
        setNonPrimitiveMetrics();
    }
}