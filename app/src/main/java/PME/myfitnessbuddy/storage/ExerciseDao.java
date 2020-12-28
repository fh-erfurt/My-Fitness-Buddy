package PME.myfitnessbuddy.storage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import PME.myfitnessbuddy.model.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.model.MuscleGroup;
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
/*
    @Insert
    List<Long> insertMuscleGroups(List<MuscleGroup> muscleGroups);

    default long insertExerciseWithMuscleGroups( Exercise exercise )
    {
        // Prepare Contact for saving
       // contact.setProfileImageUrl( Helper.generateProfileImageUrl() );

        exercise.setCreated( System.currentTimeMillis() );
        exercise.setModified( exercise.getCreated() );
        exercise.setVersion( exercise.getVersion() + 1 );

        // Save Contact
        long exerciseId = this.insertExercise( exercise );

        // Prepare Addresses
        for(MuscleGroup muscleGroup : exercise.getMuscleGroups() )
        {
            muscleGroup.setExerciseId( exerciseId );

            muscleGroup.setCreated( exercise.getCreated() );
            muscleGroup.setModified( exercise.getCreated() );
            muscleGroup.setVersion( muscleGroup.getVersion() + 1 );
        }

        // Insert Addresses
        this.insertMuscleGroups( exercise.getMuscleGroups() );

        // Return new contact id
        return exerciseId;
    }

    default long updateExerciseWithMuscleGroup( Exercise exercise )
    {
        long exerciseId = exercise.getExerciseId();
        exercise.setCreated( System.currentTimeMillis() );
        exercise.setModified( exercise.getCreated() );
        exercise.setVersion( exercise.getVersion() + 1 );

        for( MuscleGroup muscleGroup : exercise.getMuscleGroups() )
        {
            muscleGroup.setExerciseId( exerciseId );

            muscleGroup.setCreated( exercise.getCreated() );
            muscleGroup.setModified( exercise.getCreated() );
            muscleGroup.setVersion( muscleGroup.getVersion() + 1 );
        }

        this.updateMuscleGroups( exercise.getMuscleGroups() );
        this.update( exercise );

        return exerciseId;
    }


 */
/*
    @Transaction
    @Query("SELECT * from Exercise")
    LiveData<List<ExerciseWithMuscleGroup>> getExercisesWithMuscleGroups();


 */

    @Transaction
    @Query("SELECT * FROM Exercise")
    LiveData< List<ExerciseWithMuscleGroup>> getExercisesWithMuscleGroups();


    @Update
    void updateMuscleGroups( List<MuscleGroup> muscleGroups );


}
