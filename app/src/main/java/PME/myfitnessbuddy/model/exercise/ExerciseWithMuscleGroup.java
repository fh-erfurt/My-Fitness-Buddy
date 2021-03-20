package PME.myfitnessbuddy.model.exercise;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;
import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;

/**
 * Entity class ExerciseWithMuscleGroup stores all information to the exerciseWithMuscleGroup object
 *
 * */

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

    public long getExerciseId(){
        return this.exercise.getExerciseId();
    }

}
