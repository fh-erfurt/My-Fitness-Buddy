package com.example.myfitnessbuddy.view.ui.exercise;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myfitnessbuddy.model.exercise.Exercise;
import com.example.myfitnessbuddy.storage.ExerciseRepository;
import com.example.myfitnessbuddy.storage.TrainingRepository;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {
    private final ExerciseRepository exerciseRepository;

    public ExerciseViewModel(Application application) {
        super(application);
        this.exerciseRepository = ExerciseRepository.getRepository(application);
    }

    public LiveData<List<Exercise>> getExercises() {
        return this.exerciseRepository.getExerciseLiveData();
    }
}
