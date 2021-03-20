package PME.myfitnessbuddy.storage.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.model.exercise.ExerciseWithTrainingsLog;
import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;
import PME.myfitnessbuddy.model.exercise.Exercise;

import java.util.List;

/**
 * Defines all sql operations needed to manage the exercise
 * */

@Dao
public interface ExerciseDao {


    @Insert
    long insertExercise(Exercise exercise);

    @Update
    void update(Exercise... exercise);

    @Delete
    void delete(List<Exercise> exercises);

    @Delete
    void delete2(Exercise exercise);

   @Query("DELETE FROM Exercise")
    void deleteAll();

    @Query("SELECT count(*) FROM Exercise")
    int count();

    @Query("SELECT * from Exercise")
    List<Exercise> getExercises();

    @Query("SELECT * FROM Exercise WHERE designation not in (SELECT designation FROM Exercise e JOIN TrainingExerciseCrossRef t ON e.exerciseId =t.exerciseId WHERE t.trainingId=:trainingId)")
    List<Exercise> getAllExercisesWhichAreNotTraining(Integer trainingId);

    @Query("SELECT * from Exercise ORDER BY exerciseId DESC LIMIT 1")
    Exercise getLastEntry();

    @Query("SELECT * FROM Exercise WHERE exerciseId = :exerciseId")
    LiveData<Exercise> getExerciseById(long exerciseId);

    @Transaction
    @Query("SELECT * FROM Exercise e JOIN TrainingExerciseCrossRef t ON e.exerciseId =t.exerciseId WHERE t.trainingId LIKE :trainingId")
    LiveData< List<ExerciseWithMuscleGroup>> getAllExercisesFromTraining(Integer trainingId);

    @Transaction
    @Query("SELECT * FROM Exercise")
    LiveData< List<ExerciseWithMuscleGroup>> getExercisesWithMuscleGroups();

    @Transaction
    @Query("SELECT * FROM Exercise WHERE exerciseId LIKE :id")
    List <ExerciseWithTrainingsLog> getExercisesWithTrainingsLogById(long id);

}
