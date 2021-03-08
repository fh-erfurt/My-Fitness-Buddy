package PME.myfitnessbuddy.storage.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import PME.myfitnessbuddy.model.Person;
import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.model.relationship.TrainingExerciseCrossRef;

@Dao
public interface TrainingExerciseCrossRefDao {

    @Insert
    long insertTrainingExerciseCrossRef(TrainingExerciseCrossRef trainingExerciseCrossRef);


    @Update
    void update(TrainingExerciseCrossRef... trainingExerciseCrossRef);

    @Delete
    void delete(TrainingExerciseCrossRef... trainingExerciseCrossRefs);

    @Query("DELETE FROM TrainingExerciseCrossRef")
    void deleteAll();

    @Query("DELETE FROM TrainingExerciseCrossRef WHERE trainingId = :trainingId")
    void deleteByTrainingsId(long trainingId);

    @Query("DELETE FROM TrainingExerciseCrossRef WHERE trainingId = :trainingId and exerciseId = :exerciseId")
    void deleteByTrainingsIdAndExerciseId(long trainingId, long exerciseId);

    @Query("SELECT count(*) FROM TrainingExerciseCrossRef")
    int count();

    @Query("SELECT count(trainingId) FROM TrainingExerciseCrossRef WHERE trainingId = :trainingId")
    int countedExercises(long trainingId);

    @Query("SELECT * from TrainingExerciseCrossRef")
    List<TrainingExerciseCrossRef> getTrainings();


    @Query("SELECT * from TrainingExerciseCrossRef")
    LiveData<List<TrainingExerciseCrossRef>> getTrainingLiveData();







}
