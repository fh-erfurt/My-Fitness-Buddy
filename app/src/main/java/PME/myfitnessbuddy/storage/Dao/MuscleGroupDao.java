package PME.myfitnessbuddy.storage.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;

import java.util.List;

/**
 * Defines all sql operations needed to manage the muscleGroup
 * */

@Dao
public interface

MuscleGroupDao {
    @Insert
    long insertMuscleGroup(MuscleGroup muscleGroup);

    @Update
    void update(MuscleGroup... muscleGroups);

    @Delete
    void delete(MuscleGroup... muscleGroups);

    @Query("DELETE FROM MuscleGroup")
    void deleteAll();

    @Query("SELECT count(*) FROM MuscleGroup")
    int count();

    @Query("SELECT * from MuscleGroup ORDER BY muscleGroupId DESC LIMIT 1")
    MuscleGroup getLastEntry();

    @Query("SELECT * FROM MuscleGroup WHERE designation LIKE :search")
    List<MuscleGroup> getMuscleGroupForDesignation(String search);



}
