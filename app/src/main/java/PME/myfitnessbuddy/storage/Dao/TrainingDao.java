package PME.myfitnessbuddy.storage.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;


import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.model.training.TrainingWithExercise;

import java.util.List;
@Dao
public interface TrainingDao {
    @Insert
    long insert(Training training);

    @Insert
    List<Long> insert(Training... trainings);

    @Update
    void update(Training... trainings);

    @Delete
    void delete(Training... trainings);

    @Query("DELETE FROM Training")
    void deleteAll();

    @Query("SELECT count(*) FROM Training")
    int count();

    @Query("SELECT * from Training")
    List<Training> getTrainings();

    @Query("SELECT * from Training")
    LiveData<List<Training>> getTrainingsLiveData();

    @Transaction
    @Query("SELECT * FROM Training")
    LiveData< List<TrainingWithExercise>> getTrainingWithExercises();

    @Query("SELECT * from Training ORDER BY designation ASC")
    List<Training> getTrainingSortedByDesignation();

    @Query("SELECT * from Training ORDER BY trainingId DESC LIMIT 1")
    Training getLastEntry();

    @Query("SELECT * FROM Training WHERE designation LIKE :search")
    List<Training> getTrainingForDesignation(String search);

    @Query("SELECT * FROM Training WHERE trainingId = :trainingId")
    LiveData<Training> getTrainingById(long trainingId);
}
