package PME.myfitnessbuddy.storage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import PME.myfitnessbuddy.model.MuscleGroup;

import java.util.List;
@Dao
public interface MuscleGroupDao {
    @Insert
    long insert(MuscleGroup muscleGroup);

    @Insert
    List<Long> insert(MuscleGroup... muscleGroups);

    @Update
    void update(MuscleGroup... muscleGroups);

    @Delete
    void delete(MuscleGroup... muscleGroups);

    @Query("DELETE FROM MuscleGroup")
    void deleteAll();

    @Query("SELECT count(*) FROM MuscleGroup")
    int count();

    @Query("SELECT * from MuscleGroup")
    List<MuscleGroup> getMuscleGroups();

    @Query("SELECT * from MuscleGroup")
    LiveData<List<MuscleGroup>> getMuscleGroupsLiveData();

    @Query("SELECT * from MuscleGroup ORDER BY designation ASC")
    List<MuscleGroup> getMuscleGroupSortedByDesignation();

    @Query("SELECT * from MuscleGroup ORDER BY id DESC LIMIT 1")
    MuscleGroup getLastEntry();

    @Query("SELECT * FROM MuscleGroup WHERE designation LIKE :search")
    List<MuscleGroup> getMuscleGroupForDesignation(String search);
}
