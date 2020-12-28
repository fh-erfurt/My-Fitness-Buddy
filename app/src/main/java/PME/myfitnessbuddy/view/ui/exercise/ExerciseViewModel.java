package PME.myfitnessbuddy.view.ui.exercise;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import PME.myfitnessbuddy.model.ExerciseMuscleGroupCrossRef;
import PME.myfitnessbuddy.model.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.model.MuscleGroup;
import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.storage.ExerciseRepository;
import PME.myfitnessbuddy.storage.MuscleGroupRepository;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {
    private final ExerciseRepository exerciseRepository;
    private final MuscleGroupRepository muscleGroupRepository;

    private MutableLiveData<String> mText;

    public ExerciseViewModel(Application application) {
        super(application);
        this.exerciseRepository = ExerciseRepository.getRepository(application);
        this.muscleGroupRepository = MuscleGroupRepository.getRepository(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is exercise fragment");
    }
/*
    @RequiresApi(api = Build.VERSION_CODES.N)
    public LiveData<List<Exercise>> getExercises() {
        return this.exerciseRepository.getExerciseLiveData();
    }


 */


    public LiveData<List<ExerciseWithMuscleGroup>> getExercises() {
        return this.exerciseRepository.getExerciseLiveData();
    }
/*
    @RequiresApi(api = Build.VERSION_CODES.N)
    public LiveData<List<MuscleGroup>> getMuscleGroups() {
        return this.muscleGroupRepository.getMuscleGroupLiveData();
    }


 */
    public LiveData<String> getText() {
        return mText;
    }
}

