package PME.myfitnessbuddy.storage.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.model.training.TrainingWithExercise;

import java.util.List;

/**
 * Defines all sql operations needed to manage the training
 * */

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

    @Delete
    void delete2(Training training);

    @Query("DELETE FROM Training")
    void deleteAll();

    @Query("SELECT count(*) FROM Training")
    int count();

    @Transaction
    @Query("SELECT * FROM Training")
    LiveData< List<TrainingWithExercise>> getTrainingWithExercises();

    @Query("SELECT * from Training ORDER BY trainingId DESC LIMIT 1")
    Training getLastEntry();

    @Query("SELECT * FROM Training WHERE trainingId = :trainingId")
    LiveData<Training> getTrainingById(long trainingId);

}
