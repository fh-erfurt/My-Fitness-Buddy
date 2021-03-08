package PME.myfitnessbuddy.view.ui.exercise;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import PME.myfitnessbuddy.model.TrainingsLog;
import PME.myfitnessbuddy.model.relationship.ExerciseMuscleGroupCrossRef;
import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.model.muscleGroup.MuscleGroup;
import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.storage.ExerciseMuscleGroupCrossRefRepository;
import PME.myfitnessbuddy.storage.ExerciseRepository;
import PME.myfitnessbuddy.storage.MuscleGroupRepository;
import PME.myfitnessbuddy.storage.TrainingExerciseCrossRefRepository;
import PME.myfitnessbuddy.storage.TrainingsLogRepository;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {
    private final ExerciseRepository exerciseRepository;
    private final MuscleGroupRepository muscleGroupRepository;
    private final ExerciseMuscleGroupCrossRefRepository exerciseMuscleGroupCrossRefRepository;
    private final TrainingsLogRepository trainingsLogRepository;

    private MutableLiveData<String> mText;

    public ExerciseViewModel(Application application) {
        super(application);
        this.exerciseRepository = ExerciseRepository.getRepository(application);
        this.muscleGroupRepository = MuscleGroupRepository.getRepository(application);
        this.exerciseMuscleGroupCrossRefRepository = ExerciseMuscleGroupCrossRefRepository.getRepository(application);
        this.trainingsLogRepository = TrainingsLogRepository.getRepository(application);
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

        this.exerciseMuscleGroupCrossRefRepository.insert(exerciseMuscleGroupCrossRef);
    }

    public void insertTrainingsLog(TrainingsLog trainingsLog) {

        this.trainingsLogRepository.insert(trainingsLog);

    }

    public LiveData<List<ExerciseWithMuscleGroup>> getExercises() {
        return this.exerciseRepository.getExerciseLiveData();
    }

    public List<ExerciseWithMuscleGroup> getAllExercises() {
        return this.exerciseRepository.allExercises();
    }

    public LiveData<List<ExerciseWithMuscleGroup>> getExercisesFromTraining(Integer trainingId){
        return this.exerciseRepository.getExerciseFromTraining(trainingId);
    }


    public List<Exercise> getExercisesFromRepo() {
        return this.exerciseRepository.getExercises();
    }

    public List<Exercise> getExercisesWhichAreNotInTrainingFromRepo(Integer trainingId) {
        return this.exerciseRepository.getExercisesWhichAreNotInTraining(trainingId);
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


    ////////////NEU//////////////
    public LiveData<String> getText() {
        return mText;
    }

    public void deleteExercises( List<ExerciseWithMuscleGroup> exercises )
    {
        //this.exerciseRepository.delete( exercises );
    }

}
