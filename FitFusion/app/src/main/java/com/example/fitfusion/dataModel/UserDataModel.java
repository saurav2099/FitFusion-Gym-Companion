package com.example.fitfusion.dataModel;

public class UserDataModel {
    private int height;
    private int currentWeight;
    private int age;
    private int targetWeight;
    private int targetCalories;
    private int targetSteps;
    private String gender;

    public UserDataModel() {
        // Required default constructor for Firebase
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(int targetWeight) {
        this.targetWeight = targetWeight;
    }

    public int getTargetCalories() {
        return targetCalories;
    }

    public void setTargetCalories(int targetCalories) {
        this.targetCalories = targetCalories;
    }

    public int getTargetSteps() {
        return targetSteps;
    }

    public void setTargetSteps(int targetSteps) {
        this.targetSteps = targetSteps;
    }
}
