package PME.myfitnessbuddy.storage.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import PME.myfitnessbuddy.model.Person;
import PME.myfitnessbuddy.model.TrainingsLog;
import PME.myfitnessbuddy.model.exercise.ExerciseWithTrainingsLog;
import PME.myfitnessbuddy.model.training.TrainingWithExercise;

@Dao
public interface TrainingsLogDao {
    @Insert
    long insertTrainingsLog(TrainingsLog trainingsLog);

    @Insert
    List<Long> insertTrainingsLog(TrainingsLog... trainingsLogs);

    @Update
    void update(TrainingsLog... trainingsLogs);

    @Delete
    void delete(TrainingsLog... trainingsLogs);

    @Query("DELETE FROM TrainingsLog")
    void deleteAll();

    @Query("SELECT count(*) FROM TrainingsLog")
    int count();

    @Query("SELECT * from TrainingsLog")
    List<TrainingsLog> getTrainingsLogs();

    @Query("SELECT * from TrainingsLog ORDER BY created")
    List<TrainingsLog> getTrainingsLogSortedByCreated();

    @Query("SELECT * from TrainingsLog ORDER BY trainingsLogId DESC LIMIT 1")
    TrainingsLog getLastEntry();

    @Transaction
    @Query("SELECT * FROM Exercise")
    LiveData< List<ExerciseWithTrainingsLog>> getExerciseWithTrainingsLog();

    @Transaction
    @Query("SELECT * FROM Exercise")
    LiveData< List<ExerciseWithTrainingsLog>> getExerciseWithTrainingsLogById();

    /*
    @Transaction
    @Query("SELECT * FROM ...")
    /// Die letzten 3 Einträge des letzten Trainingstages



 abfrage = query("SELECT * FROM TABELE where date = (SELECT top 1 date FROM  TABELE where date < DATE('now') )
 ORDER BY _date_ DESC LIMIT 3);

     */
/*

    @Transaction
    @Query("SELECT * FROM  TrainingsLog where DATE(created) = DATE('now') and  ORDER BY created DESC LIMIT 3")
    LiveData< List<TrainingsLog>> actualTrainingsDayLast3Entries();

 */
     /*
    /// Die letzten 3 Einträge des aktuellen Trainingstages

    abfrage = query("SELECT * FROM TABELE where date = DATE('now') ORDER BY date DESC LIMIT 3);
     */

}
