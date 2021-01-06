package PME.myfitnessbuddy.view.ui.training;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import PME.myfitnessbuddy.storage.ExerciseRepository;
import PME.myfitnessbuddy.storage.TrainingRepository;

public class TrainingCreateViewModel extends AndroidViewModel {

    public TrainingCreateViewModel(Application application) {
        super(application);
        this.trainingRepository = ExerciseRepository.getRepository(application);
    }

    private final ExerciseRepository trainingRepository;

}
