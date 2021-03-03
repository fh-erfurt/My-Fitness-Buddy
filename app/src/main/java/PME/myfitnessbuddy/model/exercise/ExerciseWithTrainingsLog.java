package PME.myfitnessbuddy.model.exercise;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import PME.myfitnessbuddy.model.TrainingsLog;
import PME.myfitnessbuddy.model.relationship.TrainingExerciseCrossRef;
import PME.myfitnessbuddy.model.training.Training;

public class ExerciseWithTrainingsLog {

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
            entityColumn = "trainingsLogId"

    )
    public List<TrainingsLog> trainingsLog;

}
