package com.example.myfitnessbuddy.storage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.myfitnessbuddy.model.Training.Training;

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

    @Query("SELECT * from Training ORDER BY designation ASC")
    List<Training> getTrainingSortedByDesignation();

    @Query("SELECT * from Training ORDER BY id DESC LIMIT 1")
    Training getLastEntry();

    @Query("SELECT * FROM Training WHERE designation LIKE :search")
    List<Training> getTrainingForDesignation(String search);
}
