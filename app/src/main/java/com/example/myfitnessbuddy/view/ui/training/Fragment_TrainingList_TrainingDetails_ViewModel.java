package com.example.myfitnessbuddy.view.ui.training;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myfitnessbuddy.model.Training.Training;
import com.example.myfitnessbuddy.storage.TrainingRepository;

public class Fragment_TrainingList_TrainingDetails_ViewModel extends AndroidViewModel {
    private final TrainingRepository trainingRepository;

    public Fragment_TrainingList_TrainingDetails_ViewModel(Application application) {
        super(application);
        this.trainingRepository = TrainingRepository.getRepository(application);
    }

    public LiveData<Training> getTraining(long trainingId ) {
        return this.trainingRepository.getTrainingByIdAsLiveData( trainingId );
    }
}
