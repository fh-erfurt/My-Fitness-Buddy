package PME.myfitnessbuddy.model.muscleGroup;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;

public class MuscleGroupWithExercise {

    @Embedded
    public MuscleGroup muscleGroup;

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Relation(
            parentColumn = "muscleGroupId",
            entityColumn = "exerciseId",
            associateBy = @Junction(ExerciseMuscleGroupCrossRef.class)

    )

    public List<Exercise> exercises;

}
