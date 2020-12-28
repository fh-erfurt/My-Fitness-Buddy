package PME.myfitnessbuddy.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import PME.myfitnessbuddy.model.exercise.Exercise;

public class ExerciseWithMuscleGroup {

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @Embedded
    public Exercise exercise;

    @Relation(
            parentColumn = "exerciseId",
            entityColumn = "muscleGroupId",
            associateBy = @Junction(ExerciseMuscleGroupCrossRef.class)

    )
    public List<MuscleGroup> muscleGroups;
/*
    public Exercise merge()
    {
        this.exercise.setMuscleGroups( this.muscleGroups );

        return this.exercise;
    }



 */
}
