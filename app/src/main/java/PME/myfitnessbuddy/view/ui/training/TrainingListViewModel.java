package PME.myfitnessbuddy.view.ui.training;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;
import PME.myfitnessbuddy.model.relationship.TrainingExerciseCrossRef;
import PME.myfitnessbuddy.model.training.Training;
import PME.myfitnessbuddy.model.training.TrainingWithExercise;
import PME.myfitnessbuddy.storage.ExerciseMuscleGroupCrossRefRepository;
import PME.myfitnessbuddy.storage.ExerciseRepository;
import PME.myfitnessbuddy.storage.MuscleGroupRepository;
import PME.myfitnessbuddy.storage.TrainingExerciseCrossRefRepository;
import PME.myfitnessbuddy.storage.TrainingRepository;

import java.util.List;

public class TrainingListViewModel extends AndroidViewModel {
    private final TrainingRepository trainingRepository;
    private final TrainingExerciseCrossRefRepository trainingExerciseCrossRefRepository;



    public TrainingListViewModel(Application application) {
        super(application);
        this.trainingRepository = TrainingRepository.getRepository(application);
            this.trainingExerciseCrossRefRepository = TrainingExerciseCrossRefRepository.getRepository(application);

        }

    public LiveData<List<TrainingWithExercise>> getTrainings() {
        return this.trainingRepository.getTrainingsLiveData();
    }

    public long insertTraining(Training training) {

        this.trainingRepository.insert(training);
        return trainingRepository.getRepositoryTrainingId();
    }

    public void insertExerciseCrossRef(TrainingExerciseCrossRef trainingExerciseCrossRef) {

        this.trainingExerciseCrossRefRepository.insert(trainingExerciseCrossRef);
    }
}
