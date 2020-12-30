package PME.myfitnessbuddy.model.exercise;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;
import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;

public class ExerciseWithMuscleGroup {

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @Embedded
    public Exercise exercise;

    public List<MuscleGroup> getMuscleGroups() {
        return muscleGroups;
    }

    public void setMuscleGroups(List<MuscleGroup> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

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
