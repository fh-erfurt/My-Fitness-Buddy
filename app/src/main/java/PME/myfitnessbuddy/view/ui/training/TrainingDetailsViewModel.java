package PME.myfitnessbuddy.view.ui.training;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.storage.TrainingExerciseCrossRefRepository;
import PME.myfitnessbuddy.storage.TrainingRepository;

/**
 * TrainingeDetails ViewModel class fetches the information from one training from the database
 * */

public class TrainingDetailsViewModel extends AndroidViewModel {

    private final TrainingRepository trainingRepository;
    private final TrainingExerciseCrossRefRepository trainingExerciseCrossRefRepository;

    public TrainingDetailsViewModel(Application application) {
        super(application);
        this.trainingRepository = TrainingRepository.getRepository(application);
        this.trainingExerciseCrossRefRepository = TrainingExerciseCrossRefRepository.getRepository(application);
    }

    public LiveData<Training> getTraining(long trainingId ) {
        return this.trainingRepository.getTrainingByIdAsLiveData( trainingId );
    }

    public void deleteTrainingExerciseCrossRefs( long trainingId, List <ExerciseWithMuscleGroup> exerciseWithMuscleGroups )
    {

        for (int i=0; i < exerciseWithMuscleGroups.size(); i++) {
            this.trainingExerciseCrossRefRepository.deleteByTrainingIdAndExerciseId( trainingId,
                    exerciseWithMuscleGroups.get(i).getExerciseId());
        }

    }

}
