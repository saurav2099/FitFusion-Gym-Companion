package com.example.fitfusion.Suggestion.Workout;
import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;
import junit.framework.TestCase;

public class RestingTest extends TestCase {

    private BodyInfo bodyInfo;

    @Override
    protected void setUp() throws Exception {
        // Set up a BodyInfo object with relevant information for testing
        bodyInfo = new BodyInfo(
                25,                      // Age
                Constants.GENDER.MALE,   // Gender
                175,                     // Height
                70,                      // Weight
                75,                      // Current Heart Rate
                70,                      // Average Heart Rate
                16,                      // Current Respiration Rate
                15,                      // Average Respiration Rate
                5000,                    // Current Steps
                10000,                   // Target Steps
                2000,                    // Current Calories
                2500                     // Target Calories
        );
        bodyInfo.setNonPrimitiveMetrics();
    }

    public void testRestingDurationForLowEnergyLevel() {
        // Create a Resting object with low energy level
        Resting resting = new Resting(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);

        // You need to define the expected duration based on your business logic
        // For example, assuming the default duration for low energy level is 5 minutes
        assertEquals("5", resting.getWorkoutInfo().getValue());
    }

    public void testRestingDurationForMediumEnergyLevel() {
        // Create a Resting object with medium energy level
        Resting resting = new Resting(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.MEDIUM);
        // For example, assuming the default duration for medium energy level is 5 minutes
        assertEquals("5", resting.getWorkoutInfo().getValue());
    }

    public void testRestingDurationForHighEnergyLevel() {
        // Create a Resting object with high energy level
        Resting resting = new Resting(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.HIGH);

        // For example, assuming the default duration for high energy level is 5 minutes
        assertEquals("5", resting.getWorkoutInfo().getValue());
    }

    public void testRestingDurationForDifferentAgeGroups() {
        // Test resting duration for different age groups
        bodyInfo.setAge(18); // Teen
        Resting restingTeen = new Resting(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        assertEquals("5", restingTeen.getWorkoutInfo().getValue());

        bodyInfo.setAge(25); // Young
        Resting restingYoung = new Resting(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        assertEquals("5", restingYoung.getWorkoutInfo().getValue());

        bodyInfo.setAge(50); // Mid-aged
        Resting restingMidAged = new Resting(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        assertEquals("10", restingMidAged.getWorkoutInfo().getValue());

        bodyInfo.setAge(60); // Old
        Resting restingOld = new Resting(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        assertEquals("15", restingOld.getWorkoutInfo().getValue());
    }

    public void testRestingDurationForDifferentWeightCategories() {
        // Test resting duration for different weight categories
        bodyInfo.setWeight(50); // Underweight
        Resting restingUnderweight = new Resting(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        assertEquals("5", restingUnderweight.getWorkoutInfo().getValue());

        bodyInfo.setWeight(70); // Healthy weight
        Resting restingHealthyWeight = new Resting(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        assertEquals("5", restingHealthyWeight.getWorkoutInfo().getValue());

        bodyInfo.setWeight(90); // Overweight
        Resting restingOverweight = new Resting(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        assertEquals("10", restingOverweight.getWorkoutInfo().getValue());

        bodyInfo.setWeight(110); // Obese
        Resting restingObese = new Resting(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        assertEquals("15", restingObese.getWorkoutInfo().getValue());
    }

}
