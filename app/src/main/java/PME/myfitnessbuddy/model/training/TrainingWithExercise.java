package PME.myfitnessbuddy.model.training;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.model.relationship.TrainingExerciseCrossRef;

public class TrainingWithExercise {

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    @Embedded
    public Training training;

    @Relation(
            parentColumn = "trainingId",
            entityColumn = "exerciseId",
            associateBy = @Junction(TrainingExerciseCrossRef.class)
    )

    public List<Exercise> exercises;

    public long getTrainingId(){
       return this.training.getTrainingId();
    }

}
