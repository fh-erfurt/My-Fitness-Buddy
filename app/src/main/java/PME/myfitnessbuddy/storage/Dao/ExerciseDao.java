package PME.myfitnessbuddy.storage.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;
import PME.myfitnessbuddy.model.exercise.Exercise;

import java.util.List;
@Dao
public interface ExerciseDao {


    @Insert
    long insertExercise(Exercise exercise);

    @Insert
    List<Long> insertExercise(Exercise... exercise);

    @Update
    void update(Exercise... exercise);

    @Delete
    void delete(Exercise... exercise);

   @Query("DELETE FROM Exercise")
    void deleteAll();

    @Query("SELECT count(*) FROM Exercise")
    int count();

    @Query("SELECT * from Exercise")
    List<Exercise> getExercises();

    @Query("SELECT * from Exercise")
    LiveData<List<Exercise>> getExerciseLiveData();


    @Query("SELECT * from Exercise ORDER BY designation ASC")
    List<Exercise> getExerciseSortedByDesignation();

    @Query("SELECT * from Exercise ORDER BY exerciseId DESC LIMIT 1")
    Exercise getLastEntry();

    @Query("SELECT * FROM Exercise WHERE designation LIKE :search")
    List<Exercise> getExerciseForDesignation(String search);

    @Query("SELECT * FROM Exercise WHERE exerciseId = :exerciseId")
    LiveData<Exercise> getExerciseById(long exerciseId);

    @Transaction
    @Query("SELECT * FROM Exercise e JOIN TrainingExerciseCrossRef t ON e.exerciseId =t.exerciseId WHERE t.trainingId LIKE :trainingId")
    LiveData< List<ExerciseWithMuscleGroup>> getAllExercisesFromTraining(Integer trainingId);


    @Transaction
    @Query("SELECT * FROM Exercise")
    LiveData< List<ExerciseWithMuscleGroup>> getExercisesWithMuscleGroups();

    @Transaction
    @Query("SELECT * FROM Exercise")
    List<ExerciseWithMuscleGroup> getAllExercises();

    @Update
    void updateMuscleGroups( List<MuscleGroup> muscleGroups );


}
