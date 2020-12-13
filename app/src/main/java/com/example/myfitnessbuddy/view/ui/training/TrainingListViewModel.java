package com.example.myfitnessbuddy.view.ui.training;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myfitnessbuddy.model.Training.Training;
import com.example.myfitnessbuddy.storage.TrainingRepository;

import java.util.List;

public class TrainingListViewModel extends AndroidViewModel {
    private final TrainingRepository trainingRepository;

    public TrainingListViewModel(Application application) {
        super(application);
        this.trainingRepository = TrainingRepository.getRepository(application);
    }

    public LiveData<List<Training>> getTrainings() {
        return this.trainingRepository.getTrainingsLiveData();
    }
}
