package PME.myfitnessbuddy.storage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import PME.myfitnessbuddy.model.training.Training;

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

    @Query("SELECT * from Training ORDER BY designation ASC")
    List<Training> getTrainingSortedByDesignation();

    @Query("SELECT * from Training ORDER BY training_id DESC LIMIT 1")
    Training getLastEntry();

    @Query("SELECT * FROM Training WHERE designation LIKE :search")
    List<Training> getTrainingForDesignation(String search);

    @Query("SELECT * FROM Training WHERE training_id = :trainingId")
    LiveData<Training> getTrainingById(long trainingId);
}
