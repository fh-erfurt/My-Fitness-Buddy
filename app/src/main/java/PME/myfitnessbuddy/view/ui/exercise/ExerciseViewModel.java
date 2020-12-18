package PME.myfitnessbuddy.view.ui.exercise;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.storage.ExerciseRepository;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {
    private final ExerciseRepository exerciseRepository;

    private MutableLiveData<String> mText;

    public ExerciseViewModel(Application application) {
        super(application);
        this.exerciseRepository = ExerciseRepository.getRepository(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is exercise fragment");
    }

    public LiveData<List<Exercise>> getExercises() {
        return this.exerciseRepository.getExerciseLiveData();
    }

    public LiveData<String> getText() {
        return mText;
    }
}

