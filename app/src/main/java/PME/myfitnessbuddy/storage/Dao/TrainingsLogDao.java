package PME.myfitnessbuddy.storage.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import PME.myfitnessbuddy.model.TrainingsLog;

@Dao
public interface TrainingsLogDao {
    @Insert
    long insertTrainingsLog(TrainingsLog trainingsLog);

    @Update
    void update(TrainingsLog... trainingsLogs);

    @Delete
    void delete(TrainingsLog... trainingsLogs);

    @Query("DELETE FROM TrainingsLog")
    void deleteAll();

    @Query("SELECT count(*) FROM TrainingsLog")
    int count();

    @Query("SELECT * from TrainingsLog ORDER BY trainingsLogId DESC LIMIT 1")
    TrainingsLog getLastEntry();


}
