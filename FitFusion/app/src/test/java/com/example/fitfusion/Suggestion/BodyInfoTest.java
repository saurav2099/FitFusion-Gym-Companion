package com.example.fitfusion.Suggestion;

import junit.framework.TestCase;

public class BodyInfoTest extends TestCase {

    private BodyInfo bodyInfo;

    @Override
    protected void setUp() throws Exception {
        // Set up a default BodyInfo object for testing
        bodyInfo = new BodyInfo(
                25,                      // Age
                Constants.GENDER.MALE,  // Gender
                175,                     // Height
                70,                      // Weight
                70,                      // Current Heart Rate
                65,                      // Average Heart Rate
                18,                      // Current Respiration Rate
                16,                      // Average Respiration Rate
                8000,                    // Current Steps
                12000,                   // Target Steps
                1800,                    // Current Calories
                2000                     // Target Calories
        );
        bodyInfo.setNonPrimitiveMetrics();
    }

    public void testSetBmiUnderweight() {
        // Set weight to underweight
        bodyInfo.setWeight(50);
        bodyInfo.setBmi();
        assertEquals(Constants.BMI.UNDER_WEIGHT, bodyInfo.getBMI());
    }

    public void testSetBmiHealthyWeight() {
        // Set weight to healthy range
        bodyInfo.setWeight(70);
        bodyInfo.setBmi();
        assertEquals(Constants.BMI.HEALTHY_WEIGHT, bodyInfo.getBMI());
    }

    public void testSetBmiOverweight() {
        // Set weight to overweight
        bodyInfo.setWeight(85);
        bodyInfo.setBmi();
        assertEquals(Constants.BMI.OVER_WEIGHT, bodyInfo.getBMI());
    }

    public void testSetBmiObese() {
        // Set weight to obese
        bodyInfo.setWeight(100);
        bodyInfo.setBmi();
        assertEquals(Constants.BMI.OBESE, bodyInfo.getBMI());
    }

    public void testSetAgeGroupTeen() {
        // Set age to teen
        bodyInfo.setAge(16);
        bodyInfo.setAgeGroup();
        assertEquals(Constants.AGE_GROUP.TEEN, bodyInfo.getAgeGroup());
    }

    public void testSetAgeGroupYoung() {
        // Set age to young
        bodyInfo.setAge(25);
        bodyInfo.setAgeGroup();
        assertEquals(Constants.AGE_GROUP.YOUNG, bodyInfo.getAgeGroup());
    }

    public void testSetAgeGroupMidAged() {
        // Set age to mid-aged
        bodyInfo.setAge(45);
        bodyInfo.setAgeGroup();
        assertEquals(Constants.AGE_GROUP.MID_AGED, bodyInfo.getAgeGroup());
    }

    public void testSetAgeGroupOld() {
        // Set age to old
        bodyInfo.setAge(60);
        bodyInfo.setAgeGroup();
        assertEquals(Constants.AGE_GROUP.OLD, bodyInfo.getAgeGroup());
    }

    public void testSetEnergyLevelLow() {
        // Set low heart rate and low calories
        bodyInfo.setCurrHeartRate(50);
        bodyInfo.setCurrCalories(1200);
        bodyInfo.setEnergyLevel();
        assertEquals(Constants.ENERGY_LEVEL.LOW, bodyInfo.getEnergyLevel());
    }

    public void testSetEnergyLevelMedium() {
        // Set medium heart rate and calories
        bodyInfo.setCurrHeartRate(80);
        bodyInfo.setCurrCalories(2000);
        bodyInfo.setEnergyLevel();
        assertEquals(Constants.ENERGY_LEVEL.MEDIUM, bodyInfo.getEnergyLevel());
    }

    public void testSetEnergyLevelHigh() {
        // Set high heart rate and calories
        bodyInfo.setCurrHeartRate(120);
        bodyInfo.setCurrCalories(3000);
        bodyInfo.setEnergyLevel();
        assertEquals(Constants.ENERGY_LEVEL.HIGH, bodyInfo.getEnergyLevel());
    }
}
