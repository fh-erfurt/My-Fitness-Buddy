package PME.myfitnessbuddy.model.exercise;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;
import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;
import PME.myfitnessbuddy.model.relationship.TrainingExerciseCrossRef;
import PME.myfitnessbuddy.model.training.Training;

public class ExerciseWithTraining {

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
            entityColumn = "trainingId",
            associateBy = @Junction(TrainingExerciseCrossRef.class)

    )
    public List<Training> trainings;
}
