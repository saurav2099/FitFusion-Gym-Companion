package com.example.fitfusion.Suggestion.Workout;

import com.example.fitfusion.Suggestion.BodyInfo;
import com.example.fitfusion.Suggestion.Constants;
import junit.framework.TestCase;

import org.junit.Before;

public class CoolDownTest extends TestCase {

    private BodyInfo bodyInfo;

    @Before
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

    public void testCoolDownDurationForHighEnergyLevel() {
        // Create a CoolDown object with high energy level
        CoolDown coolDown = new CoolDown(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.HIGH);

        // The expected duration for high energy level is 15 (as per your implementation)
        assertEquals("15", coolDown.getWorkoutInfo().getValue());
    }

    public void testCoolDownDurationForLowEnergyLevel() {
        // Create a CoolDown object with low energy level
        CoolDown coolDown = new CoolDown(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);

        // The expected duration for low energy level is 5 (as per your implementation)
        assertEquals("5", coolDown.getWorkoutInfo().getValue());
    }

    public void testCoolDownDurationForMediumEnergyLevel() {
        // Create a CoolDown object with medium energy level
        CoolDown coolDown = new CoolDown(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.MEDIUM);

        // The expected duration for medium energy level is 10 (as per your implementation)
        assertEquals("10", coolDown.getWorkoutInfo().getValue());
    }

    public void testCoolDownDurationForDifferentAgeGroups() {
        // Test cool down duration for different age groups
        bodyInfo.setAge(18); // Teen
        CoolDown coolDownTeen = new CoolDown(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        assertEquals("5", coolDownTeen.getWorkoutInfo().getValue());

        bodyInfo.setAge(25); // Young
        CoolDown coolDownYoung = new CoolDown(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        assertEquals("5", coolDownYoung.getWorkoutInfo().getValue());

        bodyInfo.setAge(50); // Mid-aged
        CoolDown coolDownMidAged = new CoolDown(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        assertEquals("5", coolDownMidAged.getWorkoutInfo().getValue());

        bodyInfo.setAge(60); // Old
        CoolDown coolDownOld = new CoolDown(bodyInfo, Constants.LAST_WORKOUT_INTENSITY.LOW);
        assertEquals("5", coolDownOld.getWorkoutInfo().getValue());
    }
}
