# FitFusion Workout Suggestions

## Table of Contents

1. [Overview](#overview)
2. [Classes](#classes)
    - [BodyInfo](#1-bodyinfo)
    - [Constants](#2-constants)
    - [Workout](#3-workout)
    - [WorkoutInfo](#4-workoutinfo)
    - [CoolDown, Resting, Running, Skipping, Walking](#5-cooldown-resting-running-skipping-walking)
    - [Suggestion](#6-suggestion)
3. [Usage Examples](#usage-examples)
4. [Integration Guide](#integration-guide)
5. [Customization](#customization)
6. [Conclusion](#conclusion)

## 1. Overview

The FitFusion Workout Suggestions module is designed to assist users in generating personalized workout suggestions based on individual health metrics and previous workout intensity. This documentation provides an in-depth guide on the classes, methods, and usage of the module.

## 2. Classes

### 1. BodyInfo

The `BodyInfo` class encapsulates user-specific physical metrics, including age, gender, height, weight, heart rate, steps, and calories. It calculates non-primitive metrics such as BMI, age group, and energy level based on the provided information.

#### Constructor

```java
public BodyInfo(int age, Constants.GENDER gender, int height, int weight, int currHeartRate, int avgHeartRate, int currRespRate, int avgRespRate, int currSteps, int targetSteps, int currCalories, int targetCalories)
```

Creates a new `BodyInfo` instance with the provided user information.

#### Public Methods

-   `setNonPrimitiveMetrics()`: Calculates and sets BMI, age group, and energy level based on the provided metrics.
-   Getter and setter methods for various physical metrics.

### 2. Constants

The `Constants` class holds constant values used throughout the module, including thresholds for BMI categories, age groups, and energy levels.

### 3. Workout

The abstract `Workout` class serves as the base class for specific workout types. It includes methods for retrieving workout information such as name, duration, unit, and background image.

### 4. WorkoutInfo

The `WorkoutInfo` class represents information about a specific workout, including name, duration, unit, and background image.

#### Constructor

```java
public WorkoutInfo(String name, String value, String unit, String bgImg)
```

Creates a new `WorkoutInfo` instance with the provided information.

### 5. CoolDown, Resting, Running, Skipping, Walking

These classes extend the `Workout` class and represent specific workout types with customized duration calculations based on user metrics.

### 6. Suggestion

The `Suggestion` class coordinates the generation of workout suggestions based on the user's body information and previous workout intensity.

#### Constructor

```java
public Suggestion(BodyInfo bodyInfo)
```

Creates a new `Suggestion` instance with the provided `BodyInfo`. The initial last workout intensity is set to `Constants.LAST_WORKOUT_INTENSITY.LOW`.

#### Public Methods

-   `updateLastWorkoutIntensity`: Updates the last workout intensity based on the provided value.
-   `getLastWorkoutIntensity`: Returns the last workout intensity.
-   `getSuggestions`: Generates workout suggestions based on the user's body information and previous workout intensity. Returns a list of `WorkoutInfo` objects.

## 3. Usage Examples

```java
// Example BodyInfo creation
BodyInfo bodyInfo = new BodyInfo(/* ... */);

// Create a Suggestion instance
Suggestion suggestion = new Suggestion(bodyInfo);

// Update last workout intensity
suggestion.updateLastWorkoutIntensity(Constants.LAST_WORKOUT_INTENSITY.MEDIUM);

// Get workout suggestions
ArrayList<WorkoutInfo> suggestions = suggestion.getSuggestions();

// Print the suggestions
for (WorkoutInfo workout : suggestions) {
    System.out.println("Workout: " + workout.getName());
    System.out.println("Duration: " + workout.getValue() + " " + workout.getUnit());
    System.out.println("Background Image: " + workout.getBgImg());
    System.out.println();
}
```

In this example, we create a `BodyInfo` instance, initialize a `Suggestion` object, update the last workout intensity, and retrieve workout suggestions. The suggestions are then printed to the console.

## 4. Integration Guide

The FitFusion Workout Suggestions module can be easily integrated into fitness applications or platforms. Follow these steps for successful integration:

1. **Include the Code**: Copy the provided Java classes (`BodyInfo`, `Constants`, `Workout`, `WorkoutInfo`, `CoolDown`, `Resting`, `Running`, `Skipping`, `Walking`, and `Suggestion`) into your project.

2. **Initialize `BodyInfo`**: Collect user-specific information and create an instance of the `BodyInfo` class.

3. **Create `Suggestion` Instance**: Instantiate the `Suggestion` class with the `BodyInfo` instance.

4. **Update Last Workout Intensity**: If the user has a history of workouts, update the last workout intensity using the `updateLastWorkoutIntensity` method.

5. **Generate Suggestions**: Call the `getSuggestions` method to obtain a list of workout suggestions.

6. **Display Suggestions**: Utilize the workout suggestions in your application's user interface, providing information on workout names, durations, and background images.

## 5. Customization

The FitFusion Workout Suggestions module is designed for flexibility and customization. You can customize the workout duration calculations, thresholds, and workout types based on your application's specific requirements.

-   **Extend Workout Types**: Create additional workout types by extending the
