package PME.myfitnessbuddy.view.ui.training;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.storage.TrainingRepository;

public class TrainingDetailsViewModel extends AndroidViewModel {
    private final TrainingRepository trainingRepository;

    public TrainingDetailsViewModel(Application application) {
        super(application);
        this.trainingRepository = TrainingRepository.getRepository(application);
    }

    public LiveData<Training> getTraining(long trainingId ) {
        return this.trainingRepository.getTrainingByIdAsLiveData( trainingId );
    }
}
