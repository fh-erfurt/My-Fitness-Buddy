package PME.myfitnessbuddy.view.ui.exercise;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;
import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;
import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.storage.ExerciseCrossRefRepository;
import PME.myfitnessbuddy.storage.ExerciseRepository;
import PME.myfitnessbuddy.storage.MuscleGroupRepository;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {
    private final ExerciseRepository exerciseRepository;
    private final MuscleGroupRepository muscleGroupRepository;
    private final ExerciseCrossRefRepository exerciseCrossRefRepository;

    private MutableLiveData<String> mText;

    public ExerciseViewModel(Application application) {
        super(application);
        this.exerciseRepository = ExerciseRepository.getRepository(application);
        this.muscleGroupRepository = MuscleGroupRepository.getRepository(application);
        this.exerciseCrossRefRepository = ExerciseCrossRefRepository.getRepository(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is exercise fragment");
    }
/*
    @RequiresApi(api = Build.VERSION_CODES.N)
    public LiveData<List<Exercise>> getExercises() {
        return this.exerciseRepository.getExerciseLiveData();
    }


 */
        public long insertExercise(Exercise exercise) {

            this.exerciseRepository.insert(exercise);
            return  exerciseRepository.getRepositoryExerciseId();
        }

    public long insertMuscleGroup(MuscleGroup muscleGroup) {

        this.muscleGroupRepository.insert(muscleGroup);
        return  muscleGroupRepository.getMuscleGroupRepositoryId();
    }

    public void insertExerciseCrossRef(ExerciseMuscleGroupCrossRef exerciseMuscleGroupCrossRef) {

        this.exerciseCrossRefRepository.insert(exerciseMuscleGroupCrossRef);
    }

    public LiveData<List<ExerciseWithMuscleGroup>> getExercises() {
        return this.exerciseRepository.getExerciseLiveData();
    }

    public List<MuscleGroup> getMuscleGroupForDesignation(String search){
            return this.muscleGroupRepository.getMuscleGroupsForDesignation(search);
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

