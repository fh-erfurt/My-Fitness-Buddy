package com.example.myfitnessbuddy.storage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myfitnessbuddy.model.exercise.Exercise;

import java.util.List;
@Dao
public interface ExerciseDao {



    @Insert
    long insert(Exercise exercise);

    @Insert
    List<Long> insert(Exercise... exercise);

    @Update
    void update(Exercise... exercise);

    @Delete
    void delete(Exercise... exercise);

   @Query("DELETE FROM Exercise")
    void deleteAll();

    @Query("SELECT count(*) FROM Exercise")
    int count();

    @Query("SELECT * from Exercise")
    List<Exercise> getExercise();

    @Query("SELECT * from Exercise")
    LiveData<List<Exercise>> getExerciseLiveData();

    @Query("SELECT * from Exercise ORDER BY designation ASC")
    List<Exercise> getExerciseSortedByDesignation();

    @Query("SELECT * from Exercise ORDER BY exercise_id DESC LIMIT 1")
    Exercise getLastEntry();

    @Query("SELECT * FROM Exercise WHERE designation LIKE :search")
    List<Exercise> getExerciseForDesignation(String search);

}
