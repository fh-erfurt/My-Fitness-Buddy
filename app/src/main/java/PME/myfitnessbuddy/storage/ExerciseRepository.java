package PME.myfitnessbuddy.storage;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import PME.myfitnessbuddy.model.exercise.ExerciseWithMuscleGroup;
import PME.myfitnessbuddy.model.exercise.Exercise;
import PME.myfitnessbuddy.model.exercise.ExerciseWithTrainingsLog;
import PME.myfitnessbuddy.storage.Dao.ExerciseDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class ExerciseRepository {

    private ExerciseDao exerciseDao;

    public long getRepositoryExerciseId() {
        return repositoryExerciseId;
    }

    private long repositoryExerciseId;

    private static ExerciseRepository INSTANCE;

    public static ExerciseRepository getRepository( Application application )
    {
        if( INSTANCE == null ) {
            synchronized ( ExerciseRepository.class ) {
                if( INSTANCE == null ) {
                    INSTANCE = new ExerciseRepository( application );
                }
            }
        }

        return INSTANCE;
    }

  public ExerciseRepository(Context context) {
        MyFitnessBuddyDatabase db = MyFitnessBuddyDatabase.getDatabase( context );
        this.exerciseDao = db.exerciseDao();
    }

    public List<Exercise> getExercises()
    {
        return this.query( () -> this.exerciseDao.getExercises() );
    }

    public List<Exercise> getExercisesWhichAreNotInTraining(Integer trainingId)
    {
        return this.query( () -> this.exerciseDao.getAllExercisesWhichAreNotTraining(trainingId) );
    }

    private List<Exercise> query( Callable<List<Exercise>> query )
    {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private List<ExerciseWithTrainingsLog> query2(Callable<List<ExerciseWithTrainingsLog>> query )
    {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public void update(Exercise exercise) {
        exercise.setVersion((int) System.currentTimeMillis());
        exercise.setVersion( exercise.getVersion() + 1 );

        MyFitnessBuddyDatabase.execute( () -> exerciseDao.update( exercise) );
    }

    public void insert(Exercise exercise) {
        exercise.setCreated( System.currentTimeMillis() );
        exercise.setModified( exercise.getCreated() );
        exercise.setVersion( 1 );

        MyFitnessBuddyDatabase.execute( () -> this.repositoryExerciseId = exerciseDao.insertExercise( exercise ) );
    }

    public void delete( List<Exercise> exercises )
    {
        MyFitnessBuddyDatabase.execute( () -> exerciseDao.delete( exercises ) );
    }

    public void delete2( Exercise exercise )
    {
        MyFitnessBuddyDatabase.execute( () -> exerciseDao.delete2( exercise ) );
    }

    public LiveData<Exercise> getExerciseByIdAsLiveData(long exerciseId )
    {
        return this.queryLiveData(() -> this.exerciseDao.getExerciseById(exerciseId) );
    }

public LiveData<List<ExerciseWithMuscleGroup>> getExerciseLiveData() {
    try {
        return MyFitnessBuddyDatabase.executeWithReturn( this.exerciseDao::getExercisesWithMuscleGroups );
    }
    catch (ExecutionException | InterruptedException e) {
        e.printStackTrace();
    }

    // Well, is this a reasonable default return value?
    return null;
}

    private <T> LiveData<T> queryLiveData( Callable<LiveData<T>> query )
    {
        try {
            return MyFitnessBuddyDatabase.executeWithReturn( query );
        }
        catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Well, is this a reasonable default return value?
        return new MutableLiveData<>();
    }

    public LiveData<List<ExerciseWithMuscleGroup>> getExerciseFromTraining(Integer trainingId )
    {
        return this.exerciseDao.getAllExercisesFromTraining( trainingId ) ;
    }

    public List<ExerciseWithTrainingsLog> getExerciseWithTrainingsLogLiveDataByExerciseId(long exerciseId)
    {
        return this.query2(() -> this.exerciseDao.getExercisesWithTrainingsLogById(exerciseId) );
    }

}

