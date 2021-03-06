package PME.myfitnessbuddy.model.exercise;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.Collections;
import java.util.List;

import PME.myfitnessbuddy.model.TrainingsLog;

/**
 * Entity class ExerciseWithTrainingsLog stores all information to the exerciseWithTrainingsLog object
 *
 * */

public class ExerciseWithTrainingsLog {

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @Embedded
    public Exercise exercise;

    /**
     * sorted by creation date in descending order
     */
    public void sortTrainingsLog() {
        Collections.sort(this.trainingsLog);
        Collections.reverse(this.trainingsLog);
    }

    public List<TrainingsLog> getTrainingsLog() {
        return trainingsLog;
    }

    @Relation(
            parentColumn = "exerciseId",
            entityColumn = "exerciseCreatorId"

    )
    public List<TrainingsLog> trainingsLog;

}
