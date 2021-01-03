package PME.myfitnessbuddy.view.ui.exercise;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.storage.ExerciseRepository;

public class ExerciseDetailsViewModel extends AndroidViewModel {
    private final ExerciseRepository exerciseRepository;

    public ExerciseDetailsViewModel(Application application) {
        super(application);
        this.exerciseRepository = ExerciseRepository.getRepository(application);
    }

    public LiveData<Exercise> getExercise(long exerciseId ) {
        return this.exerciseRepository.getExerciseByIdAsLiveData( exerciseId );
    }

    public LiveData<List<ExerciseWithMuscleGroup>> getExerciseLiveData( ) {
        return this.exerciseRepository.getExerciseLiveData();
    }
}
