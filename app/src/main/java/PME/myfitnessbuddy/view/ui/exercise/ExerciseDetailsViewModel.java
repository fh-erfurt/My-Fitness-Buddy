package PME.myfitnessbuddy.view.ui.exercise;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.model.exercise.ExerciseWithTrainingsLog;
import PME.myfitnessbuddy.storage.ExerciseRepository;
import PME.myfitnessbuddy.storage.TrainingsLogRepository;

public class ExerciseDetailsViewModel extends AndroidViewModel {
    private final ExerciseRepository exerciseRepository;

    private final TrainingsLogRepository trainingsLogRepository;


    public ExerciseDetailsViewModel(Application application) {
        super(application);
        this.exerciseRepository = ExerciseRepository.getRepository(application);
        this.trainingsLogRepository = TrainingsLogRepository.getRepository(application);
    }

    public LiveData<Exercise> getExercise(long exerciseId ) {
        return this.exerciseRepository.getExerciseByIdAsLiveData( exerciseId );
    }

    public LiveData<List<ExerciseWithMuscleGroup>> getExerciseLiveData( ) {
        return this.exerciseRepository.getExerciseLiveData();
    }


    public List<ExerciseWithTrainingsLog> getExerciseWithTrainingsLogLiveDataByExerciseId(long exerciseId) {
        return this.exerciseRepository.getExerciseWithTrainingsLogLiveDataByExerciseId(exerciseId);
    }
}
