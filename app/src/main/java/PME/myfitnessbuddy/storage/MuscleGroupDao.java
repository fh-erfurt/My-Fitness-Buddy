package PME.myfitnessbuddy.storage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;
import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;
import PME.myfitnessbuddy.model.muscleGroup.MuscleGroupWithExercise;
import PME.myfitnessbuddy.model.exercise.Exercise;

import java.util.List;
@Dao
public interface

MuscleGroupDao {
    @Insert
    long insertMuscleGroup(MuscleGroup muscleGroup);

    @Insert
    List<Long> insertMuscleGroup(MuscleGroup... muscleGroups);

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

    @Query("SELECT * from MuscleGroup ORDER BY muscleGroupId DESC LIMIT 1")
    MuscleGroup getLastEntry();

    @Query("SELECT * FROM MuscleGroup WHERE designation LIKE :search")
    List<MuscleGroup> getMuscleGroupForDesignation(String search);

    @Query("SELECT * from MuscleGroup")
    LiveData<List<MuscleGroup>> getMuscleGroupLiveData();

    @Insert
    List<Long> insertExercises(List<Exercise> exercises);
/*
    default long insertMuscleGroupWithExercises(MuscleGroup muscleGroup )
    {
        // Prepare Contact for saving
        // contact.setProfileImageUrl( Helper.generateProfileImageUrl() );

        muscleGroup.setCreated( System.currentTimeMillis() );
        muscleGroup.setModified( muscleGroup.getCreated() );
        muscleGroup.setVersion( muscleGroup.getVersion() + 1 );

        // Save Contact
        long muscleGroupId = this.insertMuscleGroup( muscleGroup );



        // Prepare Addresses
        for(Exercise exercise : muscleGroup.getExercises() )
        {
            exercise.setMuscleGroupId( muscleGroupId );

            exercise.setCreated( muscleGroup.getCreated() );
            exercise.setModified( muscleGroup.getCreated() );
            exercise.setVersion( exercise.getVersion() + 1 );
        }

        // Insert Addresses
        this.insertExercises( muscleGroup.getExercises() );

        // Return new contact id
        return muscleGroupId;
    }

    default long updateMuscleGroupWithExercise( MuscleGroup muscleGroup )
    {
        long muscleGroupId = muscleGroup.getId();
        muscleGroup.setCreated( System.currentTimeMillis() );
        muscleGroup.setModified( muscleGroup.getCreated() );
        muscleGroup.setVersion( muscleGroup.getVersion() + 1 );

        for( ExerciseMuscleGroupCrossRef exercise : muscleGroup.getExercises() )
        {
            exercise.setMuscleGroupId( muscleGroupId );

            exercise.setCreated( muscleGroup.getCreated() );
            exercise.setModified( muscleGroup.getCreated() );
            exercise.setVersion( exercise.getVersion() + 1 );
        }

        this.updateExercises( muscleGroup.getExercises() );
        this.update( muscleGroup );

        return muscleGroupId;
    }



 */
    @Transaction
    @Query("SELECT * from MuscleGroup")
    LiveData<List<MuscleGroupWithExercise>> getMuscleGroupsWithExercises();

    @Update
    void updateExercises( List<ExerciseMuscleGroupCrossRef> muscleGroups );

    @Query("SELECT * FROM MuscleGroup WHERE muscleGroupId = :muscleGroupId")
    LiveData<MuscleGroup> geMuscleGroupById(long muscleGroupId);

}
