package PME.myfitnessbuddy.storage.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;

/**
 * Defines all sql operations needed to manage the exerciseMuscleGroupCrossRefs
 * */

@Dao
public interface ExerciseMuscleGroupCrossRefDao {
    @Insert
    long insertExerciseCrossRef(ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef);

    @Update
    void update(ExerciseMuscleGroupCrossRef... exerciseCrossRef);

    @Delete
    void delete(ExerciseMuscleGroupCrossRef... exerciseMuscleGroupCrossRefs);

    @Query("DELETE FROM ExerciseMuscleGroupCrossRef")
    void deleteAll();

    @Query("SELECT count(*) FROM ExerciseMuscleGroupCrossRef")
    int count();

    @Query("SELECT * from ExerciseMuscleGroupCrossRef")
    List<ExerciseMuscleGroupCrossRef> getExercises();

    @Query("SELECT * from ExerciseMuscleGroupCrossRef ORDER BY exerciseId DESC LIMIT 1")
    ExerciseMuscleGroupCrossRef getLastEntry();





}
